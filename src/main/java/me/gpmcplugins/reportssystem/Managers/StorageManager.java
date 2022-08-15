package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    private Map<Integer, ReportCreator> reportsInProgress= new HashMap<>();
    private Map<String, PlayerReportCreationStatus> playerReportCreationStatuses = new HashMap<>();
    private int reportInProgressID=0;
    private ReportsSystem plugin;
    public StorageManager(ReportsSystem plugin){
        this.plugin=plugin;
    }
    public ReportCreator createReportTemplate(Integer id,ReportCreator.ReportType reportType, String reportedElementID, String reportingPlayer){
        ReportCreator reportCreator = new ReportCreator(reportType,reportingPlayer,reportedElementID);
        reportsInProgress.put(id,reportCreator);
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
