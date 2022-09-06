package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.Configs.NotificationConfig;
import me.gpmcplugins.reportssystem.objects.NotificationConfigObject;
import me.gpmcplugins.reportssystem.objects.NotificationObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotificationManager {
    @SuppressWarnings("unused")
    public static NotificationSendStatus sendNotification(NotificationObject notificationObject){
        NotificationConfigObject config = NotificationConfig.getInstance().getTypeConfig(notificationObject.getNotificationType());
        OfflinePlayer player = notificationObject.getNotificationSend();
        if(player.isOnline()) {
            Player ofplayer = player.getPlayer();
            assert ofplayer != null;
            ofplayer.sendMessage(Component.text(String.format(config.getTranslatableString(), (Object[]) notificationObject.getArgs())));
            queueNotification(notificationObject,true);
            return NotificationSendStatus.Sended;
        } else if (config.getCanBeDelayed()) {
                queueNotification(notificationObject,false);
            return NotificationSendStatus.Queued;
        }
        else
            queueNotification(notificationObject,true);
        return NotificationSendStatus.Not_Sended;
    }
    public static void queueNotification(NotificationObject notificationObject,boolean readed)  {
        Connection connection = ReportsSystem.getInstance().getDatabaseManager().getConn();
        String sql = "insert into notification (userid, notificationKey, args, readed, timestamp) values (?,?,?,?,?);";
        String[] args =notificationObject.getArgs();
        for (int i = 0; i < args.length; i++) {
            args[i]=args[i].replaceAll(";","");
        }
        String sqlArray = String.join(";",args);
        PreparedStatement prstm;
        try {
            prstm = connection.prepareStatement(sql);
             prstm.setString(1,notificationObject.getNotificationSend().getUniqueId().toString());
             prstm.setString(2,notificationObject.getNotificationType().toString());
             prstm.setString(3,sqlArray);
             prstm.setInt(1,readed?1:0);
             prstm.setLong(1,new Date().getTime());
             prstm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @SuppressWarnings("unused")
    public static List<NotificationObject> getUnreadedNotifications(String userid){
        List<NotificationObject> notificationObjects = new ArrayList<>();
        Connection connection = ReportsSystem.getInstance().getDatabaseManager().getConn();
        String sql = "Select * FROM notification WHERE userid=? AND readed=0";
        try {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            prstmt.setString(1,userid);
            ResultSet rs = prstmt.executeQuery();
            while (rs.next()){
                String[] args = rs.getString("args").split(";");
                NotificationType type = NotificationType.valueOf(rs.getString("notificationKey"));
                Player player = ReportsSystem.getInstance().getServer().getPlayer(UUID.fromString(userid));
                notificationObjects.add(new NotificationObject(player,type,args));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notificationObjects;
    }
    public enum NotificationType{
        ReportAdminChange,
        ReportStateChange
    }
    public enum NotificationSendStatus{
        Sended,
        Queued,
        Not_Sended
    }

    }

