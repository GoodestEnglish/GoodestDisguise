package me.GoodestEnglish.disguise;

import lombok.Getter;
import me.GoodestEnglish.disguise.cache.SkinCache;
import me.GoodestEnglish.disguise.command.CacheSkinCommand;
import me.GoodestEnglish.disguise.command.DisguiseCommand;
import me.GoodestEnglish.disguise.database.MongoDB;
import me.GoodestEnglish.disguise.manager.DisguiseManager;
import me.GoodestEnglish.disguise.util.BasicConfigFile;
import me.GoodestEnglish.disguise.util.GoodestCommand;
import me.GoodestEnglish.disguise.util.menu.Menu;
import org.bukkit.plugin.java.JavaPlugin;

public class GoodestDisguise extends JavaPlugin {

    public static GoodestDisguise INSTANCE;
    @Getter private MongoDB mongoDB;

    @Getter private BasicConfigFile configFile;
    @Getter private BasicConfigFile skinDataFile;

    @Getter private DisguiseManager disguiseManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        GoodestCommand.setJavaPlugin(this);

        configFile = new BasicConfigFile(this, "config.yml");
        skinDataFile = new BasicConfigFile(this, "skindata.yml");

        disguiseManager = new DisguiseManager();

        Menu.init();
        SkinCache.init();

        new DisguiseCommand();
        new CacheSkinCommand();
    }
}
