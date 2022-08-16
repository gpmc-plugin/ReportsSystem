package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public ReportStatus getReportStatus(){
        Connection conn = plugin.getDatabaseManager().getConn();
        String sql = "Select status from reports_status where id=?";
        try {
            PreparedStatement prstm = conn.prepareStatement(sql);
            prstm.setInt(1,this.id);
            ResultSet rs=prstm.executeQuery();
            if(rs.next()){
                ReportStatus reportStatus = ReportStatus.valueOf(rs.getString("status"));
                if(reportStatus==null)
                    return ReportStatus.In_Progress;
                else
                    return reportStatus;
            }

            else
                return ReportStatus.In_Progress;
        } catch (SQLException e) {
            plugin.getDatabaseManager().throwError(e.getMessage());
        }
        return ReportStatus.In_Progress;
    }
    public void setReportStatus(ReportStatus reportStatus){
        Connection conn = plugin.getDatabaseManager().getConn();
        String sql = "UPDATE \"main\".\"reports_status\" SET \"status\"=? WHERE \"id\"=?";
        try {
            PreparedStatement prstm = conn.prepareStatement(sql);
            prstm.setString(1,reportStatus.toString());
            prstm.setInt(2,this.id);

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
