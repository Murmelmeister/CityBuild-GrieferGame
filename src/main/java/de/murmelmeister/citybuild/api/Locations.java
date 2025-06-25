package de.murmelmeister.citybuild.api;

import de.murmelmeister.citybuild.CityBuild;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Locations {

    private File folder;
    private File file;
    private YamlConfiguration config;

    public void createFile() {
        setFolder(new File("plugins//GrieferGame//" + CityBuild.getInstance().getPluginName() + "//"));
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdirs();
            if (!(aBoolean))
                CityBuild.getInstance().getSLF4JLogger().warn("The folder cannot be created a second time.");
        }

        setFile(new File(getFolder(), "locations.yml"));
        if (!(getFile().exists())) {
            try {
                boolean aBoolean = getFile().createNewFile();
                if (!(aBoolean))
                    CityBuild.getInstance().getSLF4JLogger().warn("The file 'locations.yml' cannot be created a second time.");
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

    public void setLocation(String name, Location location) {
        createFile();
        double x = location.getBlockX() + 0.5D;
        double y = location.getBlockY();
        double z = location.getBlockZ() + 0.5D;
        double yaw = (Math.round(location.getYaw() / 45.0F) * 45);
        double pitch = (Math.round(location.getPitch() / 45.0F) * 45);
        String worldName = location.getWorld().getName();
        String environmentName = location.getWorld().getEnvironment().name();

        ArrayList<String> locationNames = this.getLocationNames();
        locationNames.add(name);
        getLocationNames().remove("null");
        getLocationNames().remove("[]");

        getConfig().set("Locations." + name + ".X", x);
        getConfig().set("Locations." + name + ".Y", y);
        getConfig().set("Locations." + name + ".Z", z);
        getConfig().set("Locations." + name + ".Yaw", yaw);
        getConfig().set("Locations." + name + ".Pitch", pitch);
        getConfig().set("Locations." + name + ".WorldName", worldName);
        getConfig().set("Locations." + name + ".EnvironmentName", environmentName);
        getConfig().set("Locations.Name", locationNames);
        saveFile();
    }

    public Location getLocation(String name) {
        createFile();
        double x = getConfig().getDouble("Locations." + name + ".X");
        double y = getConfig().getDouble("Locations." + name + ".Y");
        double z = getConfig().getDouble("Locations." + name + ".Z");
        double yaw = getConfig().getDouble("Locations." + name + ".Yaw");
        double pitch = getConfig().getDouble("Locations." + name + ".Pitch");
        String worldName = getConfig().getString("Locations." + name + ".WorldName");
        assert worldName != null;
        Location location = new Location(CityBuild.getInstance().getServer().getWorld(worldName), x, y, z);
        location.setYaw((float) yaw);
        location.setPitch((float) pitch);
        return location;
    }

    public void removeLocation(String name) {
        createFile();
        ArrayList<String> locationNames = this.getLocationNames();
        locationNames.remove(name);

        getConfig().set("Locations." + name + ".X", null);
        getConfig().set("Locations." + name + ".Y", null);
        getConfig().set("Locations." + name + ".Z", null);
        getConfig().set("Locations." + name + ".Yaw", null);
        getConfig().set("Locations." + name + ".Pitch", null);
        getConfig().set("Locations." + name + ".WorldName", null);
        getConfig().set("Locations." + name + ".EnvironmentName", null);
        getConfig().set("Locations.Name", locationNames);
        saveFile();
    }

    public void setSpawn(Location location) {
        createFile();
        double x = location.getBlockX() + 0.5D;
        double y = location.getBlockY();
        double z = location.getBlockZ() + 0.5D;
        double yaw = (Math.round(location.getYaw() / 45.0F) * 45);
        double pitch = (Math.round(location.getPitch() / 45.0F) * 45);
        String worldName = location.getWorld().getName();
        String environmentName = location.getWorld().getEnvironment().name();
        getConfig().set("Locations.Spawn.X", x);
        getConfig().set("Locations.Spawn.Y", y);
        getConfig().set("Locations.Spawn.Z", z);
        getConfig().set("Locations.Spawn.Yaw", yaw);
        getConfig().set("Locations.Spawn.Pitch", pitch);
        getConfig().set("Locations.Spawn.WorldName", worldName);
        getConfig().set("Locations.Spawn.EnvironmentName", environmentName);
        saveFile();
    }

    public Location getSpawn() {
        createFile();
        double x = getConfig().getDouble("Locations.Spawn.X");
        double y = getConfig().getDouble("Locations.Spawn.Y");
        double z = getConfig().getDouble("Locations.Spawn.Z");
        double yaw = getConfig().getDouble("Locations.Spawn.Yaw");
        double pitch = getConfig().getDouble("Locations.Spawn.Pitch");
        String worldName = getConfig().getString("Locations.Spawn.WorldName");
        assert worldName != null;
        Location location = new Location(CityBuild.getInstance().getServer().getWorld(worldName), x, y, z);
        location.setYaw((float) yaw);
        location.setPitch((float) pitch);
        return location;
    }

    public ArrayList<String> getLocationNames() {
        ArrayList<String> locationNames = new ArrayList<>();
        if (getConfig().contains("Locations.Name")) {
            locationNames = (ArrayList<String>) getConfig().getStringList("Locations.Name");
        }
        return locationNames;
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
