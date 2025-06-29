package de.murmelmeister.citybuild.commands;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.commands.economy.MoneyCommand;
import de.murmelmeister.citybuild.commands.economy.PayCommand;
import de.murmelmeister.citybuild.commands.homes.DeleteHomeCommand;
import de.murmelmeister.citybuild.commands.homes.HomeCommand;
import de.murmelmeister.citybuild.commands.homes.SetHomeCommand;
import de.murmelmeister.citybuild.commands.locations.*;
import org.bukkit.command.TabExecutor;

import java.util.Objects;

public class Commands {

    public void registerCommands() {
        addCommand("setspawn", new SetSpawnCommand());
        addCommand("spawn", new SpawnCommand());
        addCommand("citybuild", new CityBuildCommand());
        addCommand("deletehome", new DeleteHomeCommand());
        addCommand("home", new HomeCommand());
        addCommand("sethome", new SetHomeCommand());
        //addCommand("money", new MoneyCommand());
        addCommand("pay", new PayCommand());
        addCommand("deletewarp", new DeleteWarpCommand());
        addCommand("warp", new WarpCommand());
        addCommand("setwarp", new SetWarpCommand());
        addCommand("lobby", new LobbyCommand());
    }

    private void addCommand(String commandName, TabExecutor command) {
        Objects.requireNonNull(CityBuild.getInstance().getCommand(commandName)).setExecutor(command);
        Objects.requireNonNull(CityBuild.getInstance().getCommand(commandName)).setTabCompleter(command);
    }

}
