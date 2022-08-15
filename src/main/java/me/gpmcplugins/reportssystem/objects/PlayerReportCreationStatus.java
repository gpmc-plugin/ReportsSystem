package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

public class PlayerReportCreationStatus {
    private String uuid;
    private Player player;
    private ReportsSystem plugin;
    public PlayerReportCreationStatus(String uuid, ReportsSystem plugin){
        this.plugin=plugin;
        this.uuid=uuid;

    }
}
