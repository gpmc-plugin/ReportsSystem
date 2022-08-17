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
        Component message = Component.text("Powstał nowy report sprawdź /report-review", NamedTextColor.GREEN).clickEvent(ClickEvent.suggestCommand("/report-review"));
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            if(onlinePlayer.hasPermission("reportsystem.notification"))
                onlinePlayer.sendMessage(message);
        }
    }
}
