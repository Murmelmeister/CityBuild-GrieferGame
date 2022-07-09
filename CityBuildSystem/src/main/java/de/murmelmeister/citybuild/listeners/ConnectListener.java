package de.murmelmeister.citybuild.listeners;

import de.murmelmeister.citybuild.utils.scoreboards.TestScoreboard;
import de.murmelmeister.playtime.PlayTimeAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ConnectListener extends Listeners {

    private final TextColor[] pattern = new TextColor[]{TextColor.fromHexString("#8b0000"), TextColor.fromHexString("#970d06"), TextColor.fromHexString("#a4180b"), TextColor.fromHexString("#b12210"), TextColor.fromHexString("#bd2b15"), TextColor.fromHexString("#ca331a"), TextColor.fromHexString("#d73c1f"), TextColor.fromHexString("#e44424"), TextColor.fromHexString("#f24d29"), TextColor.fromHexString("#ff552e"),
            TextColor.fromHexString("#f24d29"), TextColor.fromHexString("#e44424"), TextColor.fromHexString("#d73c1f"), TextColor.fromHexString("#ca331a"), TextColor.fromHexString("#bd2b15"), TextColor.fromHexString("#b12210"), TextColor.fromHexString("#a4180b"), TextColor.fromHexString("#970d06"), TextColor.fromHexString("#8b0000")};

    private final List<TextColor> textColorList = Arrays.asList(TextColor.fromHexString("#01b416"), TextColor.fromHexString("#0dbc2b"), TextColor.fromHexString("#17c43b"), TextColor.fromHexString("#1fcd4a"), TextColor.fromHexString("#26d558"), TextColor.fromHexString("#2ddd65"), TextColor.fromHexString("#34e673"), TextColor.fromHexString("#3bee80"), TextColor.fromHexString("#42f78d"), TextColor.fromHexString("#49ff9a"),
            TextColor.fromHexString("#42f78d"), TextColor.fromHexString("#3bee80"), TextColor.fromHexString("#34e673"), TextColor.fromHexString("#2ddd65"), TextColor.fromHexString("#26d558"), TextColor.fromHexString("#1fcd4a"), TextColor.fromHexString("#17c43b"), TextColor.fromHexString("#0dbc2b"), TextColor.fromHexString("#01b416"));

    private final Map<UUID, Integer> counts = new HashMap<>();
    private final Map<UUID, BukkitTask> tasks = new HashMap<>();
    private final List<List<TextColor>> gradientStates = new ArrayList<>();

    private TextComponent formatTextColor(String input, TextColor[] colors) {
        TextComponent component = Component.empty();
        char[] chars = input.toCharArray();
        int count = 0;

        for (char character : chars) {
            if (count >= colors.length) count = 0;
            TextColor color = colors[count];
            component = component.append(Component.text(character).color(color));
            count++;
        }
        return component.decoration(TextDecoration.ITALIC, false);
    }

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
        //updateActionBars(playTimeAPI, player);

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

    private void setActionBar(PlayTimeAPI playTime, Player player) {
        if ((playTime.getYears(player.getUniqueId()) == 0)
                && (playTime.getDays(player.getUniqueId()) == 0)
                && (playTime.getHours(player.getUniqueId()) == 0)) {
            player.sendActionBar(Component.text("Spielzeit: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), TextColor.color(0xE7D606))));

        } else if ((playTime.getYears(player.getUniqueId()) == 0)
                && (playTime.getDays(player.getUniqueId()) == 0)) {
            player.sendActionBar(Component.text("Spielzeit: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTime.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), TextColor.color(0xE7D606))));

        } else if (playTime.getYears(player.getUniqueId()) == 0) {
            player.sendActionBar(Component.text("Spielzeit: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getDays(player.getUniqueId()) == 1 ? "1 Tag" : playTime.getDays(player.getUniqueId()) + " Tage")
                            + " " + (playTime.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTime.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), TextColor.color(0xE7D606))));
        } else {
            player.sendActionBar(Component.text("Spielzeit: ", TextColor.color(0xcccccc))
                    .append(Component.text((playTime.getYears(player.getUniqueId()) == 1 ? "1 Jahr" : playTime.getYears(player.getUniqueId()) + " Jahre")
                            + " " + (playTime.getDays(player.getUniqueId()) == 1 ? "1 Tag" : playTime.getDays(player.getUniqueId()) + " Tage")
                            + " " + (playTime.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTime.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), TextColor.color(0xE7D606))));
        }
    }

    private void setActionBar(PlayTimeAPI playTime, Player player, TextColor color) {
        if ((playTime.getYears(player.getUniqueId()) == 0)
                && (playTime.getDays(player.getUniqueId()) == 0)
                && (playTime.getHours(player.getUniqueId()) == 0)) {
            player.sendActionBar(Component.text("Spielzeit: ", color)
                    .append(Component.text((playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), color)));

        } else if ((playTime.getYears(player.getUniqueId()) == 0)
                && (playTime.getDays(player.getUniqueId()) == 0)) {
            player.sendActionBar(Component.text("Spielzeit: ", color)
                    .append(Component.text((playTime.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTime.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), color)));

        } else if (playTime.getYears(player.getUniqueId()) == 0) {
            player.sendActionBar(Component.text("Spielzeit: ", color)
                    .append(Component.text((playTime.getDays(player.getUniqueId()) == 1 ? "1 Tag" : playTime.getDays(player.getUniqueId()) + " Tage")
                            + " " + (playTime.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTime.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), color)));
        } else {
            player.sendActionBar(Component.text("Spielzeit: ", color)
                    .append(Component.text((playTime.getYears(player.getUniqueId()) == 1 ? "1 Jahr" : playTime.getYears(player.getUniqueId()) + " Jahre")
                            + " " + (playTime.getDays(player.getUniqueId()) == 1 ? "1 Tag" : playTime.getDays(player.getUniqueId()) + " Tage")
                            + " " + (playTime.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTime.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTime.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTime.getMinutes(player.getUniqueId()) + " Minuten"), color)));
        }
    }

    public void updateActionBar(PlayTimeAPI playTime) {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player all : instance.getServer().getOnlinePlayers()) {
                    //setActionBar(playTime, all);
                    updateActionBars(playTime, all);
                }

            }
        }.runTaskTimerAsynchronously(this.instance, 20L, 2 * 20L);
    }

    public void updateActionBars(PlayTimeAPI playTimeAPI, Player player) {
        List<List<TextColor>> states = new ArrayList<>();
        List<TextColor> patterns = this.textColorList;

        if (tasks.containsKey(player.getUniqueId())) this.stopAnimation(player);
        counts.put(player.getUniqueId(), 0);
        BukkitTask task = this.instance.getServer().getScheduler().runTaskTimerAsynchronously(this.instance, () -> {
            for (int i = 0; i < patterns.size(); i++) {
                Collections.rotate(patterns, i);
                states.add(patterns);
            }

            TextComponent color = this.formatTextColorArray("Spielzeit: " + (playTimeAPI.getYears(player.getUniqueId()) == 1 ? "1 Jahr" : playTimeAPI.getYears(player.getUniqueId()) + " Jahre")
                            + " " + (playTimeAPI.getDays(player.getUniqueId()) == 1 ? "1 Tag" : playTimeAPI.getDays(player.getUniqueId()) + " Tage")
                            + " " + (playTimeAPI.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTimeAPI.getHours(player.getUniqueId()) + " Stunden")
                            + " " + (playTimeAPI.getMinutes(player.getUniqueId()) == 1 ? "1 Minute" : playTimeAPI.getMinutes(player.getUniqueId()) + " Minuten"),
                    states.get(counts.get(player.getUniqueId())));
            player.sendMessage(color);
            player.sendActionBar(color);
            counts.put(player.getUniqueId(), counts.get(player.getUniqueId()) + 1);
            if (counts.get(player.getUniqueId()) >= states.size()) counts.put(player.getUniqueId(), 0);
        }, 10, 2);
        tasks.put(player.getUniqueId(), task);
    }

    public void stopAnimation(Player player) {
        UUID uuid = player.getUniqueId();
        if (!(tasks.containsKey(uuid))) return;
        tasks.get(uuid).cancel();
        tasks.remove(uuid);
        counts.remove(uuid);
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
