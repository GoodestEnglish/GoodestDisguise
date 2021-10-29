package me.GoodestEnglish.disguise.manager;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.GoodestEnglish.disguise.DisguiseAPI;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.cache.RankCache;
import me.GoodestEnglish.disguise.cache.SkinCache;
import me.GoodestEnglish.disguise.profile.PlayerProfile;
import me.GoodestEnglish.disguise.util.Common;
import me.GoodestEnglish.disguise.util.PacketSender;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DisguiseManager {

    public void disguise(Player player, String nick, RankCache rankCache, SkinCache skinCache, boolean firstJoin) {
        Common.sendMessage(player, "&e正在處理暱稱請求, 請稍作等待...");
        PlayerProfile profile = PlayerProfile.getByUuid(player.getUniqueId());

        if (System.currentTimeMillis() < profile.getLastDisguiseTime() + 60*1000) {
            Common.sendMessage(player, "&c抱歉, 每次暱稱需要等待60秒才能再使用一次");
            return;
        }

        profile.setDisguised(true);
        profile.setDisguiseUsername(nick);
        profile.setDisguiseRank(rankCache);
        profile.setDisguiseSkin(skinCache);
        if (!firstJoin) {
            profile.setLastDisguiseTime(System.currentTimeMillis());
        }

        profile.save(bool -> {
            if (bool) {
                GameProfile gameProfile = new GameProfile(player.getUniqueId(), nick);
                gameProfile.getProperties().removeAll("textures");
                gameProfile.getProperties().put("textures", new Property("textures", skinCache.getValue(), skinCache.getSignature()));

                changeGameProfile(((CraftPlayer)player).getHandle(), gameProfile);
                reloadSkin(player, firstJoin);
                Common.sendMessage(player, "&a成功使用暱稱! 你現在將會是 " + nick);
            } else {
                Common.sendMessage(player, "&c在使用暱稱時出現了錯誤, 請稍後再試");
            }
        });
    }

    public void undisguise(Player player) {
        PlayerProfile profile = PlayerProfile.getByUuid(player.getUniqueId());
        profile.setDisguised(false);
        profile.setDisguiseUsername(null);
        profile.setDisguiseRank(null);
        profile.setDisguiseSkin(null);

        changeGameProfile(((CraftPlayer)player).getHandle(), profile.getOriginalGameProfile());
        reloadSkin(player, false);
        Common.sendMessage(player, "&a成功解除暱稱");
    }

    private void changeGameProfile(EntityHuman entityHuman, GameProfile gameProfile) {
        try {
            Field gp2 = entityHuman.getClass().getSuperclass().getDeclaredField("bH");
            gp2.setAccessible(true);
            gp2.set(entityHuman, gameProfile);
            gp2.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadSkin(Player player, boolean firstJoin) {
        reloadSkinForSelf(player, firstJoin);
        reloadSkinForOther(player);
    }

    public void reloadSkinForSelf(Player player, boolean firstJoin) {
        //https://github.com/SkinsRestorer/SkinsRestorerX/blob/dev/bukkit/src/main/java/net/skinsrestorer/bukkit/skinrefresher/SpigotSkinRefresher.java
        final EntityPlayer ep = ((CraftPlayer) player).getHandle();
        final Location loc = player.getLocation().clone();
        ep.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep));
        Bukkit.getScheduler().runTask(GoodestDisguise.INSTANCE, () -> {
            ep.playerConnection.sendPacket(new PacketPlayOutRespawn(ep.dimension, ep.getWorld().getDifficulty(), ep.getWorld().getWorldData().getType(), ep.playerInteractManager.getGameMode()));
            ep.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep));
            for (Chunk chunk : player.getWorld().getLoadedChunks()) {
                ep.playerConnection.sendPacket(new PacketPlayOutMapChunk(((CraftChunk)chunk).getHandle(), true, 20));
            }
            ep.updateAbilities();
            ep.playerConnection.sendPacket(new PacketPlayOutPosition(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), new HashSet<>()));
            ep.playerConnection.sendPacket(new PacketPlayOutHeldItemSlot(player.getInventory().getHeldItemSlot()));
            player.updateInventory();
            ep.triggerHealthUpdate();

            if (!firstJoin) {
                player.teleport(loc);
            }
        });
    }

    public void reloadSkinForOther(Player player) {
        PacketSender.sendToAll(new PacketPlayOutEntityDestroy(player.getEntityId()));
        PacketSender.sendToAll(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)player).getHandle()));

        List<Player> viewablePlayers = Bukkit.getOnlinePlayers().stream().filter(target -> target.canSee(player)).collect(Collectors.toList());
        for (Player target : viewablePlayers) {
            target.hidePlayer(player);
            target.showPlayer(player);
        }
    }

}
