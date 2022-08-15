package me.gpmcplugins.reportssystem.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.gpmcplugins.reportssystem.Statics.Adapters;
import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        TextComponent messageComponent = (TextComponent) e.message();
        messageComponent = messageComponent.clickEvent(ClickEvent.runCommand("/report message "+messageID));
        messageComponent = messageComponent.hoverEvent(HoverEvent.showText(Component.text("Aby zgłosić tą wiadomość kliknij ją", NamedTextColor.YELLOW, TextDecoration.BOLD)));
        e.message(messageComponent);
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        plugin.getStorageManager().addUser(player.getUniqueId().toString());
    }
    public void onPlayerQuit(PlayerQuitEvent e){
        plugin.getStorageManager().removeUser(e.getPlayer().getUniqueId().toString());
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void detectingDescriptionSave(AsyncChatEvent e){
        Player player = e.getPlayer();
        PlayerReportCreationStatus playerReport = plugin.getStorageManager().getUser(player.getUniqueId().toString());
        if(playerReport.getReportID()!=null){
            if(playerReport.getState()==1){
                ReportCreator report = plugin.getStorageManager().getReport(playerReport.getReportID());
                report.setDescription(((TextComponent)e.message()).content());
                playerReport.sendSavedMessage();
                playerReport.setState(2);
                playerReport.sendSummary(report);
                e.setCancelled(true);
            }
        }
    }
}
