package de.murmelmeister.citybuild.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class LobbyCommand extends CommandManager {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("citybuild.command.lobby"))) {
            sendMessage(sender, this.messages.getConfig().getString("Message.NoPermission"));
            return true;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            sendMessage(sender, "#AA0000This command does not work in the console.");
            return true;
        }

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF("Lobby-1");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        sendMessage(player, "#999999Connect to Lobby-1...");

        player.sendPluginMessage(this.instance, "BungeeCord", b.toByteArray());

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
