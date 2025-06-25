package de.murmelmeister.citybuild.commands.locations;

import de.murmelmeister.citybuild.commands.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SetWarpCommand extends CommandManager {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("citybuild.command.setwarp"))) {
            sendMessage(sender, this.messages.getConfigMessage("Message.NoPermission"));
            return true;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            sendMessage(sender, this.messages.getConfigMessage("Message.NoConsole"));
            return true;
        }

        if (args.length != 1) {
            sendMessage(sender, "§7Syntax: §c/setwarp <location>");
            return true;
        }

        this.locations.setLocation(args[0], player.getLocation());
        sendMessage(player, String.format("§aDer Warp wurde gesetzt. §7(§aName: %s§7)", args[0]));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
