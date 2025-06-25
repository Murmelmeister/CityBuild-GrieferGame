package de.murmelmeister.citybuild.configs.mysqls;

import de.murmelmeister.citybuild.CityBuild;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EcoMySQL {

    private File folder;
    private File file;
    private YamlConfiguration config;

    public EcoMySQL() {
        createFile();
        saveFile();
    }

    public void createFile() {
        setFolder(new File("plugins//GrieferGame//" + CityBuild.class.getSimpleName() + "//MySQL//"));
        if (!(getFolder().exists())) {
            boolean aBoolean = getFolder().mkdirs();
            if (!(aBoolean)) CityBuild.getInstance().getSLF4JLogger().warn("The folder cannot be created a second time.");
        }

        setFile(new File(getFolder(), "economy.yml"));
        if (!(getFile().exists())) {
            try {
                boolean aBoolean = getFile().createNewFile();
                if (!(aBoolean))
                    CityBuild.getInstance().getSLF4JLogger().warn("The file 'economy.yml' cannot be created a second time.");
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
        this.getConfig().set("EconomyCB.Hostname", "localhost");
        this.getConfig().set("EconomyCB.Port", 3306);
        this.getConfig().set("EconomyCB.Database", "EconomyCBBase");
        this.getConfig().set("EconomyCB.Username", "root");
        this.getConfig().set("EconomyCB.Password", "12345");
    }

    private void loadConfig() {
        this.getConfig().getString("EconomyCB.Hostname");
        this.getConfig().getInt("EconomyCB.Port");
        this.getConfig().getString("EconomyCB.Database");
        this.getConfig().getString("EconomyCB.Username");
        this.getConfig().getString("EconomyCB.Password");
    }

    private Connection connection;

    public void connectEconomyCB() {
        if (!isConnected()) {
            try {
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.getConfig().getString("EconomyCB.Hostname") + ":" + this.getConfig().getInt("EconomyCB.Port") + "/"
                                + this.getConfig().getString("EconomyCB.Database") + "?autoReconnect=true&useUnicode=yes",
                        this.getConfig().getString("EconomyCB.Username"), this.getConfig().getString("EconomyCB.Password")));
                CityBuild.getInstance().getSLF4JLogger().info("MySQL connected!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                getConnection().close();
                CityBuild.getInstance().getSLF4JLogger().warn("MySQL connection closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return this.connection == null ? false : true;
    }
}
