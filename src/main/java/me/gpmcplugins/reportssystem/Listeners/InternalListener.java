package me.gpmcplugins.reportssystem.Listeners;

import me.gpmcplugins.reportssystem.Discord.SendCreatedCommit;
import me.gpmcplugins.reportssystem.Events.ReportCreateEvent;
import me.gpmcplugins.reportssystem.Events.ReportUpdateEvent;
import me.gpmcplugins.reportssystem.Managers.MessageManager;
import me.gpmcplugins.reportssystem.Managers.NotificationManager;
import me.gpmcplugins.reportssystem.objects.NotificationObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class InternalListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreateReport(ReportCreateEvent e) {
        SendCreatedCommit.sendReportToDiscord(e.getReport());
        MessageManager.sendReportMessage();
        String uuid = e.getPlayer().getUniqueId().toString();
        ReportsSystem.getInstance().getStorageManager().getUser(uuid).sendReportCreated(e.getReport().getId());
    }
    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onReportUpdate(ReportUpdateEvent e){
        switch (e.getChangeType()){
            case AdminChange:
                if(e.getNewState().getAdmin()!=null){
                    NotificationManager.sendNotification(new NotificationObject(e.getNewState().getReportingUser(), NotificationManager.NotificationType.ReportAdminChange,e.getNewState().getId().toString(),e.getNewState().getAdmin().getName()));
                }
                break;
            case StateChange:
                NotificationManager.sendNotification(new NotificationObject(e.getNewState().getReportingUser(), NotificationManager.NotificationType.ReportStateChange,e.getNewState().getId().toString(),e.getNewState().getReportStatus().toString()));
        }
    }
}
