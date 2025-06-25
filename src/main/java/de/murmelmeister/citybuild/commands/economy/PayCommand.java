package de.murmelmeister.citybuild.commands.economy;

import de.murmelmeister.citybuild.commands.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

public class PayCommand extends CommandManager {

    // TODO: Config + German translation + Coin to Money changing

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("citybuild.command.pay.use"))) {
            sendMessage(sender, "#ff0000You don't have the permission to do that.");
            return true;
        }

        Player player = sender instanceof Player ? (Player) sender : null;

        if (player == null) {
            sendMessage(sender, "#AA0000This command does not work in the console.");
            return true;
        }

        if (args.length == 2) {

            // Pay all player
            if (args[0].equals("*")) {

                // Pay all player and pay all his Coin
                if (args[1].equals("*")) {
                    sendMessage(player, "#ff0000Do not do this, please. Thank you. :)");
                    return true;
                }

                if (!(sender.hasPermission("citybuild.command.pay.allothers"))) {
                    sendMessage(sender, "#ff0000You don't have the permission to do that.");
                    return true;
                }

                BigDecimal coin = new BigDecimal(args[1]);

                if(-coin.doubleValue() >= 0) {
                    sendMessage(sender, "#ff0000You must not specify a negative number.");
                    return true;
                }

                if (!(this.moneyAPI.hasEnoughMoney(player.getUniqueId(), coin.doubleValue()))) {
                    sendMessage(sender, "#999999You do not have #ff0000enough Coin#999999.");
                    return true;
                }

                for (Player all : player.getServer().getOnlinePlayers()) {
                    this.moneyAPI.removeMoney(player.getUniqueId(), coin);
                    this.moneyAPI.addMoney(all.getUniqueId(), coin);
                    sendMessage(all, String.format("#ffb500%s #999999has paid you #00ff00%s Coin#999999.", player.getName(), this.decimalFormat.format(coin)));
                }

                sendMessage(player, String.format("#999999You have paid #ffb500all #00ff00%s Coin#999999.", this.decimalFormat.format(coin)));
                return true;
            }

            Player target = player.getServer().getPlayer(args[0]);

            if (target == null) {
                sendMessage(sender, String.format("#999999The player #ffb500%s #999999is #ff0000§nnot§r #ff0000online #999999on #ff0000this server#999999.", args[0]));
                return true;
            }

            // Pay all your Coin to a player
            if (args[1].equals("*")) {

                if (!(sender.hasPermission("citybuild.command.pay.allCoin"))) {
                    sendMessage(sender, "#ff0000You don't have the permission to do that.");
                    return true;
                }

                BigDecimal allCoin = this.moneyAPI.getMoney(player.getUniqueId());

                if(-allCoin.doubleValue() >= 0) {
                    sendMessage(sender, "#ff0000You must not specify a negative number.");
                    return true;
                }

                this.moneyAPI.removeMoney(player.getUniqueId(), allCoin);
                this.moneyAPI.addMoney(target.getUniqueId(), allCoin);

                sendMessage(target, String.format("#ffb500%s #999999paid you #00ff00all his Coin#999999. #777777(#ffb500%s Coin#777777)", player.getName(), this.decimalFormat.format(allCoin)));
                sendMessage(player, String.format("#999999You have paid #ffb500%s #00ff00all your Coin#999999. #777777(#ffb500%s Coin#777777)", target.getName(), this.decimalFormat.format(allCoin)));

                return true;
            }

            // Normal pay
            BigDecimal coin = new BigDecimal(args[1]);

            if(-coin.doubleValue() >= 0) {
                sendMessage(sender, "#ff0000You must not specify a negative number.");
                return true;
            }

            if (!(this.moneyAPI.hasEnoughMoney(player.getUniqueId(), coin.doubleValue()))) {
                sendMessage(sender, "#999999You do not have #ff0000enough Coin#999999.");
                return true;
            }

            this.moneyAPI.removeMoney(player.getUniqueId(), coin);
            this.moneyAPI.addMoney(target.getUniqueId(), coin);

            sendMessage(target, String.format("#ffb500%s #999999has paid you #00ff00%s Coin#999999.", player.getName(), this.decimalFormat.format(coin)));
            sendMessage(player, String.format("#999999You have paid #ffb500%s #00ff00%s Coin#999999.", target.getName(), this.decimalFormat.format(coin)));

        } else {
            sendMessage(sender, "#999999Syntax: #ff0000/pay <player> <coin>");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
