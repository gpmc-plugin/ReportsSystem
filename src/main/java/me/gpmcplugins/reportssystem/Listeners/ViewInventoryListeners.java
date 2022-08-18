package me.gpmcplugins.reportssystem.Listeners;

import me.gpmcplugins.reportssystem.Objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.Objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ViewInventoryListeners  implements Listener {
    private final ReportsSystem plugin;
    public ViewInventoryListeners(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void viewInventoryClickEvent(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
        PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(e.getWhoClicked().getUniqueId().toString());
        if(playerStatus.getLookingDeathId()!=null){
            //Player player = (Player) e.getWhoClicked();
            ReportObject report=null;
            Player player = (Player) e.getWhoClicked();
            try {
                report = plugin.getDatabaseManager().getReport(playerStatus.getLookingDeathId());
                if(!player.hasPermission("reportsystem.reportreview"))
                    e.setCancelled(report.reportStatus!= ReportObject.ReportStatus.Accepted);
            } catch (SQLException ex) {
                plugin.getDatabaseManager().throwError(ex.getMessage());
            }
            if(e.getAction()!= InventoryAction.MOVE_TO_OTHER_INVENTORY)
                e.setCancelled(true);
            if(!e.isCancelled()){
                if(Objects.requireNonNull(e.getClickedInventory()).getType()!=InventoryType.PLAYER){
                    int slot = e.getSlot();

                    try {
                        plugin.getConfig().load("item.yml");
                    } catch (IOException | InvalidConfigurationException ex) {
                        throw new RuntimeException(ex);
                    }
                    assert report != null;
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
    @EventHandler(priority = EventPriority.LOWEST)
    public void viewInventoryCloseEvent(InventoryCloseEvent e){
        if(e.getPlayer() instanceof Player){
            PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(e.getPlayer().getUniqueId().toString());
            if(playerStatus==null)
                return;
            playerStatus.setLookingDeathId(null);
        }
    }
}
