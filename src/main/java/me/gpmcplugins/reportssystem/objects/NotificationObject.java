package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Managers.NotificationManager.NotificationType;
import org.bukkit.entity.Player;

public class NotificationObject {
    private final Player notificationSend;
    private final NotificationType notificationType;
    private final String[] args;
    public NotificationObject(Player notificationSend, NotificationType notificationType, String ... args){
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

    public Player getNotificationSend() {
        return notificationSend;
    }
}
