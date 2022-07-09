package de.murmelmeister.citybuild.commands.locations;

import de.murmelmeister.citybuild.commands.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DeleteWarpCommand extends CommandManager {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("citybuild.command.deletewarp"))) {
            sendMessage(sender, this.messages.getConfigMessage("Message.NoPermission"));
            return true;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            sendMessage(sender, this.messages.getConfigMessage("Message.NoConsole"));
            return true;
        }

        if (args.length != 1) {
            sendMessage(sender, "§7Syntax: §c/deletewarp <location>");
            return true;
        }

        this.locations.removeLocation(args[0]);
        sendMessage(player, "§aWurde gelöscht.");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            String lastWord = args[args.length - 1];

            for (String location : this.locations.getLocationNames()) {

                if (StringUtil.startsWithIgnoreCase(location, lastWord)) {
                    tabComplete.add(location);
                }
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }
        return tabComplete;
    }
}
