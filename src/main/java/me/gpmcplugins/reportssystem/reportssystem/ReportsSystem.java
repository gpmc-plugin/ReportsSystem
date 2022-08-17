package me.gpmcplugins.reportssystem.reportssystem;

import me.gpmcplugins.reportssystem.Commands.HelpCommand;
import me.gpmcplugins.reportssystem.Commands.ReportCommand;
import me.gpmcplugins.reportssystem.Commands.ReportContinueCommand;
import me.gpmcplugins.reportssystem.Commands.ReportReviewCommand;
import me.gpmcplugins.reportssystem.GUI.ChestGUI;
import me.gpmcplugins.reportssystem.GUI.ReportReviewInterface;
import me.gpmcplugins.reportssystem.Commands.ViewDeathInventoryCommand;
import me.gpmcplugins.reportssystem.Listeners.ChestGUIListener;
import me.gpmcplugins.reportssystem.Listeners.EventListener;
import me.gpmcplugins.reportssystem.Listeners.ViewInventoryListeners;
import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.Managers.StorageManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.deafultBackgroundIconItemItemStack;

public final class ReportsSystem extends JavaPlugin {
    DatabaseManager databaseManager = new DatabaseManager(this);
    EventListener eventListener = new EventListener(this);
    StorageManager storageManager = new StorageManager(this);

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(eventListener,this);
        this.getServer().getPluginManager().registerEvents(new ViewInventoryListeners(this),this);

        ReportReviewInterface.setup(this);
        ChestGUIListener.setup(this);

        ChestGUI.setDeafultBackgroundItem(deafultBackgroundIconItemItemStack);

        Objects.requireNonNull(getCommand("report")).setExecutor(new ReportCommand(this));
        Objects.requireNonNull(getCommand("report-review")).setExecutor(new ReportReviewCommand(this));
        Objects.requireNonNull(getCommand("reportcontinue")).setExecutor(new ReportContinueCommand(this));
        Objects.requireNonNull(getCommand("report-help")).setExecutor(new HelpCommand(this));
        Objects.requireNonNull(getCommand("viewDeathInventory")).setExecutor(new ViewDeathInventoryCommand(this));
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
