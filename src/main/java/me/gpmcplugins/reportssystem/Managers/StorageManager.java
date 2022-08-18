package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.Objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.Objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    private final Map<Integer, ReportCreator> reportsInProgress= new HashMap<>();
    private final Map<String, PlayerReportCreationStatus> playerReportCreationStatuses = new HashMap<>();
    private int reportInProgressID=0;
    private final ReportsSystem plugin;
    public StorageManager(ReportsSystem plugin){
        this.plugin=plugin;
    }
    public ReportCreator createReportTemplate(Integer id,ReportCreator.ReportType reportType, String reportedElementID, String reportingPlayer){
        PlayerReportCreationStatus player = getUser(reportingPlayer);
        if(player.getReportID()!=null)
            player.clearReport();
        ReportCreator reportCreator = new ReportCreator(reportType,reportingPlayer,reportedElementID,plugin);
        reportsInProgress.put(id,reportCreator);
        player.setReportID(id);
        return  reportCreator;
    }
    public ReportCreator getReport(Integer id){
        return reportsInProgress.getOrDefault(id,null);
    }
    public int getReportInProgressID() {
        return reportInProgressID;
    }
    public void incrementReportsID(){
        reportInProgressID++;
    }
    public void addUser(String uuid){
        PlayerReportCreationStatus player = new PlayerReportCreationStatus(uuid,this.plugin);
        playerReportCreationStatuses.put(uuid,player);
    }
    public void removeUser(String uuid){
        playerReportCreationStatuses.remove(uuid);
    }
    public PlayerReportCreationStatus getUser(String uuid){
        return playerReportCreationStatuses.getOrDefault(uuid,null);
    }
}
