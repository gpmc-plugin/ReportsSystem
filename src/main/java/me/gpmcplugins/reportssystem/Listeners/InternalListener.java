package me.gpmcplugins.reportssystem.Listeners;

import me.gpmcplugins.reportssystem.Events.ReportCreateEvent;
import me.gpmcplugins.reportssystem.Managers.MessageManager;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class InternalListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onCreateReport(ReportCreateEvent e){
        MessageManager.sendReportMessage();
        String uuid = e.getPlayer().getUniqueId().toString();
        ReportsSystem.getInstance().getStorageManager().getUser(uuid).sendReportCreated(e.getReport().getId());
    }
}
