package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class ReportDeath {
    public Integer deathID;
    public Player noob;
    public String message_translate;
    public Date timestamp;
    private ReportsSystem plugin;
    public ReportDeath(Integer deathID,String noob,String message_translate,Long timestamp,ReportsSystem plugin){
        this.plugin=plugin;
        this.timestamp = new Date(timestamp);
        this.message_translate = message_translate;
        this.noob = plugin.getServer().getPlayer(UUID.fromString(noob));
        this.deathID=deathID;
    }
}
