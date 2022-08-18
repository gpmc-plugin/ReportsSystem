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
    public Integer id;
    public Player reportingUser;
    public ReportsSystem plugin;
    public ReportCreator.ReportType type;
    public ReportCreator.ReportShortDescription shortDescription;
    public String reportedID;
    public String description;
    public Player admin=null;
    public Date timestamp;
    public ReportStatus reportStatus;
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
        new MessageManager(plugin).sendUpdateMessage(reportStatus,id,reportingUser);

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
