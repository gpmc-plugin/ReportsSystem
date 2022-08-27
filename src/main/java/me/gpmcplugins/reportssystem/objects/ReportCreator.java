package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Events.ReportPreCreateEvent;
import me.gpmcplugins.reportssystem.Managers.MessageManager;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.Bukkit;

import java.sql.SQLException;

public class ReportCreator {
    private final ReportType type;
    private final String ReportingPlayer;
    private final String ReportedElementID;
    private String Description="Nie podano dokładnego powodu";
    private ReportShortDescription reportShortDescription;
    private final ReportsSystem plugin;
    public ReportCreator(ReportType type, String reportingPlayer, String reportedElementID, ReportsSystem plugin){
        this.plugin=plugin;
        this.type=type;
        this.ReportingPlayer=reportingPlayer;
        this.ReportedElementID=reportedElementID;

    }
    public void setShortDescription(ReportShortDescription rsd){
        this.reportShortDescription = rsd;
    }
    public void setDescription(String description){
        this.Description = description;
    }

    public ReportType getType() {
        return type;
    }

    public ReportShortDescription getReportShortDescription() {
        return reportShortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public String getReportingPlayer() {
        return ReportingPlayer;
    }

    public String getReportedElementID() {
        return ReportedElementID;
    }
    public PlayerReportCreationStatus getPlayer(){
        return plugin.getStorageManager().getUser(this.ReportingPlayer);
    }
    public Integer createReport(){
        ReportPreCreateEvent reportPreCreateEvent = new ReportPreCreateEvent(this,this.getPlayer().getPlayer());
        Bukkit.getPluginManager().callEvent(reportPreCreateEvent);
        MessageManager.sendReportMessage();
        try {
            if(!reportPreCreateEvent.isCancelled())
             return plugin.getDatabaseManager().createReport(getReportingPlayer(),getType().toString(),getReportShortDescription().toString(),getDescription(),getReportedElementID());
        } catch (SQLException e) {
            plugin.getDatabaseManager().throwError(e.getMessage());
        }
        return null;
    }

    public enum ReportType{
        User,
        Message,
        Death
    }
    public enum ReportShortDescription{
        Death_Bug,
        Death_Other_Player,
        User_Cheating,
        User_Scam,
        Message_Bad_Words,
        Message_Scam,
        Message_Hate_Speach,
        Message_Offensive,
        Other
    }
}
