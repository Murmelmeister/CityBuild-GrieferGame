package de.murmelmeister.citybuild.listeners;

import de.murmelmeister.citybuild.api.PlayTimeAPI;
import de.murmelmeister.citybuild.utils.scoreboards.TestScoreboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class ConnectListener extends Listeners {
    private final List<TextColor> textColorList = Arrays.asList(TextColor.fromHexString("#01b416"), TextColor.fromHexString("#0dbc2b"), TextColor.fromHexString("#17c43b"), TextColor.fromHexString("#1fcd4a"), TextColor.fromHexString("#26d558"), TextColor.fromHexString("#2ddd65"), TextColor.fromHexString("#34e673"), TextColor.fromHexString("#3bee80"), TextColor.fromHexString("#42f78d"), TextColor.fromHexString("#49ff9a"),
            TextColor.fromHexString("#42f78d"), TextColor.fromHexString("#3bee80"), TextColor.fromHexString("#34e673"), TextColor.fromHexString("#2ddd65"), TextColor.fromHexString("#26d558"), TextColor.fromHexString("#1fcd4a"), TextColor.fromHexString("#17c43b"), TextColor.fromHexString("#0dbc2b"), TextColor.fromHexString("#01b416"));
    private final List<TextColor> patterns = new ArrayList<>(textColorList);

    private TextComponent formatTextColorArray(String input, List<TextColor> colors) {
        TextComponent component = Component.empty();
        char[] chars = input.toCharArray();
        int count = 0;

        for (char character : chars) {
            if (count >= colors.size()) count = 0;
            component = component.append(Component.text(character).color(colors.get(count)));
            count++;
        }
        return component;
    }

    /*
    Handle the player join event.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer(); // Player
        createMoneyAccount(player.getUniqueId(), player.getName());
        createBankAccount(player.getUniqueId(), player.getName());
        event.joinMessage(null);

        player.getInventory().clear();

        new TestScoreboard(player);

        try {
            player.teleport(this.locations.getSpawn()); // Teleport to the spawn
        } catch (NullPointerException | IllegalArgumentException e) {
            // When the spawn does not exist
            sendMessage(player, "§8--- §cSetup §8---");
            sendMessage(player, "§7Bitte setze den Spawn. §8(§c/setspawn§8)");
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

    public void updateActionBar(PlayTimeAPI playTimeAPI, Player player) {
        Collections.rotate(patterns, 1);

        String playTime = "Spielzeit: " + (playTimeAPI.getYears(player.getUniqueId()) == 1 ? "1 Jahr" : playTimeAPI.getYears(player.getUniqueId()) + " Jahre")
                          + " " + (playTimeAPI.getDays(player.getUniqueId()) == 1 ? "1 Tag" : playTimeAPI.getDays(player.getUniqueId()) + " Tage")
                          + " " + (playTimeAPI.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTimeAPI.getHours(player.getUniqueId()) + " Stunden")
                          + " " + (playTimeAPI.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTimeAPI.getMinutes(player.getUniqueId()) + " Minuten");

        TextComponent color = this.formatTextColorArray(playTime, patterns);
        player.sendActionBar(color);
    }

    private void createMoneyAccount(UUID uuid, String name) {
        if (!(this.moneyAPI.hasAccount(uuid)))
            this.moneyAPI.createNewAccount(uuid, name);
    }

    private void createBankAccount(UUID uuid, String name) {
        if (!(this.bankAPI.hasAccount(uuid)))
            this.bankAPI.createNewAccount(uuid, name);
    }

}
