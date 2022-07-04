package de.murmelmeister.citybuild.api;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Homes {

    private File folder;
    private File file;
    private YamlConfiguration config;

    public void createFile(UUID uuid) {
        setFolder(new File("plugins//GrieferGame//" + CityBuild.getInstance().getPluginName() + "//"));
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdirs();
            if (!(aBoolean))
                CityBuild.getInstance().getSLF4JLogger().warn("The folder cannot be created a second time.");
        }

        setFile(new File(getFolder(), uuid.toString() + ".yml"));
        if (!(getFile().exists())) {
            try {
                boolean aBoolean = getFile().createNewFile();
                if (!(aBoolean))
                    CityBuild.getInstance().getSLF4JLogger().warn("The file '" + uuid + ".yml' cannot be created a second time.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        setConfig(YamlConfiguration.loadConfiguration(getFile()));
    }

    public void saveFile() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasAccount(UUID uuid) {
        this.createFile(uuid);
        return this.getConfig().getString("Homes") != null;
    }

    public void createNewAccount(UUID uuid) {
        this.createFile(uuid);
        this.getConfig().set("Homes", null);
        this.saveFile();
    }

    public void deleteAccount(UUID uuid) {
        this.createFile(uuid);
        this.getConfig().set("Homes", null);
        this.saveFile();
    }

    public void setHome(UUID uuid, Location location, String nameHome) {
        this.createFile(uuid);
        double x = location.getBlockX() + 0.5D;
        double y = location.getBlockY() + 0.25D;
        double z = location.getBlockZ() + 0.5D;
        double yaw = (Math.round(location.getYaw() / 45.0F) * 45);
        double pitch = (Math.round(location.getPitch() / 45.0F) * 45);
        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        String worldLevel = location.getWorld().getEnvironment().name();

        ArrayList<String> homeNames = this.getHomeNames();
        homeNames.add(nameHome);
        this.getHomeNames().remove("null");
        this.getHomeNames().remove("[]");

        this.getConfig().set("Homes.Name", homeNames);
        this.getConfig().set("Homes." + nameHome + ".X", x);
        this.getConfig().set("Homes." + nameHome + ".Y", y);
        this.getConfig().set("Homes." + nameHome + ".Z", z);
        this.getConfig().set("Homes." + nameHome + ".Yaw", yaw);
        this.getConfig().set("Homes." + nameHome + ".Pitch", pitch);
        this.getConfig().set("Homes." + nameHome + ".WorldName", worldName);
        this.getConfig().set("Homes." + nameHome + ".WorldLevel", worldLevel);
        this.saveFile();
    }

    public Location getHome(UUID uuid, String nameHome) {
        this.createFile(uuid);
        double x = this.getConfig().getDouble("Homes." + nameHome + ".X");
        double y = this.getConfig().getDouble("Homes." + nameHome + ".Y");
        double z = this.getConfig().getDouble("Homes." + nameHome + ".Z");
        double yaw = this.getConfig().getDouble("Homes." + nameHome + ".Yaw");
        double pitch = this.getConfig().getDouble("Homes." + nameHome + ".Pitch");
        String worldName = this.getConfig().getString("Homes." + nameHome + ".WorldName");
        assert worldName != null;
        Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
        location.setYaw((float) yaw);
        location.setPitch((float) pitch);

        return location;
    }

    public boolean hasHomes(UUID uuid, String nameHome) {
        this.createFile(uuid);
        return this.getConfig().getString("Homes." + nameHome) != null;
    }

    public void removeHome(UUID uuid, String nameHome) {
        this.createFile(uuid);
        ArrayList<String> homeNames = this.getHomeNames();
        homeNames.remove(nameHome);

        this.getConfig().set("Homes." + nameHome, null);
        this.getConfig().set("Homes.Name", homeNames);
        this.saveFile();
    }

    public boolean hasHomeLimit(Player player) {
        if (!(player.hasPermission("citybuild.sethome.multiple.unlimited"))) {
            ArrayList<String> homeNames = this.getHomeNames();
            if (homeNames.size() >= 4) {
                player.sendMessage("§cDu hast die maximale Homes erreicht.");
                return false;
            }
            return true;
        }
        return true;
    }

    public ArrayList<String> getHomeNames() {
        ArrayList<String> homeNames = new ArrayList<>();
        if (this.getConfig().contains("Homes.Name")) {
            homeNames = (ArrayList<String>) this.getConfig().getStringList("Homes.Name");
        }
        return homeNames;
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
