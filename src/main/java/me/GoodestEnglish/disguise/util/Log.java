package me.GoodestEnglish.disguise.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Log {

    public static void show(LogLevel level, String message) {
        if (level == LogLevel.LOW) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&a[DEBUG] " + message));
        } else if (level == LogLevel.MEDIUM) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&e[DEBUG] " + message));
        } else if (level == LogLevel.HIGH) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&c[DEBUG] " + message));
        } else if (level == LogLevel.EXTREME) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4[DEBUG] " + message));
        }
    }

    public static void debug(Player player, String message) {
        if (player.hasPermission(Permission.ADMIN_PERMISSION)) {
            player.sendMessage(CC.translate("&c[ADMIN DEBUG] " + message));
        }
    }

    public static void debug(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission(Permission.ADMIN_PERMISSION)) {
                player.sendMessage(CC.translate("&c[ADMIN DEBUG] " + message));
            }
        });
    }

    public enum LogLevel {
        LOW, MEDIUM, HIGH, EXTREME
    }
}
