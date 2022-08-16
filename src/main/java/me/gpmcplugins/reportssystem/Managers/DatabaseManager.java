package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.objects.ReportDeath;
import me.gpmcplugins.reportssystem.objects.ReportMessage;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    Connection conn;
    ReportsSystem plugin;
    int nextReportID;
    int nextMessageID;
    int nextDeathID;
    public DatabaseManager(ReportsSystem plugin){
        this.plugin=plugin;
        this.connect();
        this.loadData();
    }
    public void throwError(String error){
        plugin.getServer().getConsoleSender().sendMessage(error);
    }
    public void connect() {
        if(!this.plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        String dbPath = this.plugin.getDataFolder().getAbsolutePath()+"\\db.db";
        String connString = "jdbc:sqlite:"+dbPath;
        try{
            conn = DriverManager.getConnection(connString);
        } catch (SQLException e) {
            throwError(e.getMessage());
        } finally {
            try {
                this.createTables();
            } catch (SQLException e) {
                throwError(e.getMessage());
            }
        }

    }
    public void loadData()  {
        try {
        Statement stmt = conn.createStatement();
        String sql = "Select count(*) from message_log";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        nextMessageID = rs.getInt("count(*)");
        sql = "Select count(*) from reports";
        rs = stmt.executeQuery(sql);
        rs.next();
        nextReportID = rs.getInt("count(*)");
        sql = "Select count(*) from deaths";
        rs = stmt.executeQuery(sql);
        rs.next();
        nextDeathID=rs.getInt("count(*)");
        } catch (SQLException e) {
            throwError(e.getMessage());
        }

    }
    private void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS\"message_log\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sender\"\tTEXT,\n" +
                "\t\"recipients\"\tTEXT,\n" +
                "\t\"message\"\tTEXT,\n" +
                "\t\"timestamp\"\tNUMERIC\n" +
                ");";

        stmt.execute(sql);
        sql="CREATE TABLE IF NOT EXISTS \"reports\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"reporting_player\"\tTEXT,\n" +
                "\t\"type\"\tTEXT,\n" +
                "\t\"short_description\"\tTEXT,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\t\"timestamp\"\tTEXT,\n" +
                "\t\"admin\"\tTEXT,\n" +
                "\t\"reported_id\"\tTEXT\n" +
                ");";
        stmt.execute(sql);
        sql="CREATE TABLE IF NOT EXISTS\"deaths\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"noob\"\tTEXT,\n" +
                "\t\"message_translate\"\tTEXT,\n" +
                "\t\"timestamp\"\tINTEGER,\n" +
                "\t\"message_key\"\tINTEGER\n" +
                ");";
        stmt.execute(sql);
        try{
            sql="Alter table reports add column status TEXT";
            stmt.execute(sql);
        }
        catch(Exception e){

        }


    }

    public int getNextMessageID() {
        return nextMessageID;
    }
    public void incrementNextMessageID(){
        nextMessageID++;
    }

    public int getNextDeathID() {
        return nextDeathID;
    }
    public void incrementNextDeathID(){
        nextDeathID++;
    }

    public int getNextReportID() {
        return nextReportID;
    }
    public void incrementNextReportID(){
        nextReportID++;
    }

    public void logMessage(int ID, String Sender, String Recipients, String message) throws SQLException {
        Long timestamp = new Date().getTime();
        String sql = "INSERT INTO \"main\".\"message_log\"(\"id\",\"sender\",\"recipients\",\"message\",\"timestamp\") VALUES (?,?,?,?,?);";
        PreparedStatement prstm= conn.prepareStatement(sql);
        prstm.setInt(1,ID);
        prstm.setString(2,Sender);
        prstm.setString(3,Recipients);
        prstm.setString(4,message);
        prstm.setLong(5,timestamp);
        prstm.execute();
    }
    public Integer createReport(String Sender,String type,String shortDescription,String description,String reportedID) throws SQLException {
        Long timestamp = new Date().getTime();
        String sql = "INSERT INTO \"main\".\"reports\"(\"id\",\"reporting_player\",\"type\",\"short_description\",\"description\",\"timestamp\",\"admin\",\"reported_id\") VALUES (?,?,?,?,?,?,NULL,?);\n";
        Integer reportID = getNextReportID();
        incrementNextReportID();
        PreparedStatement prstm= conn.prepareStatement(sql);
        prstm.setInt(1,reportID);
        prstm.setString(2,Sender);
        prstm.setString(3,type);
        prstm.setString(4,shortDescription);
        prstm.setString(5,description);
        prstm.setLong(6,timestamp);
        prstm.setString(7,reportedID);
        prstm.execute();
        return reportID;
    }
    public ReportMessage getMessage(Integer id){

        try {
            String sql = "Select * from message_log where id=?";
            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Player sender = plugin.getServer().getPlayer(UUID.fromString(rs.getString("sender")));
                return new ReportMessage(sender,rs.getString("message"));
            }
        } catch (SQLException e) {
            throwError(e.getMessage());
        }

        return null;
    }
    public void logDeath(Integer id,String whoDied,String deathMessageTranslate) throws SQLException {
        String sql = "INSERT INTO \"main\".\"deaths\"(\"id\",\"noob\",\"message_translate\",\"timestamp\",\"message_key\") VALUES (?,?,?,?,NULL);";
        Long timestamp = new Date().getTime();
        PreparedStatement prstm = conn.prepareStatement(sql);
        prstm.setInt(1,id);
        prstm.setString(2,whoDied);
        prstm.setString(3,deathMessageTranslate);
        prstm.setLong(4,timestamp);
        prstm.execute();
    }
    public List<ReportObject> getLastReports(Integer limit, Integer site, boolean open) throws SQLException {
        Integer reportsAfter = limit*site;
        String sql = "Select * from reports where status is "+(open?"NULL":"NOT NULL")+" order by timestamp ASC Limit ?,?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,reportsAfter);
        pstmt.setInt(2,limit);
        ResultSet rs = pstmt.executeQuery();
        List<ReportObject> reports = new ArrayList<>();
        while (rs.next()){
            Integer reportid = rs.getInt("id");
            String reportingPlayer=rs.getString("reporting_player");
            String type = rs.getString("type");
            String shortDescription=rs.getString("short_description");
            String description = rs.getString("description");
            Long timestamp = rs.getLong("timestamp");
            String admin = rs.getString("admin");
            String reportedId = rs.getString("reported_id");
            String status = rs.getString("status");
            reports.add(new ReportObject(reportid,reportingPlayer,type,shortDescription,reportedId,description,admin,timestamp,plugin,status));

        }
        return reports;
    }
    public List<ReportObject> getUserReport(String adminId,Integer limit, Integer site, boolean open) throws SQLException {
        Integer reportsAfter = limit*site;
        String sql = "Select * from reports order by timestamp Where admin=? Limit ?,?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,adminId);
        pstmt.setInt(2,reportsAfter);
        pstmt.setInt(3,limit);
        ResultSet rs = pstmt.executeQuery();
        List<ReportObject> reports = new ArrayList<>();
        while (rs.next()){
            Integer reportid = rs.getInt("id");
            String reportingPlayer=rs.getString("reporting_player");
            String type = rs.getString("type");
            String shortDescription=rs.getString("short_description");
            String description = rs.getString("description");
            Long timestamp = rs.getLong("timestamp");
            String admin = rs.getString("admin");
            String reportedId = rs.getString("reported_id");
            String status = rs.getString("status");
            reports.add(new ReportObject(reportid,reportingPlayer,type,shortDescription,reportedId,description,admin,timestamp,plugin,status));

        }
        return reports;
    }
    public Connection getConn(){
        return this.conn;
    }
    public ReportDeath getDeath(Integer deathID) throws SQLException {
        String sql = "Select * from deaths where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,deathID);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next())
            return new ReportDeath(deathID,rs.getString("noob"),rs.getString("message_translate"),rs.getLong("timestamp"),plugin);
        else
            return null;
    }
    public ReportObject getReport(Integer id) throws SQLException {
        String sql = "Select * from reports Where id=? order by timestamp ;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        List<ReportObject> reports = new ArrayList<>();
        if (rs.next()){
            Integer reportid = rs.getInt("id");
            String reportingPlayer=rs.getString("reporting_player");
            String type = rs.getString("type");
            String shortDescription=rs.getString("short_description");
            String description = rs.getString("description");
            Long timestamp = rs.getLong("timestamp");
            String admin = rs.getString("admin");
            String reportedId = rs.getString("reported_id");
            String status = rs.getString("status");
            return new ReportObject(reportid,reportingPlayer,type,shortDescription,reportedId,description,admin,timestamp,plugin,status);

        }
        return null;
    }
}
