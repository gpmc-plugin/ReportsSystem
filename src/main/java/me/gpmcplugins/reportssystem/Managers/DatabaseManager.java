package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

import java.sql.*;
import java.util.Date;

public class DatabaseManager {
    Connection conn;
    ReportsSystem plugin;
    int nextReportID;
    int nextMessageID;
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

    };
    public void loadData()  {
        try {
        Statement stmt = conn.createStatement();
        String sql = "Select count(*) from message_log";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        nextMessageID = rs.getInt("count(*)");
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
    }

    public int getNextMessageID() {
        return nextMessageID;
    }
    public void incrementNextMessageID(){
        nextMessageID++;
    }
    public void logMessage(int ID, String Sender,String Recipients, String message) throws SQLException {
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
}
