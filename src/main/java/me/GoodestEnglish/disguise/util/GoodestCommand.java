package me.GoodestEnglish.disguise.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public abstract class GoodestCommand extends BukkitCommand {

    @Setter private static JavaPlugin javaPlugin;
    private final List<String> permissions;

    public GoodestCommand(String name, String... permission) {
        super(name);
        this.permissions = Arrays.asList(permission);


        ((CraftServer) javaPlugin.getServer()).getCommandMap().register(javaPlugin.getName(), this);
    }

    public GoodestCommand(String name, List<String> aliases, String... permission) {
        super(name, "", "", aliases);
        this.permissions = Arrays.asList(permission);


        ((CraftServer) javaPlugin.getServer()).getCommandMap().register(javaPlugin.getName(), this);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        long startMS = System.currentTimeMillis();
        if (sender instanceof ConsoleCommandSender && getAllowedUser() == AllowedUser.PLAYER) {
            sender.sendMessage(CC.RED + "這個指令只允許由玩家使用");
            return false;
        } else if (sender instanceof Player && getAllowedUser() == AllowedUser.CONSOLE) {
            sender.sendMessage(CC.RED + "這個指令只允許由控制台使用");
            return false;
        }
        if (!permissions.isEmpty() && !Common.hasPermission(sender, permissions)) {
            sender.sendMessage(NO_PERISSION);
            return false;
        }
        onCommand(sender, args);
        //Log.debug((Player) sender, "執行 '/" + getName() + "' 指令合共使用了 " + (System.currentTimeMillis() - startMS) + "ms");
        return false;
    }

    public abstract void onCommand(CommandSender commandSender, String[] args);

    public abstract void sendHelpMessage(CommandSender commandSender);

    public abstract AllowedUser getAllowedUser();

    public static String NO_PERISSION = CC.RED + "你沒有權限使用這個指令";

    @Getter
    @AllArgsConstructor
    public enum AllowedUser {
        PLAYER("玩家"),
        CONSOLE("控制台"),
        ALL("所有用家");

        public String readable;
    }

}
