package me.GoodestEnglish.disguise.util;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketSender {

    public static void send(Player player, Packet<?> packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendToAll(Packet<?> packet) {
        for (Player player : Bukkit.getOnlinePlayers())
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

}
