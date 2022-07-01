package de.murmelmeister.citybuild.commands;

import de.murmelmeister.citybuild.CityBuild;
import org.bukkit.command.TabExecutor;

import java.util.Objects;

public class Commands {

    public void registerCommands() {
        addCommand("setspawn", new SetSpawnCommand());
        addCommand("spawn", new SpawnCommand());
        addCommand("citybuild", new CityBuildCommand());
    }

    private void addCommand(String commandName, TabExecutor command) {
        Objects.requireNonNull(CityBuild.getInstance().getCommand(commandName)).setExecutor(command);
        Objects.requireNonNull(CityBuild.getInstance().getCommand(commandName)).setTabCompleter(command);
    }

}
