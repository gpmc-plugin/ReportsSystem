package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class ReportDeath {
    public Integer deathID;
    public Player noob;
    public String messageTranslate;
    public Date timestamp;

    public ReportDeath(Integer deathID, String noob, String messageTranslate, Long timestamp, ReportsSystem plugin){
        this.timestamp = new Date(timestamp);
        this.messageTranslate = messageTranslate;
        this.noob = plugin.getServer().getPlayer(UUID.fromString(noob));
        this.deathID=deathID;
    }
}
