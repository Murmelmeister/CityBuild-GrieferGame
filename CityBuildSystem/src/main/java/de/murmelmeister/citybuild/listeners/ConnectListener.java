package de.murmelmeister.citybuild.listeners;

import de.murmelmeister.playtime.PlayTimeAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ConnectListener extends Listeners {

    /*
    Handle the player join event.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer(); // Player
        event.joinMessage(null);

        player.getInventory().clear();
        updateActionBar(this.playTimeAPI);

        try {
            player.teleport(this.locations.getSpawn()); // Teleport to the spawn
        } catch (NullPointerException | IllegalArgumentException e) {
            // When the spawn does not exist
            sendMessage(player, "§8--- §cSetup §8---");
            sendMessage(player, "§7Bitte setze den Spawn. §8(§c/setspawn§8)");
        }

        // When the death height does not exist
        if (this.locations.getConfig().get("Locations.DeathHeight") == null) {
            sendMessage(player, "§8--- §cSetup §8---");
            sendMessage(player, "§7Bitte setze die Todeshöhe. §8(§c/setdeathheight§8)");
        }
    }

    /*
    Handle the player quit event.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerQuit(PlayerQuitEvent event) {
        // When the player disconnect
        event.quitMessage(null); // Disconnect message
    }

    private void setActionBar(PlayTimeAPI playTime, Player player) {
        if ((playTime.getYears(player.getUniqueId()) == 0)
                && (playTime.getDays(player.getUniqueId()) == 0)
                && (playTime.getHours(player.getUniqueId()) == 0)) {
            player.sendActionBar(Component.text("PlayTime: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getMinutes(player.getUniqueId()) == 1 ? "1 minute" : playTime.getMinutes(player.getUniqueId()) + " minutes"), TextColor.color(0xE7D606))));

        } else if ((playTime.getYears(player.getUniqueId()) == 0)
                && (playTime.getDays(player.getUniqueId()) == 0)) {
            player.sendActionBar(Component.text("PlayTime: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getHours(player.getUniqueId()) == 1 ? "1 hour" : playTime.getHours(player.getUniqueId()) + " hours")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 minute" : playTime.getMinutes(player.getUniqueId()) + " minutes"), TextColor.color(0xE7D606))));

        } else if (playTime.getYears(player.getUniqueId()) == 0) {
            player.sendActionBar(Component.text("PlayTime: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getDays(player.getUniqueId()) == 1 ? "1 day" : playTime.getDays(player.getUniqueId()) + " days")
                            + " " + (playTime.getHours(player.getUniqueId()) == 1 ? "1 hour" : playTime.getHours(player.getUniqueId()) + " hours")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 minute" : playTime.getMinutes(player.getUniqueId()) + " minutes"), TextColor.color(0xE7D606))));
        } else {
            player.sendActionBar(Component.text("PlayTime: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getYears(player.getUniqueId()) == 1 ? "1 year" : playTime.getYears(player.getUniqueId()) + " years")
                            + " " + (playTime.getDays(player.getUniqueId()) == 1 ? "1 day" : playTime.getDays(player.getUniqueId()) + " days")
                            + " " + (playTime.getHours(player.getUniqueId()) == 1 ? "1 hour" : playTime.getHours(player.getUniqueId()) + " hours")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 minute" : playTime.getMinutes(player.getUniqueId()) + " minutes"), TextColor.color(0xE7D606))));
        }
    }

    private void updateActionBar(PlayTimeAPI playTime) {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player all : instance.getServer().getOnlinePlayers()) {
                    setActionBar(playTime, all);
                }

            }
        }.runTaskTimer(this.instance, 20, 20);
    }

}
