package de.murmelmeister.citybuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CityBuildCommand extends CommandManager {

    // TODO: Message config editierbar machen

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {

            if (!(sender.hasPermission(this.messages.getConfigMessage("Permission.CityBuild.Use")))) {
                sendMessage(sender, this.messages.getConfigMessage("Message.NoPermission"));
                return true;
            }

            String author = "Murmelmeister (LilaAtomBurger)";
            String version = "0.0.1-ALPHA-SNAPSHOT";

            sendMessage(sender, " ");
            sendMessage(sender, "§7Das Plugin wurde von §3" + author + " §7programmiert.");
            sendMessage(sender, "§7Version: §3" + version);
            sendMessage(sender, " ");

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("reload")) {

                if (!(sender.hasPermission(this.messages.getConfigMessage("Permission.CityBuild.Reload")))) {
                    sendMessage(sender, this.messages.getConfigMessage("Message.NoPermission"));
                    return true;
                }

                this.messages.createFile();
                sendMessage(sender, this.messages.getConfigMessage("Message.CityBuild.ReloadMessage"));
            }

        } else {
            sendMessage(sender, this.messages.getConfigMessage("Message.CityBuild.Syntax"));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> tabComplete = new ArrayList<>();

        if (args.length == 1) {

            String[] commands = new String[]{"reload"};

            String lastWord = args[args.length - 1];

            for (String cmd : commands) {

                if (StringUtil.startsWithIgnoreCase(cmd, lastWord)) {
                    tabComplete.add(cmd);
                }

                tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
            }
        }
        return tabComplete;
    }
}
