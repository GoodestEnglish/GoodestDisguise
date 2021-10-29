package me.GoodestEnglish.disguise.cache;

import lombok.Getter;
import me.GoodestEnglish.disguise.GoodestDisguise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NickCache {

    @Getter private static final List<String> nick = new ArrayList<>();

    public static void init() {
        nick.addAll(GoodestDisguise.INSTANCE.getNickDataFile().getStringList("DATA"));
    }

    public static String getRandomNick() {
        return nick.get(new Random().nextInt(nick.size()));
    }

}
