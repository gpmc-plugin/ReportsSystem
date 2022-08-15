package me.gpmcplugins.reportssystem.reportssystem;

import me.gpmcplugins.reportssystem.Commands.ReportCommand;
import me.gpmcplugins.reportssystem.Commands.ReportContinueCommand;
import me.gpmcplugins.reportssystem.Commands.ReportReviewCommand;
import me.gpmcplugins.reportssystem.Listeners.EventListener;
import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.Managers.StorageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReportsSystem extends JavaPlugin {
    DatabaseManager databaseManager = new DatabaseManager(this);
    EventListener eventListener = new EventListener(this);
    StorageManager storageManager = new StorageManager(this);

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(eventListener,this);

        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("report-review").setExecutor(new ReportReviewCommand(this));
        getCommand("reportcontinue").setExecutor(new ReportContinueCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    public StorageManager getStorageManager(){
        return storageManager;
    }
}
