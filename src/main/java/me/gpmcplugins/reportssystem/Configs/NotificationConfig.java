package me.gpmcplugins.reportssystem.Configs;

import me.gpmcplugins.reportssystem.Managers.NotificationManager.NotificationType;
import me.gpmcplugins.reportssystem.objects.NotificationConfigObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class NotificationConfig {
    private FileConfiguration configuration;
    private File configFile;
    private static NotificationConfig instance;
    private final Map<NotificationType,String> translatableStrings = new HashMap<>();
    private final Map<NotificationType,Boolean> canBeDelayed = new HashMap<>();
    public NotificationConfig() {
        instance=this;
        generateDefaultConfig();
        setupConfig();
        prepareConfiguration();

    }

    private void setupConfig() {
        ReportsSystem plugin = ReportsSystem.getInstance();
        this.configuration = new YamlConfiguration();
        configFile = new File(Paths.get(plugin.getDataFolder().getAbsolutePath(), "notifications.yml").toUri());
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile())
                    configuration.load(configFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                configuration.load(configFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void prepareConfiguration() {
        Configuration defaultConfiguration = new YamlConfiguration();
        for (NotificationType value : NotificationType.values()) {
            ConfigurationSection section = defaultConfiguration.createSection(value.toString());
            Boolean canBeDelayedValue = getDefaultCanBeDelayed(value);
            section.set("isDelayeble",canBeDelayedValue);
            String translatableString = getDefaultTranslatableString(value);
            section.set("translatableString",translatableString);
        }
        this.configuration.addDefaults(defaultConfiguration);
        this.configuration.options().copyDefaults(true);
        saveConfig();
    }
    private void generateDefaultConfig(){
        translatableStrings.put(NotificationType.ReportAdminChange,"Twój report o id %s został przyjęty do realizacji przez admina %s");
        translatableStrings.put(NotificationType.ReportStateChange,"Twój reports o id %s został zaaktualizowany. Aktualny status reportu to %s");
        canBeDelayed.put(NotificationType.ReportStateChange,true);
        canBeDelayed.put(NotificationType.ReportAdminChange,true);
    }
    public  String getDefaultTranslatableString(NotificationType type){
        return translatableStrings.getOrDefault(type,null);
    }
    public  Boolean getDefaultCanBeDelayed(NotificationType type){
        return canBeDelayed.getOrDefault(type,null);
    }
    public NotificationConfigObject getTypeConfig(NotificationType notificationType){
        ConfigurationSection configurationSection = configuration.getConfigurationSection(notificationType.toString());
        assert configurationSection != null;
        Boolean canBeDelayedValue=configurationSection.getBoolean("isDelayeble");
        String translatableStringValue =configurationSection.getString("translatableString");
        return new NotificationConfigObject(notificationType,translatableStringValue,canBeDelayedValue);
    }
    private void saveConfig(){
        try {
            this.configuration.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static NotificationConfig getInstance(){
        return instance;
    }
}