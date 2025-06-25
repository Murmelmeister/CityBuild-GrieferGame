package de.murmelmeister.citybuild.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class HomeListener extends Listeners {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handlePlayerLogin(PlayerLoginEvent event) {
        if (!(this.homes.hasAccount(event.getPlayer().getUniqueId())))
            this.homes.createNewAccount(event.getPlayer().getUniqueId());
    }

}
