package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    private Map<Integer, ReportCreator> reportsInProgress= new HashMap<>();
    private Map<String, PlayerReportCreationStatus> playerReportCreationStatuses = new HashMap<>();
    private int reportInProgressID=0;
    public ReportCreator createReportTemplate(Integer id,ReportCreator.ReportType reportType, String reportedElementID, String reportingPlayer){
        ReportCreator reportCreator = new ReportCreator(reportType,reportingPlayer,reportedElementID);
        reportsInProgress.put(id,reportCreator);
        return  reportCreator;
    }
    public int getReportInProgressID() {
        return reportInProgressID;
    }
    public void incrementReportsID(){
        reportInProgressID++;
    }
}
