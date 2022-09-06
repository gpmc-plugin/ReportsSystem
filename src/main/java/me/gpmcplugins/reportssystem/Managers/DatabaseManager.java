package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.Events.ReportCreateEvent;
import me.gpmcplugins.reportssystem.objects.ReportDeath;
import me.gpmcplugins.reportssystem.objects.ReportMessage;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DatabaseManager {
    Connection conn;
    ReportsSystem plugin;
    int nextReportID;
    int nextMessageID;
    int nextDeathID;

    public DatabaseManager(ReportsSystem plugin) {
        this.plugin=plugin;
        this.connect();
        this.loadData();
    }
    public void throwError(String error){
        plugin.getServer().getConsoleSender().sendMessage(error);
    }
    public void connect() {
        if(!this.plugin.getDataFolder().exists())
        {
            if(!plugin.getDataFolder().mkdir())
            {
                throw new RuntimeException("Couldnt create a directory in plugins");
            }
        }
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
        sql="create table IF NOT EXISTS notification\n" +
                "(\n" +
                "    userid          TEXT,\n" +
                "    notificationKey TEXT,\n" +
                "    args            TEXT,\n" +
                "    readed          INTEGER,\n" +
                "    timestamp       numeric\n" +
                ");";
        stmt.execute(sql);
        try{
            sql="Alter table reports add column status TEXT";
            stmt.execute(sql);
        }
        catch(SQLException ignored){

        }
        try{
            sql="Alter table reports add column readed INTEGER";
            stmt.execute(sql);
        }
        catch(SQLException ignored){
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
        long timestamp = new Date().getTime();
        String sql = "INSERT INTO \"main\".\"message_log\"(\"id\",\"sender\",\"recipients\",\"message\",\"timestamp\") VALUES (?,?,?,?,?);";
        PreparedStatement prstm= conn.prepareStatement(sql);
        prstm.setInt(1,ID);
        prstm.setString(2,Sender);
        prstm.setString(3,Recipients);
        prstm.setString(4,message);
        prstm.setLong(5,timestamp);
        prstm.execute();
    }
    public void createReport(String Sender,String type,String shortDescription,String description,String reportedID) throws SQLException {
        long timestamp = new Date().getTime();
        String sql = "INSERT INTO \"main\".\"reports\"(\"id\",\"reporting_player\",\"type\",\"short_description\",\"description\",\"timestamp\",\"admin\",\"reported_id\") VALUES (?,?,?,?,?,?,NULL,?);\n";
        int reportID = getNextReportID();
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
        ReportObject reportObject= this.getReport(reportID);
        ReportCreateEvent reportCreateEvent = new ReportCreateEvent(reportObject,reportObject.getReportingUser().getPlayer());
        Bukkit.getPluginManager().callEvent(reportCreateEvent);
    }
    public ReportMessage getMessage(Integer id){

        try {
            String sql = "Select * from message_log where id=?";
            PreparedStatement pstmt;
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
        long timestamp = new Date().getTime();
        PreparedStatement prstm = conn.prepareStatement(sql);
        prstm.setInt(1,id);
        prstm.setString(2,whoDied);
        prstm.setString(3,deathMessageTranslate);
        prstm.setLong(4,timestamp);
        prstm.execute();
    }
    public List<ReportObject> getAdminReports(String adminId, Integer limit, Integer site, OpenStatus openStatus) throws SQLException {
        int reportsAfter = limit*site;
        String sql = "Select * from reports where " + translateOpenStatusToSql(openStatus) + " AND admin"+(adminId==null?" is null":"=?")+" order by timestamp ASC Limit ?,?";
        return getReportObjects(adminId, limit, reportsAfter, sql);
    }

    @NotNull
    private List<ReportObject> getReportObjects(String adminId, Integer limit, int reportsAfter, String sql) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int indexchanger=adminId!=null?0:1;
        if(adminId!=null)
            pstmt.setString(1,adminId);
        pstmt.setInt(2-indexchanger,reportsAfter);
        pstmt.setInt(3-indexchanger,limit);
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

    @SuppressWarnings("unused")
    public List<ReportObject> getUserReports(String userId, Integer limit, Integer site, OpenStatus openStatus) throws SQLException {
        int reportsAfter = limit*site;
        String sql = "Select * from reports where "+ translateOpenStatusToSql(openStatus)+(openStatus==OpenStatus.ALL?"":" AND")+" reporting_player"+(userId==null?" is null":"=?")+" order by timestamp ASC Limit ?,?";
        return getReportObjects(userId, limit, reportsAfter, sql);
    }
    public Connection getConn(){
        return this.conn;
    }
    public ReportDeath getDeath(Integer deathID)  {
        String sql = "Select * from deaths where id=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,deathID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return new ReportDeath(deathID,rs.getString("noob"),rs.getString("message_translate"),rs.getLong("timestamp"),plugin);
            else
                return null;
        } catch (SQLException e) {
            throwError(e.getMessage());
        }
        return null;

    }
    public ReportObject getReport(Integer id) throws SQLException {
        String sql = "Select * from reports Where id=? order by timestamp ;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
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
    public Integer getAdminReportsCount(String aid,OpenStatus open) throws SQLException {
        String sql="Select count(*) from reports where admin"+(aid==null?" is null":"=?")+(open==OpenStatus.ALL?"":" AND")+translateOpenStatusToSql(open);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(aid!=null)
            pstmt.setString(1,aid);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt("count(*)");
    }
    public Integer getUserReportsCount(String uid,OpenStatus open) throws SQLException {
        String sql="Select count(*) from reports where reporting_player"+(uid==null?" is null":"=?")+(open==OpenStatus.ALL?"":" AND")+translateOpenStatusToSql(open);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(uid!=null)
            pstmt.setString(1,uid);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt("count(*)");
    }
    @SuppressWarnings("unused")
    public List<Integer> getNonReadMessages(String uid) throws SQLException {
        String sql = "Select * from reports Where \"reporting_player\"=? AND readed=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,uid);
        pstmt.setInt(2,0);
        ResultSet rs=pstmt.executeQuery();
        List<Integer> reports = new ArrayList<>();
        while(rs.next()){
            Integer rid = rs.getInt("id");
            reports.add(rid);
        }
        return reports;
    }
    public void setReadStatus(int rid,boolean status) throws SQLException {
        int statusCode=status?1:0;
        String sql = "update \"MAIN\".\"REPORTS\" set \"READED\"=? where \"id\"=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,statusCode);
        pstmt.setInt(2,rid);
        pstmt.execute();
    }
    private String translateOpenStatusToSql(OpenStatus openStatus){
        switch (openStatus){
            case OPEN:
                return " status IS NULL";
            case NOT_OPEN:
                return " status IS NOT NULL";
        }
        return " ";
    }
    @SuppressWarnings("unused")
    public enum OpenStatus{
        ALL,
        OPEN,
        NOT_OPEN
    }

    public void onDisable()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadFix()
    {
        Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
        for(Player p : onlinePlayers)
            plugin.getStorageManager().addUser(p.getUniqueId().toString());
    }
}
