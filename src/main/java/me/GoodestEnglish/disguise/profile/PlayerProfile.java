package me.GoodestEnglish.disguise.profile;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import lombok.Getter;
import lombok.Setter;
import me.GoodestEnglish.disguise.cache.RankCache;
import me.GoodestEnglish.disguise.cache.SkinCache;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerProfile {

    private static final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    private final UUID uuid;
    private final String username;
    @Setter private boolean disguised = false;
    @Setter private String disguiseUsername = null;
    @Setter private SkinCache disguiseSkin = null;
    @Setter private RankCache disguiseRank = null;
    private final GameProfile originalGameProfile;


    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        this.username = Bukkit.getPlayer(uuid).getName();
        this.originalGameProfile = ((CraftPlayer)Bukkit.getPlayer(uuid)).getProfile();
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
