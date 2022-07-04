package de.murmelmeister.citybuild;

import de.murmelmeister.citybuild.api.Locations;
import de.murmelmeister.citybuild.commands.Commands;
import de.murmelmeister.citybuild.configs.Messages;
import de.murmelmeister.citybuild.configs.mysqls.EcoMySQL;
import de.murmelmeister.citybuild.configs.mysqls.PTMySQL;
import de.murmelmeister.citybuild.listeners.Listeners;
import de.murmelmeister.economy.BankAPI;
import de.murmelmeister.economy.MoneyAPI;
import de.murmelmeister.playtime.PlayTimeAPI;

public class InitPlugin {

    private Messages messages;
    private Locations locations;
    private PTMySQL ptMySQL;
    private EcoMySQL ecoMySQL;

    private PlayTimeAPI playTimeAPI;
    private MoneyAPI moneyAPI;
    private BankAPI bankAPI;

    private Listeners listeners;
    private Commands commands;

    public void onDisable() {
        getPtMySQL().disconnect();
        getEcoMySQL().disconnect();
    }

    public void onEnable() {
        setMessages(new Messages());
        setLocations(new Locations());
        setPtMySQL(new PTMySQL());
        setEcoMySQL(new EcoMySQL());

        getPtMySQL().connectPlayTime();
        getEcoMySQL().connectEconomyCB();

        setPlayTimeAPI(new PlayTimeAPI(getPtMySQL().getConnection()));
        setMoneyAPI(new MoneyAPI(getEcoMySQL().getConnection()));
        setBankAPI(new BankAPI(getEcoMySQL().getConnection()));

        getMoneyAPI().createTable();
        getBankAPI().createTable();

        setListeners(new Listeners());
        setCommands(new Commands());
        getListeners().registerListeners();
        getCommands().registerCommands();
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
}
