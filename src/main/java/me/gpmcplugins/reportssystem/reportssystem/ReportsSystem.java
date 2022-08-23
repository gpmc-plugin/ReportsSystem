package me.gpmcplugins.reportssystem.reportssystem;

import me.gpmcplugins.reportssystem.Commands.*;
import me.gpmcplugins.reportssystem.GUI.ChestGUI;
import me.gpmcplugins.reportssystem.GUI.ReportListInterface;
import me.gpmcplugins.reportssystem.GUI.ReportReviewInterface;
import me.gpmcplugins.reportssystem.Listeners.ChestGUIListener;
import me.gpmcplugins.reportssystem.Listeners.EventListener;
import me.gpmcplugins.reportssystem.Listeners.ViewInventoryListeners;
import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.Managers.StorageManager;
import me.gpmcplugins.reportssystem.Managers.UpdateManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.deafultBackgroundIconItemItemStack;

public final class ReportsSystem extends JavaPlugin {
    DatabaseManager databaseManager = new DatabaseManager(this);
    EventListener eventListener = new EventListener(this);
    StorageManager storageManager = new StorageManager(this);
    UpdateManager updateManager = new UpdateManager(this);

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(eventListener,this);
        this.getServer().getPluginManager().registerEvents(new ViewInventoryListeners(this),this);
        ReportReviewInterface.setup(this);
        ChestGUIListener.setup(this);
        ReportListInterface.setup(this);


        //todo add report logs
        //todo make player inventory visible in report-review
        Objects.requireNonNull(getCommand("report")).setExecutor(new ReportCommand(this));
        Objects.requireNonNull(getCommand("report-review")).setExecutor(new ReportReviewCommand(this));
        Objects.requireNonNull(getCommand("report-continue")).setExecutor(new ReportContinueCommand(this));
        Objects.requireNonNull(getCommand("report-help")).setExecutor(new HelpCommand(this));
        Objects.requireNonNull(getCommand("report-view-death-inventory")).setExecutor(new ViewDeathInventoryCommand(this));
        Objects.requireNonNull(getCommand("report-info")).setExecutor(new InfoCommand());
        Objects.requireNonNull(getCommand("report-list")).setExecutor(new ReportListCommand());
        Objects.requireNonNull(getCommand("report-list")).setExecutor(new ReportListCommand());
        Objects.requireNonNull(getCommand("report-update")).setExecutor(new UpdateCommand(this));

        ChestGUI.setDeafultBackgroundItem(deafultBackgroundIconItemItemStack);



    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
        databaseManager.onDisable();
        try{
            updateManager.managerThread.interrupt();
        }catch(Exception ignored)
        {
            throw new RuntimeException();
        }
        Objects.requireNonNull(getCommand("report")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-review")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-continue")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-help")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-view-death-inventory")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-info")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-list")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-list")).unregister(getServer().getCommandMap());
        Objects.requireNonNull(getCommand("report-update")).unregister(getServer().getCommandMap());
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    public StorageManager getStorageManager(){
        return storageManager;
    }
    public UpdateManager getUpdateManager() {return updateManager;}
    public File getPluginFile(){
        return this.getFile();
    }
}
