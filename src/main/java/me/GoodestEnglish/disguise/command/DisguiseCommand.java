package me.GoodestEnglish.disguise.command;

import me.GoodestEnglish.disguise.menu.SelectRankMenu;
import me.GoodestEnglish.disguise.util.GoodestCommand;
import me.GoodestEnglish.disguise.util.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class DisguiseCommand extends GoodestCommand {
    public DisguiseCommand() {
        super("disguise", Collections.singletonList("nick"), Permission.DONOR_PERMISSION);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        Player player = (Player) commandSender;

        if (args.length != 1) {
            new SelectRankMenu().openMenu(player);
        }
    }

    @Override
    public void sendHelpMessage(CommandSender commandSender) {

    }

    @Override
    public AllowedUser getAllowedUser() {
        return AllowedUser.PLAYER;
    }
}
