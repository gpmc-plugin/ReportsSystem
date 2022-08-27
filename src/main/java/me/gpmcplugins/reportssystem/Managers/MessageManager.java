package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class MessageManager {
    public static void sendReportMessage(){
        Component message = Component.text("Powstały nowe reporty sprawdź /report-review", NamedTextColor.GREEN).clickEvent(ClickEvent.suggestCommand("/report-review"));
        for (Player onlinePlayer : ReportsSystem.getInstance().getServer().getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("reportsystem.notification"))
                onlinePlayer.sendMessage(message);
        }
    }
    public static void sendJoinMessage(Integer count){
        String reportOdmiana;
        String czekaOdmiana;
        if(count==1) {
            reportOdmiana = "report";
            czekaOdmiana="czeka";
        }
        else if(count<5) {
            reportOdmiana = "reporty";
            czekaOdmiana="czekają";
        }
        else {
            reportOdmiana = "reportów";
            czekaOdmiana="czeka";
        }
        Component message = Component.text("Do roboty! "+(count==100?"Co najmniej 100":count)+" "+reportOdmiana+" "+czekaOdmiana+". Sprawdź /report-review", NamedTextColor.GREEN).clickEvent(ClickEvent.suggestCommand("/report-review"));
        for (Player onlinePlayer : ReportsSystem.getInstance().getServer().getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("reportsystem.notification"))
                onlinePlayer.sendMessage(message);
        }
    }
    public static void sendUpdateMessage(ReportObject.ReportStatus status, Integer id, Player player){
        if(player.isOnline()){
            Component message = Component.text("Twój report o id: "+id+" został zaaktualizowany. Status został ustawiony na: "+status.toString(),NamedTextColor.GREEN);
            player.sendMessage(message);
            try {
                ReportsSystem.getInstance().getDatabaseManager().setReadStatus(id,true);
            } catch (SQLException e) {
                ReportsSystem.getInstance().getDatabaseManager().throwError(e.getMessage());
            }
        }
        else {
            try {
                ReportsSystem.getInstance().getDatabaseManager().setReadStatus(id,false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
