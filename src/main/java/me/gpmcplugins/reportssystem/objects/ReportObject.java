package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class ReportObject {
    public Integer id;
    public Player reportingUser;
    public ReportsSystem plugin;
    public ReportCreator.ReportType type;
    public ReportCreator.ReportShortDescription shortDescription;
    public String reportedID;
    public String description;
    public Player admin;
    public Date timestamp;
    public ReportObject(Integer id, String reportingUser, String type, String shortDescription, String reportedID,String description, String admin, Long timestamp, ReportsSystem plugin){
        this.plugin = plugin;
        this.reportingUser = plugin.getServer().getPlayer(UUID.fromString(reportingUser));
        this.type = ReportCreator.ReportType.valueOf(type);
        this.shortDescription = ReportCreator.ReportShortDescription.valueOf(shortDescription);
        this.reportedID = reportedID;
        this.description=description;
        this.admin = plugin.getServer().getPlayer(UUID.fromString(admin));
        this.timestamp = new Date(timestamp);

    }
}
