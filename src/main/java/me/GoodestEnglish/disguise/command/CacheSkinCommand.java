package me.GoodestEnglish.disguise.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.GoodestEnglish.disguise.cache.SkinCache;
import me.GoodestEnglish.disguise.util.CC;
import me.GoodestEnglish.disguise.util.GoodestCommand;
import me.GoodestEnglish.disguise.util.Permission;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class CacheSkinCommand extends GoodestCommand {
    public CacheSkinCommand() {
        super("cacheskin", Permission.ADMIN_PERMISSION);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 1) {
            sendHelpMessage(commandSender);
            return;
        }

        String name = args[0];
        String value;
        String signature;

        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());

            JsonObject obj = new JsonParser().parse(reader_0).getAsJsonObject();
            name = obj.get("name").getAsString();//把名稱的大小楷改好
            String uuid = obj.get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            value = textureProperty.get("value").getAsString();
            signature = textureProperty.get("signature").getAsString();
        } catch (IOException e) {
            commandSender.sendMessage(CC.RED + "在獲取玩家皮膚資料時發生錯誤, 過程已終止");
            return;
        }

        SkinCache cache = new SkinCache(name, value, signature, new ArrayList<>());
        SkinCache.getSkinCaches().add(cache);
        SkinCache.save();

        commandSender.sendMessage(CC.GREEN + "成功獲取 " + name + " 的皮膚資料!");
    }

    @Override
    public void sendHelpMessage(CommandSender commandSender) {
        commandSender.sendMessage(CC.RED + "指令用法: /cacheskin <玩家ID>");
    }

    @Override
    public AllowedUser getAllowedUser() {
        return AllowedUser.ALL;
    }
}
