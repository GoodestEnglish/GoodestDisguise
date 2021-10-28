package me.GoodestEnglish.disguise.profile;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile {

    private static final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    private final UUID uuid;
    private final String username;

    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        this.username = Bukkit.getPlayer(uuid).getName();
    }

    public static PlayerProfile getByUuid(UUID uuid) {
        PlayerProfile profile = profiles.get(uuid);
        if (profile == null) {
            profile = new PlayerProfile(uuid);
            profiles.put(uuid, profile);
        }
        return profile;
    }

}
