package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.configuration.file.FileConfiguration;


public class ConfigManager {
    public void onEnable() {
        addDeafults();
    }

    private void addDeafults() {
        getConfig().addDefault("UpdateCheckInterval", 5 * 60 * 1000);
        getConfig().addDefault("DiscordToken", "paste-token-here");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return ReportsSystem.getInstance().getConfig();
    }

    public void saveConfig() {
        ReportsSystem.getInstance().saveConfig();
    }
}
