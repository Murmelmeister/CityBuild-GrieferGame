package de.murmelmeister.citybuild.commands;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Homes;
import de.murmelmeister.citybuild.api.Locations;
import de.murmelmeister.citybuild.configs.Messages;
import de.murmelmeister.citybuild.utils.HexColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class CommandManager implements TabExecutor {

    protected CityBuild instance = CityBuild.getInstance();

    protected Messages messages = this.instance.getInitPlugin().getMessages();
    protected Locations locations = this.instance.getInitPlugin().getLocations();
    protected Homes homes = this.instance.getInitPlugin().getHomes();

    protected void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(this.messages.getPrefix() + HexColor.format(message));
    }

}
