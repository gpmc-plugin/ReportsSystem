package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Managers.NotificationManager.NotificationType;
import org.bukkit.OfflinePlayer;

public class NotificationObject {
    private final OfflinePlayer notificationSend;
    private final NotificationType notificationType;
    private final String[] args;
    public NotificationObject(OfflinePlayer notificationSend, NotificationType notificationType, String ... args){
        this.notificationSend = notificationSend;
        this.notificationType = notificationType;

        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public OfflinePlayer getNotificationSend() {
        return notificationSend;
    }
}
