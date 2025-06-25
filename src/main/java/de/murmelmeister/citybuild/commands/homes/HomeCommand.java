package de.murmelmeister.citybuild.commands.homes;

import de.murmelmeister.citybuild.commands.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HomeCommand extends CommandManager {

    // TODO: Config + German translation

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("citybuild.command.home"))) {
            sendMessage(sender, "#ff0000You don't have the permission to do that.");
            return true;
        }

        if (args.length == 1) {

            Player player = sender instanceof Player ? (Player) sender : null;

            if (player == null) {
                sendMessage(sender, "#ff0000This command does not work in the console.");
                return true;
            }

            try {
                player.teleport(this.homes.getHome(player.getUniqueId(), args[0]));
                sendMessage(player, String.format("#999999You have been #00ff00teleported #999999to the home #ffb500%s#999999.", args[0]));
            } catch (IllegalArgumentException e) {
                sendMessage(player, "#999999You do #ff0000not #999999have #ff0000this home#999999.");
            }

        } else {
            sendMessage(sender, "#999999Syntax: #ff0000/home <homes>");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {
            String lastWord = args[args.length - 1];

            for (String home : this.homes.getHomeNames()) {

                if (StringUtil.startsWithIgnoreCase(home, lastWord)) {
                    tabComplete.add(home);
                }
                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }
        return tabComplete;
    }
}
