package me.GoodestEnglish.disguise.command;

import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.util.GoodestCommand;
import me.GoodestEnglish.disguise.util.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class UndisguiseCommand extends GoodestCommand {
    public UndisguiseCommand() {
        super("undisguise", Collections.singletonList("unnick"), Permission.DONOR_PERMISSION);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        GoodestDisguise.INSTANCE.getDisguiseManager().undisguise((Player) commandSender);
    }

    @Override
    public void sendHelpMessage(CommandSender commandSender) {

    }

    @Override
    public AllowedUser getAllowedUser() {
        return AllowedUser.PLAYER;
    }
}
