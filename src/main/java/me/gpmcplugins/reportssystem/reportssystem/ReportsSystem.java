package me.gpmcplugins.reportssystem.reportssystem;

import me.gpmcplugins.reportssystem.Commands.ReportCommand;
import me.gpmcplugins.reportssystem.Listeners.EventListener;
import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReportsSystem extends JavaPlugin {
    DatabaseManager databaseManager = new DatabaseManager(this);
    EventListener eventListener = new EventListener(this);

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(eventListener,this);

        getCommand("report").setExecutor(new ReportCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
