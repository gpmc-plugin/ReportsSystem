package me.gpmcplugins.reportssystem.Listeners;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.sql.SQLException;
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
            ReportObject report=null;
            try {
                report = plugin.getDatabaseManager().getReport(playerStatus.getLookingDeathId());
                e.setCancelled(report.reportStatus!= ReportObject.ReportStatus.Accepted);
            } catch (SQLException ex) {
                plugin.getDatabaseManager().throwError(ex.getMessage());
            }
            if(e.getAction()!= InventoryAction.MOVE_TO_OTHER_INVENTORY)
                e.setCancelled(true);
            if(!e.isCancelled()){
                if(e.getClickedInventory().getType()!=InventoryType.PLAYER){
                    Integer slot = e.getSlot();

                    try {
                        plugin.getConfig().load("item.yml");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvalidConfigurationException ex) {
                        throw new RuntimeException(ex);
                    }
                    plugin.getConfig().set(report.reportedID+"."+slot,null);
                    try {
                        plugin.getConfig().save("item.yml");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                    e.setCancelled(true);
            }

        }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = false)
    public void viewInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getPlayer() instanceof Player){
            PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(e.getPlayer().getUniqueId().toString());
            if(playerStatus==null)
                return;
            playerStatus.setLookingDeathId(null);
        }
    }
}
