package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerReportCreationStatus {
    private String uuid;
    private Player player;
    private ReportsSystem plugin;
    private Integer reportID = null;
    private Integer state=0;
    public PlayerReportCreationStatus(String uuid, ReportsSystem plugin){
        this.plugin=plugin;
        this.uuid=uuid;
        player = plugin.getServer().getPlayer(UUID.fromString(this.uuid));

    }
    public void clearReport(){
        reportID=null;
        state=0;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getReportID() {
        return reportID;
    }

    public Integer getState() {
        return state;
    }
}
