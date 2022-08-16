package me.gpmcplugins.reportssystem.Listeners;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class ViewInventoryListeners  implements Listener {
    private ReportsSystem plugin;
    public ViewInventoryListeners(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void viewInventoryClickEvent(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
        PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(e.getWhoClicked().getUniqueId().toString());
        if(playerStatus.getLookingDeathId()!=null){
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(!player.hasPermission("reportsystem.reportreview"));
        }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = false)
    public void viewInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getPlayer() instanceof Player){
            PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(e.getPlayer().getUniqueId().toString());
            playerStatus.setState(null);
        }
    }
}
