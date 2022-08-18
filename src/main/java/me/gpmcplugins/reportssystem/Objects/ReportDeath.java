package me.gpmcplugins.reportssystem.Objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class ReportDeath {
    public Integer deathID;
    public Player noob;
    public String message_translate;
    public Date timestamp;

    public ReportDeath(Integer deathID,String noob,String message_translate,Long timestamp,ReportsSystem plugin){
        this.timestamp = new Date(timestamp);
        this.message_translate = message_translate;
        this.noob = plugin.getServer().getPlayer(UUID.fromString(noob));
        this.deathID=deathID;
    }
}
