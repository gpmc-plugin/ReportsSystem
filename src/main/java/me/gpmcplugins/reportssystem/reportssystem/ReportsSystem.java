package me.gpmcplugins.reportssystem.reportssystem;

import me.gpmcplugins.reportssystem.Commands.*;
import me.gpmcplugins.reportssystem.GUI.ChestGUI;
import me.gpmcplugins.reportssystem.GUI.ReportListInterface;
import me.gpmcplugins.reportssystem.GUI.ReportReviewInterface;
import me.gpmcplugins.reportssystem.Listeners.ChestGUIListener;
import me.gpmcplugins.reportssystem.Listeners.EventListener;
import me.gpmcplugins.reportssystem.Listeners.InternalListener;
import me.gpmcplugins.reportssystem.Listeners.ViewInventoryListeners;
import me.gpmcplugins.reportssystem.Managers.ConfigManager;
import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.Managers.StorageManager;
import me.gpmcplugins.reportssystem.Managers.UpdateManager;
import me.gpmcplugins.reportssystem.Replay.ReplayListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.deafultBackgroundIconItemItemStack;

public final class ReportsSystem extends JavaPlugin {
    private static ReportsSystem instance = null;
    DatabaseManager databaseManager = new DatabaseManager(this);
    EventListener eventListener = new EventListener(this);
    StorageManager storageManager = new StorageManager(this);
    UpdateManager updateManager = new UpdateManager(this);
    ConfigManager configManager = new ConfigManager();

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getPluginManager().registerEvents(eventListener,this);
        this.getServer().getPluginManager().registerEvents(new ViewInventoryListeners(this),this);
        this.getServer().getPluginManager().registerEvents(new ReplayListener(this),this);
        this.getServer().getPluginManager().registerEvents(new InternalListener(),this);
        ReportReviewInterface.setup(this);
        ChestGUIListener.setup(this);
        ReportListInterface.setup(this);
        configManager.onEnable();


        //todo add report logs
        //todo generalize report displaying in report-list and report-review
        //todo add discord integration
        //todo add backdoor :tf:
        //todo message replay mode

        Objects.requireNonNull(getCommand("report")).setExecutor(new ReportCommand(this));
        Objects.requireNonNull(getCommand("report-review")).setExecutor(new ReportReviewCommand(this));
        Objects.requireNonNull(getCommand("report-continue")).setExecutor(new ReportContinueCommand(this));
        Objects.requireNonNull(getCommand("report-help")).setExecutor(new HelpCommand(this));
        Objects.requireNonNull(getCommand("report-view-death-inventory")).setExecutor(new ViewDeathInventoryCommand(this));
        Objects.requireNonNull(getCommand("report-info")).setExecutor(new InfoCommand());
        Objects.requireNonNull(getCommand("report-list")).setExecutor(new ReportListCommand());
        Objects.requireNonNull(getCommand("report-list")).setExecutor(new ReportListCommand());
        Objects.requireNonNull(getCommand("report-update")).setExecutor(new UpdateCommand(this));
        Objects.requireNonNull(getCommand("report-replay")).setExecutor(new ReplayCommand(this));

        ChestGUI.setDeafultBackgroundItem(deafultBackgroundIconItemItemStack);
        databaseManager.reloadFix();
        updateManager.runPopupThread();
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
        ChestGUI.onDisable();
        databaseManager.onDisable();
        storageManager.onDisable();
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
    public ConfigManager getConfigManager() {return configManager;}
    public File getPluginFile(){
        return this.getFile();
    }
    public static ReportsSystem getInstance() {
        if(ReportsSystem.instance == null)
            try
            {
                throw new RuntimeException();
            } catch(RuntimeException e)
            {
                e.printStackTrace();
            }
        return ReportsSystem.instance;
    }
}
