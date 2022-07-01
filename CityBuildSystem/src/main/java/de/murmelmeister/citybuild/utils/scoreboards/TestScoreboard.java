package de.murmelmeister.citybuild.utils.scoreboards;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.economy.MoneyAPI;
import de.murmelmeister.playtime.PlayTimeAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.UUID;

public class TestScoreboard extends ScoreboardBuilder {

    private final MoneyAPI moneyAPI = CityBuild.getInstance().getInitPlugin().getMoneyAPI();
    private final PlayTimeAPI playTimeAPI = CityBuild.getInstance().getInitPlugin().getPlayTimeAPI();

    private final DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");

    public TestScoreboard(Player player) {
        super(player, Component.text("GrieferGame", TextColor.color(241, 183, 25)));
        update();
    }

    @Override
    public void createScoreboard() {

        setScoreTeam(Component.text(""), 13);
        setScoreTeam(Component.text("§6⚔§8〡§7Spieler"), 12);
        setScoreTeam(Component.text("§e" + CityBuild.getInstance().getServer().getOnlinePlayers().size() + "§7/§e" + CityBuild.getInstance().getServer().getMaxPlayers()), 11);
        setScoreTeam(Component.text(""), 10);
        setScoreTeam(Component.text("§2⛃§8〡§7Kontostand"), 9);
        // Score 8 (Money)
        setScoreTeam(Component.text(""), 7);
        setScoreTeam(Component.text("§6⌚§8〡§7Spielzeit"), 6);
        // Score 5 (PlayTime)
        setScoreTeam(Component.text(""), 4);
        setScoreTeam(Component.text("§6✐§8〡§7Discord"), 3);
        setScoreTeam(Component.text("§eEkrFRN2FdE"), 2);
        setScoreTeam(Component.text(""), 1);

    }

    @Override
    public void update() {
        new BukkitRunnable() {

            @Override
            public void run() {
                removeScoreTeam(8);
                setScoreTeam(Component.text(handleMoney(moneyAPI, player) + "$", TextColor.color(0, 214, 95)), 8);
                removeScoreTeam(5);
                setScoreTeam(Component.text("§e" + (playTimeAPI.getHours(player.getUniqueId()) == 1 ? "1 Stunde" : playTimeAPI.getHours(player.getUniqueId()) + " Stunden")), 5);
            }
        }.runTaskTimer(CityBuild.getInstance(), 20, 2 * 20);
    }

    private String handleMoney(MoneyAPI moneyAPI, Player player) {
        UUID playerUUID = player.getUniqueId();

        // TODO: Money limit setzten

        if (moneyAPI.getMoney(playerUUID).doubleValue() >= 1000000000000L) {

            BigDecimal big = moneyAPI.getMoney(playerUUID);
            BigDecimal big2 = new BigDecimal(1000000000000L);
            big = big.divide(big2, MathContext.DECIMAL128);
            return decimalFormat.format(big.doubleValue()) + " Tri";
        } else if (moneyAPI.getMoney(playerUUID).doubleValue() >= 1000000000) {

            BigDecimal big = moneyAPI.getMoney(playerUUID);
            BigDecimal big2 = new BigDecimal(1000000000);
            big = big.divide(big2, MathContext.DECIMAL128);
            return decimalFormat.format(big.doubleValue()) + " Bio";
        } else if (moneyAPI.getMoney(playerUUID).doubleValue() >= 1000000) {

            BigDecimal big = moneyAPI.getMoney(playerUUID);
            BigDecimal big2 = new BigDecimal(1000000);
            big = big.divide(big2, MathContext.DECIMAL128);
            return decimalFormat.format(big.doubleValue()) + " Mio";
        } else if (moneyAPI.getMoney(playerUUID).doubleValue() >= 1000) {

            BigDecimal big = moneyAPI.getMoney(playerUUID);
            BigDecimal big2 = new BigDecimal(1000);
            big = big.divide(big2, MathContext.DECIMAL128);
            return decimalFormat.format(big.doubleValue()) + " k";
        }

        return decimalFormat.format(moneyAPI.getMoney(playerUUID));
    }

}
