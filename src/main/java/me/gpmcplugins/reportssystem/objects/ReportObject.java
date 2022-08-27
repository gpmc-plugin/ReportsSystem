package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Managers.MessageManager;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class ReportObject {
    private final Integer id;
    private final Player reportingUser;
    private final ReportsSystem plugin;
    private final ReportCreator.ReportType type;
    private final ReportCreator.ReportShortDescription shortDescription;
    private final String reportedID;
    private final String description;
    private Player admin;
    private final Date timestamp;
    private final ReportStatus reportStatus;
    public ReportObject(Integer id, String reportingUser, String type, String shortDescription, String reportedID,String description, String admin, Long timestamp, ReportsSystem plugin, String status){
        this.id=id;
        this.plugin = plugin;
        this.reportingUser = plugin.getServer().getPlayer(UUID.fromString(reportingUser));
        this.type = ReportCreator.ReportType.valueOf(type);
        this.shortDescription = ReportCreator.ReportShortDescription.valueOf(shortDescription);
        this.reportedID = reportedID;
        this.description=description;
        if(admin!=null)
            this.admin = plugin.getServer().getPlayer(UUID.fromString(admin));
        this.timestamp = new Date(timestamp);
        if(status==null)
            this.reportStatus=ReportStatus.In_Progress;
        else
            this.reportStatus=ReportStatus.valueOf(status);

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public ReportCreator.ReportType getType() {
        return type;
    }

    public ReportsSystem getPlugin() {
        return plugin;
    }

    public Integer getId() {
        return id;
    }

    public Player getReportingUser() {
        return reportingUser;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public String getReportedID() {
        return reportedID;
    }
    @SuppressWarnings("unused")
    public Player getAdmin() {
        return admin;
    }

    public ReportCreator.ReportShortDescription getShortDescription() {
        return shortDescription;
    }

    public void setReportStatus(ReportStatus reportStatus){
        Connection conn = plugin.getDatabaseManager().getConn();
        String sql = "UPDATE \"main\".\"reports\" SET \"status\"=? WHERE \"id\"=?";
        try {
            PreparedStatement prstm = conn.prepareStatement(sql);
            if(reportStatus==ReportStatus.In_Progress)
                reportStatus=null;
            assert reportStatus != null;
            prstm.setString(1,reportStatus.toString());
            prstm.setInt(2,this.id);
            prstm.execute();

        } catch (SQLException e) {
            plugin.getDatabaseManager().throwError(e.getMessage());
        }
        MessageManager.sendUpdateMessage(reportStatus,id,reportingUser);

    }
    public void setAdmin(String uid){
        this.admin=plugin.getServer().getPlayer(UUID.fromString(uid));
        Connection conn = plugin.getDatabaseManager().getConn();
        String sql = "UPDATE \"main\".\"reports\" SET \"admin\"=? WHERE \"id\"=?";
        try {
            PreparedStatement prstm = conn.prepareStatement(sql);
            prstm.setString(1,uid);
            prstm.setInt(2,this.id);
            prstm.execute();

        } catch (SQLException e) {
            plugin.getDatabaseManager().throwError(e.getMessage());
        }
    }
    public enum ReportStatus{
        In_Progress,
        Accepted,
        Denided,
    }
}
