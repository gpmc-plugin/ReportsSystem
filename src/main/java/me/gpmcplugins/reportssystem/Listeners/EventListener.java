package me.gpmcplugins.reportssystem.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.gpmcplugins.reportssystem.Statics.Adapters;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.awt.*;
import java.sql.SQLException;


public class EventListener implements Listener {
    private ReportsSystem plugin;
    public EventListener(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void onChatMessage(AsyncChatEvent e){
        Integer messageID=plugin.getDatabaseManager().getNextMessageID();
        plugin.getDatabaseManager().incrementNextMessageID();
        String sender = e.getPlayer().getUniqueId().toString();
        String message = ((TextComponent) e.message()).content();
        try {
            plugin.getDatabaseManager().logMessage(messageID,sender,"*",message);
        } catch (SQLException ex) {
            plugin.getDatabaseManager().throwError(ex.getMessage());
        }
    }
}
