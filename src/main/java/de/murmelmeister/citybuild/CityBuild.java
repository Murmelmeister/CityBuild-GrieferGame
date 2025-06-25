package de.murmelmeister.citybuild;

import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.commands.Commands;
import de.murmelmeister.citybuild.configs.Messages;
import de.murmelmeister.citybuild.configs.mysqls.EcoMySQL;
import de.murmelmeister.citybuild.configs.mysqls.PTMySQL;
import de.murmelmeister.citybuild.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class CityBuild extends JavaPlugin {
    private Messages messages;
    private Locations locations;
    private Homes homes;
    private PTMySQL ptMySQL;
    private EcoMySQL ecoMySQL;

    private PlayTimeAPI playTimeAPI;
    private MoneyAPI moneyAPI;
    private BankAPI bankAPI;

    private Listeners listeners;
    private Commands commands;

    @Override
    public void onDisable() {
        ptMySQL.disconnect();
        ecoMySQL.disconnect();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }

    @Override
    public void onEnable() {
        ptMySQL.connectPlayTime();
        ecoMySQL.connectEconomyCB();

        playTimeAPI = new PlayTimeAPI(ptMySQL.getConnection());
        moneyAPI = new MoneyAPI(ecoMySQL.getConnection());
        bankAPI = new BankAPI(ecoMySQL.getConnection());

        playTimeAPI.createTable();
        moneyAPI.createTable();
        bankAPI.createTable();

        listeners = new Listeners();
        commands = new Commands();
        listeners.registerListeners();
        commands.registerCommands();

        //getListeners().getConnectListener().updateActionBar(getPlayTimeAPI());
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onLoad() {
        messages = new Messages();
        locations = new Locations();
        homes = new Homes();
        ptMySQL = new PTMySQL();
        ecoMySQL = new EcoMySQL();
    }

    public static CityBuild getInstance() {
        return getPlugin(CityBuild.class);
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public PTMySQL getPtMySQL() {
        return ptMySQL;
    }

    public void setPtMySQL(PTMySQL ptMySQL) {
        this.ptMySQL = ptMySQL;
    }

    public EcoMySQL getEcoMySQL() {
        return ecoMySQL;
    }

    public void setEcoMySQL(EcoMySQL ecoMySQL) {
        this.ecoMySQL = ecoMySQL;
    }

    public PlayTimeAPI getPlayTimeAPI() {
        return playTimeAPI;
    }

    public void setPlayTimeAPI(PlayTimeAPI playTimeAPI) {
        this.playTimeAPI = playTimeAPI;
    }

    public MoneyAPI getMoneyAPI() {
        return moneyAPI;
    }

    public void setMoneyAPI(MoneyAPI moneyAPI) {
        this.moneyAPI = moneyAPI;
    }

    public BankAPI getBankAPI() {
        return bankAPI;
    }

    public void setBankAPI(BankAPI bankAPI) {
        this.bankAPI = bankAPI;
    }

    public Listeners getListeners() {
        return listeners;
    }

    public void setListeners(Listeners listeners) {
        this.listeners = listeners;
    }

    public Commands getCommands() {
        return commands;
    }

    public void setCommands(Commands commands) {
        this.commands = commands;
    }

    public Homes getHomes() {
        return homes;
    }

    public void setHomes(Homes homes) {
        this.homes = homes;
    }
}
