package me.GoodestEnglish.disguise.profile;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import lombok.Getter;
import lombok.Setter;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.cache.RankCache;
import me.GoodestEnglish.disguise.cache.SkinCache;
import me.GoodestEnglish.disguise.util.CC;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class PlayerProfile {

    @Getter private static final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    private final UUID uuid;
    private final String username;
    private final GameProfile originalGameProfile;
    @Setter private boolean disguised = false;
    @Setter private String disguiseUsername = null;
    @Setter private SkinCache disguiseSkin = null;
    @Setter private RankCache disguiseRank = null;
    @Setter private long lastDisguiseTime = -1;


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

    public void load(Consumer<Boolean> callback) {
        try {
            Document document = GoodestDisguise.INSTANCE.getMongoDB().getPlayerData(uuid, Document.class);
            if (document == null) {
                return;
            }
            disguised = true;
            disguiseUsername = document.getString("disguiseUsername");
            lastDisguiseTime = document.getLong("lastDisguiseTime");
            Document skinCacheDoc = (Document) document.get("skinCache");
            disguiseSkin = new SkinCache(skinCacheDoc.getString("name"), skinCacheDoc.getString("value"), skinCacheDoc.getString("signature"), null);
            Document rankCacheDoc = (Document) document.get("rankCache");
            disguiseRank = new RankCache(rankCacheDoc.getString("name"), rankCacheDoc.getString("color"), rankCacheDoc.getString("display"));
            callback.accept(true);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPlayer(uuid).kickPlayer(CC.RED + GoodestDisguise.INSTANCE.getDescription().getName() + " >> 加載資料時出現了錯誤, 請稍後再試");
            callback.accept(false);
        }
    }

    public void save(Consumer<Boolean> callback) {
        Document document = new Document();
        document.put("uuid", uuid.toString());
        document.put("username", username);
        document.put("disguiseUsername", disguiseUsername);
        document.put("lastDisguiseTime", lastDisguiseTime);

        Document skinCacheDoc = new Document();
        skinCacheDoc.put("name", disguiseSkin.getName());
        skinCacheDoc.put("value", disguiseSkin.getValue());
        skinCacheDoc.put("signature", disguiseSkin.getSignature());
        document.put("skinCache", skinCacheDoc);

        Document rankCacheDoc = new Document();
        rankCacheDoc.put("name", disguiseRank.getName());
        rankCacheDoc.put("color", disguiseRank.getColor());
        rankCacheDoc.put("display", disguiseRank.getDisplay());
        document.put("rankCache", rankCacheDoc);

        try {
            GoodestDisguise.INSTANCE.getMongoDB().replaceResult(uuid, document);
            callback.accept(true);
        } catch (Exception e) {
            e.printStackTrace();
            callback.accept(false);
        }
    }

}
