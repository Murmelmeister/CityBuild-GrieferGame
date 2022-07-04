package de.murmelmeister.citybuild.commands.homes;

import de.murmelmeister.citybuild.commands.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SetHomeCommand extends CommandManager {

    // TODO: Config + German translation

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("citybuild.command.sethome"))) {
            sendMessage(sender, "#ff0000You don't have the permission to do that.");
            return true;
        }

        if (args.length == 1) {

            Player player = sender instanceof Player ? (Player) sender : null;

            if (player == null) {
                sendMessage(sender, "#ff0000This command does not work in the console.");
                return true;
            }

            String name = args[0];

            if (this.homes.hasHomes(player.getUniqueId(), args[0])) {
                sendMessage(sender, "#ff0000You do not have this name home. Chose a other name.");
                return true;
            }

            if (this.homes.hasHomeLimit(player)) {
                this.homes.setHome(player.getUniqueId(), player.getLocation(), name);
                sendMessage(player, String.format("#999999The home #ffb500%s #999999was #00ff00created#999999.", name));
            }

        } else {
            sendMessage(sender, "#999999Syntax: #ff0000/sethome <homes>");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
