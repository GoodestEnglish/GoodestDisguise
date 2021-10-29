package me.GoodestEnglish.disguise.listener;

import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.profile.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DisguiseListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = PlayerProfile.getByUuid(player.getUniqueId());
        profile.load(bool -> {
            if (bool) {
                GoodestDisguise.INSTANCE.getDisguiseManager().disguise(player, profile.getDisguiseUsername(), profile.getDisguiseRank(), profile.getDisguiseSkin(), true);
            }
        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //have to use a runnable to remove due to PlayerJoinEvent created a PlayerData, otherwise ConcurrentModificationException will occur
        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerProfile.getProfiles().remove(player.getUniqueId());
            }
        }.runTaskLaterAsynchronously(GoodestDisguise.INSTANCE, 1L);
    }

}
