package de.murmelmeister.citybuild;

import de.murmelmeister.citybuild.api.Locations;
import de.murmelmeister.citybuild.commands.Commands;
import de.murmelmeister.citybuild.configs.Messages;
import de.murmelmeister.citybuild.configs.MySQL;
import de.murmelmeister.citybuild.listeners.Listeners;
import de.murmelmeister.economy.BankAPI;
import de.murmelmeister.economy.MoneyAPI;
import de.murmelmeister.playtime.PlayTimeAPI;

public class InitPlugin {

    private Messages messages;
    private Locations locations;
    private MySQL mySQL;

    private PlayTimeAPI playTimeAPI;
    private MoneyAPI moneyAPI;
    private BankAPI bankAPI;

    private Listeners listeners;
    private Commands commands;

    public void onDisable() {
        getMySQL().disconnect();
    }

    public void onEnable() {
        setMessages(new Messages());
        setLocations(new Locations());
        setMySQL(new MySQL());

        getMySQL().connectPlayTime();
        getMySQL().connectEconomyCB();

        setPlayTimeAPI(new PlayTimeAPI(getMySQL().getConnection()));
        setMoneyAPI(new MoneyAPI(getMySQL().getConnection()));
        setBankAPI(new BankAPI(getMySQL().getConnection()));

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

    public MySQL getMySQL() {
        return mySQL;
    }

    public void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
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
