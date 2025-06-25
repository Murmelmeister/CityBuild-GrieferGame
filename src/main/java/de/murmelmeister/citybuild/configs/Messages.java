package de.murmelmeister.citybuild.configs;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.utils.HexColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Messages {

    private File folder;
    private File file;
    private YamlConfiguration config;

    public Messages() {
        createFile();
        saveFile();
    }

    public void createFile() {
        setFolder(new File("plugins//GrieferGame//" + CityBuild.class.getSimpleName() + "//"));
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdirs();
            if (!(aBoolean))
                CityBuild.getInstance().getSLF4JLogger().warn("The folder cannot be created a second time.");
        }

        setFile(new File(getFolder(), "messages.yml"));
        if (!(getFile().exists())) {
            try {
                boolean aBoolean = getFile().createNewFile();
                if (!(aBoolean))
                    CityBuild.getInstance().getSLF4JLogger().warn("The file 'message.yml' cannot be created a second time.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setConfig(YamlConfiguration.loadConfiguration(getFile()));
            firstConfig();
            return;
        }
        setConfig(YamlConfiguration.loadConfiguration(getFile()));
        loadConfig();
    }

    public void saveFile() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void firstConfig() {
        setConfigMessage("Prefix", "§8[§6GrieferGame§8] §r");
        setConfigMessage("Permission.SetSpawn", "citybuild.command.setspawn");
        setConfigMessage("Permission.Spawn", "citybuild.command.spawn");
        setConfigMessage("Permission.CityBuild.Use", "citybuild.command.citybuild.use");
        setConfigMessage("Permission.CityBuild.Reload", "citybuild.command.citybuild.reload");
        setConfigMessage("Message.NoPermission", "§cDazu hast du keine Rechte.");
        setConfigMessage("Message.NoConsole", "§cDieser Command funktioniert nicht in der Console.");
        setConfigMessage("Message.PlayerIsNotOnline", "§7Der Spieler §e[PLAYER] §7ist §c§nnicht §7auf diesem Server §cOnline§7.");
        setConfigMessage("Message.CityBuild.ReloadMessage", "§aDie Konfigurationen wurden neugeladen.");
        setConfigMessage("Message.CityBuild.Syntax", "§7Syntax: §c/lobbysystem [reload]");
        setConfigMessage("Message.SetSpawn", "§7Du hast den §aSpawn §7gesetzt.");
        setConfigMessage("Message.Spawn.Teleport", "§7Du hast dich zum §aSpawn §7teleportiert.");
    }

    private void loadConfig() {
        getConfigMessage("Prefix");
        getConfigMessage("Permission.SetSpawn");
        getConfigMessage("Permission.Spawn");
        getConfigMessage("Permission.CityBuild.Use");
        getConfigMessage("Permission.CityBuild.Reload");
        getConfigMessage("Message.NoPermission");
        getConfigMessage("Message.NoConsole");
        getConfigMessage("Message.PlayerIsNotOnline");
        getConfigMessage("Message.CityBuild.ReloadMessage");
        getConfigMessage("Message.CityBuild.Syntax");
        getConfigMessage("Message.SetSpawn");
        getConfigMessage("Message.Spawn.Teleport");
    }

    private void setConfigMessage(String path, Object value) {
        getConfig().set(path, value);
    }

    public String getConfigMessage(String path) {
        return getConfig().getString(HexColor.format(path));
    }

    public String getPrefix() {
        return getConfigMessage("Prefix");
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }
}
