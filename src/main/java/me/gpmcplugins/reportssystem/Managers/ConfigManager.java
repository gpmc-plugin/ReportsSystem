package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigManager {
    public void onEnable()
    {
        addDeafults();
    }

    private void addDeafults()
    {
        getConfig().addDefault("UpdateCheckInterval", 5*60*1000);
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public FileConfiguration getConfig()
    {
        return ReportsSystem.getInstance().getConfig();
    }

    public void reloadConfig()
    {
        ReportsSystem.getInstance().reloadConfig();
    }
    public void saveConfig()
    {
        ReportsSystem.getInstance().saveConfig();
    }
}
