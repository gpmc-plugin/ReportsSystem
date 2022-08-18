package me.gpmcplugins.reportssystem.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.gpmcplugins.reportssystem.Managers.MessageManager;
import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class EventListener implements Listener {
    private final ReportsSystem plugin;
    public EventListener(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatMessage(AsyncChatEvent e){
        int messageID=plugin.getDatabaseManager().getNextMessageID();
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
        messageComponent = messageComponent.hoverEvent(HoverEvent.showText(Component.text("Aby zgłosić tą wiadomość kliknij ją", NamedTextColor.YELLOW, TextDecoration.ITALIC)));
        e.message(messageComponent);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        plugin.getStorageManager().addUser(player.getUniqueId().toString());
        if(player.hasPermission("reportsystem.notification")){
            List<ReportObject> reports;
            try {
                reports = plugin.getDatabaseManager().getAdminReports(null,100,0,true);
                List<ReportObject> userreports = plugin.getDatabaseManager().getAdminReports(player.getUniqueId().toString(),100,0,true);
                int reportslenght = reports.size()+userreports.size();
                if(reportslenght>100)
                    reportslenght=100;
                if(reportslenght!=0){
                    new MessageManager(plugin).sendJoinMessage(reportslenght);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


        }
        try {
            List<Integer> unreadReports = plugin.getDatabaseManager().getNonReadMessages(player.getUniqueId().toString());
            for (Integer unreadReport : unreadReports) {
                ReportObject reportObject = plugin.getDatabaseManager().getReport(unreadReport);
                new MessageManager(plugin).sendUpdateMessage(reportObject.reportStatus,unreadReport,player);
            }
        } catch (SQLException ex) {
            plugin.getDatabaseManager().throwError(ex.getMessage());
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        Integer deathID=plugin.getDatabaseManager().getNextDeathID();
        Player player = e.getPlayer();
        plugin.getDatabaseManager().incrementNextDeathID();
        TranslatableComponent deathMessage = (TranslatableComponent) e.deathMessage();
        try {
            assert deathMessage != null;
            plugin.getDatabaseManager().logDeath(deathID,player.getUniqueId().toString(),deathMessage.key());
        } catch (SQLException ex) {
            plugin.getDatabaseManager().throwError(ex.getMessage());
            player.sendMessage("Coś poszło nie tak nie możesz zgłosić swojej śmierci");
            return;
        }
        List<ItemStack> drops = e.getDrops();
        for (int i = 0; i < drops.size(); i++) {
            player.sendMessage("seted");
            plugin.getConfig().set(deathID+"."+i,drops.get(i));
        }
        try {
            plugin.getConfig().save("item.yml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        TextComponent messageTextComponent = Component.empty()
            .content("[Kliknij tutaj aby zglosic ze smierc jest niesluzna]")
            .color(NamedTextColor.AQUA)
            .hoverEvent(HoverEvent.showText(Component.text("Smierc jest niesluszna gdy zginales w wyniku zlamania regulaminu przez osobe ktora cie zabila, lub gdy zginales przez blad gry lub pluginu",
                    NamedTextColor.YELLOW, TextDecoration.ITALIC)))
            .clickEvent(ClickEvent.runCommand("/report death "+deathID));
        player.sendMessage(messageTextComponent);

    }
}
