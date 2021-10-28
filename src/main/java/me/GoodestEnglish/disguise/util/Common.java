package me.GoodestEnglish.disguise.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

import java.util.List;

public class Common {

    public static void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendTitle(new Title(CC.translate(title), CC.translate(subtitle), fadeIn, stay, fadeOut));
        });
    }

    public static void broadcastMessage(String... str) {
        for (String string : str) {
            Bukkit.broadcastMessage(CC.translate(string));
        }
    }

    public static void broadcastSound(Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 10, 1);
        }
    }

    public static void sendMessage(CommandSender sender, String... str) {
        if (sender == null) {
            return;
        }
        for (String s : str) {
            sender.sendMessage(CC.translate(s));
        }
    }

    public static void sendMessage(Player player, String... str) {
        if (player == null) {
            return;
        }
        for (String s : str) {
            player.sendMessage(CC.translate(s));
        }
    }

    public static void sendMessage(Player player, List<String> str) {
        if (player == null) {
            return;
        }
        for (String s : str) {
            player.sendMessage(CC.translate(s));
        }
    }

    public static void sendActionBar(Player player, String str) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + CC.translate(str) + "\"}"), (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public static int countOnlinePlayers() {
        return Bukkit.getOnlinePlayers().size();
    }

    public static boolean hasPermission(CommandSender sender, List<String> permissions) {
        for (String permission : permissions) {
            if (sender.hasPermission(permission)) return true;
        }
        return false;
    }

}
