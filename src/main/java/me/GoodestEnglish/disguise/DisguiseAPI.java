package me.GoodestEnglish.disguise;

import me.GoodestEnglish.disguise.profile.PlayerProfile;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DisguiseAPI {

    public static boolean isDisguised(Player player) {
        return PlayerProfile.getByUuid(player.getUniqueId()).isDisguised();
    }

}
