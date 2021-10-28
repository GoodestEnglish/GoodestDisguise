package me.GoodestEnglish.disguise.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.util.BasicConfigFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SkinCache {

    @Getter private static final List<SkinCache> skinCaches = new ArrayList<>();

    private final String name;
    private final String value;
    private final String signature;
    private final List<String> lore;

    public static void init() {
        BasicConfigFile configFile = GoodestDisguise.INSTANCE.getSkinDataFile();
        if (configFile.getConfiguration().getConfigurationSection("DATA") != null) {
            for (String key : configFile.getConfiguration().getConfigurationSection("DATA").getKeys(false)) {
                String name = configFile.getString("DATA." + key + ".NAME");
                String value = configFile.getString("DATA." + key + ".VALUE");
                String signature = configFile.getString("DATA." + key + ".SIGNATURE");
                List<String> lore = configFile.getStringList("DATA." + key + ".LORE");

                skinCaches.add(new SkinCache(name, value, signature, lore));
            }
        }
    }

    public static void save() {
        int i = 0;
        BasicConfigFile cacheFile = GoodestDisguise.INSTANCE.getSkinDataFile();
        for (SkinCache cache : skinCaches) {
            String path = "DATA." + i++;

            cacheFile.getConfiguration().set(path + ".NAME", cache.getName());
            cacheFile.getConfiguration().set(path + ".VALUE", cache.getValue());
            cacheFile.getConfiguration().set(path + ".SIGNATURE", cache.getSignature());
            cacheFile.getConfiguration().set(path + ".LORE", cache.getLore());
        }

        try {
            cacheFile.getConfiguration().save(cacheFile.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
