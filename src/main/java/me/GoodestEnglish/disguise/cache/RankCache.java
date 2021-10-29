package me.GoodestEnglish.disguise.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.util.BasicConfigFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RankCache {

    @Getter private static final List<RankCache> rankCaches = new ArrayList<>();

    private final String name;
    private final String color;
    private final String display;

    public static void init() {
        BasicConfigFile configFile = GoodestDisguise.INSTANCE.getRankDataFile();
        for (String key : configFile.getConfiguration().getConfigurationSection("RANK").getKeys(false)) {
            String name = configFile.getString("RANK." + key + ".NAME");
            String color = configFile.getString("RANK." + key + ".COLOR");
            String display = configFile.getString("RANK." + key + ".DISPLAY");

            rankCaches.add(new RankCache(name, color, display));
        }
    }

}
