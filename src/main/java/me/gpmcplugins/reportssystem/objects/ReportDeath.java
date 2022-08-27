package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class ReportDeath {
    private final Integer deathID;
    private final Player noob;
    private final String messageTranslate;
    private final Date timestamp;

    public ReportDeath(Integer deathID, String noob, String messageTranslate, Long timestamp, ReportsSystem plugin){
        this.timestamp = new Date(timestamp);
        this.messageTranslate = messageTranslate;
        this.noob = plugin.getServer().getPlayer(UUID.fromString(noob));
        this.deathID=deathID;
    }
    @SuppressWarnings("unused")
    public Date getTimestamp() {
        return timestamp;
    }
    @SuppressWarnings("unused")
    public Integer getDeathID() {
        return deathID;
    }

    public Player getNoob() {
        return noob;
    }

    public String getMessageTranslate() {
        return messageTranslate;
    }
}
