package de.murmelmeister.citybuild.commands;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Homes;
import de.murmelmeister.citybuild.api.Locations;
import de.murmelmeister.citybuild.api.MoneyAPI;
import de.murmelmeister.citybuild.configs.Messages;
import de.murmelmeister.citybuild.utils.HexColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.text.DecimalFormat;

public abstract class CommandManager implements TabExecutor {

    protected CityBuild instance = CityBuild.getInstance();

    protected Messages messages = this.instance.getMessages();
    protected Locations locations = this.instance.getLocations();
    protected Homes homes = this.instance.getHomes();
    protected MoneyAPI moneyAPI = this.instance.getMoneyAPI();

    protected DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");

    protected void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(this.messages.getPrefix() + HexColor.format(message));
    }

}
