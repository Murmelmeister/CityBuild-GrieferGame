package de.murmelmeister.citybuild.listeners;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Homes;
import de.murmelmeister.citybuild.api.Locations;
import de.murmelmeister.citybuild.configs.Messages;
import de.murmelmeister.citybuild.utils.HexColor;
import de.murmelmeister.economy.BankAPI;
import de.murmelmeister.economy.MoneyAPI;
import de.murmelmeister.playtime.PlayTimeAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class Listeners implements Listener {

    protected CityBuild instance = CityBuild.getInstance();

    protected Messages messages = this.instance.getInitPlugin().getMessages();
    protected Locations locations = this.instance.getInitPlugin().getLocations();
    protected PlayTimeAPI playTimeAPI = this.instance.getInitPlugin().getPlayTimeAPI();
    protected MoneyAPI moneyAPI = this.instance.getInitPlugin().getMoneyAPI();
    protected BankAPI bankAPI = this.instance.getInitPlugin().getBankAPI();
    protected Homes homes = this.instance.getInitPlugin().getHomes();

    private ConnectListener connectListener;

    public void registerListeners() {
        setConnectListener(new ConnectListener());
        addListener(getConnectListener());
        addListener(new HomeListener());
    }

    private void addListener(Listener listener) {
        this.instance.getServer().getPluginManager().registerEvents(listener, this.instance);
    }

    protected void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(this.messages.getPrefix() + HexColor.format(message));
    }

    public ConnectListener getConnectListener() {
        return connectListener;
    }

    public void setConnectListener(ConnectListener connectListener) {
        this.connectListener = connectListener;
    }
}
