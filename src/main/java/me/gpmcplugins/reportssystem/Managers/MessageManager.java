package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class MessageManager {
    private final ReportsSystem plugin;
    public MessageManager(ReportsSystem plugin){
        this.plugin=plugin;
    }
    public void sendReportMessage(){
        Component message = Component.text("Powstały nowe reporty sprawdź /report-review", NamedTextColor.GREEN).clickEvent(ClickEvent.suggestCommand("/report-review"));
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("reportsystem.notification"))
                onlinePlayer.sendMessage(message);
        }
    }
    public void sendJoinMessage(Integer count){
        String reportOdmiana;
        String czekaOdmiana;
        if(count==1) {
            reportOdmiana = "report";
            czekaOdmiana="czeka";
        }
        else if(count<10) {
            reportOdmiana = "reporty";
            czekaOdmiana="czekają";
        }
        else {
            reportOdmiana = "reportów";
            czekaOdmiana="czeka";
        }
        Component message = Component.text("Do roboty! "+(count==100?"Co najmniej 100":count)+" "+reportOdmiana+" "+czekaOdmiana+". Sprawdź /report-review", NamedTextColor.GREEN).clickEvent(ClickEvent.suggestCommand("/report-review"));
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("reportsystem.notification"))
                onlinePlayer.sendMessage(message);
        }
    }
}
