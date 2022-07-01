package de.murmelmeister.citybuild;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Main extends JavaPlugin {

    private String prefix;

    public abstract void onDisable();

    public abstract void onEnable();

    public abstract void onLoad();

    protected void handleDisableMessage() {
        this.getServer().getConsoleSender().sendMessage("§3" + this.getPluginName() + " §8» §7Plugin is§c disabled§7! §7Version: §e" + this.getVersion() + " §7by §b" + this.getAuthor());
    }

    protected void handleEnableMessage() {
        this.getServer().getConsoleSender().sendMessage("§3" + this.getPluginName() + " §8» §7Plugin is§a enabled§7! §7Version: §e" + this.getVersion() + " §7by §b" + this.getAuthor());
    }

    public String getVersion() {
        return "0.0.1-ALPHA-SNAPSHOT";
    }

    public String getAuthor() {
        return "Murmelmeister (LilaAtomBurger)";
    }

    public String getPluginName() {
        return "Lobby";
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
