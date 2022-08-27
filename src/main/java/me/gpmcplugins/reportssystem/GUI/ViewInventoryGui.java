package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.SQLException;

public class ViewInventoryGui {
    Integer deathID;
    ReportsSystem plugin;
    public ViewInventoryGui(ReportsSystem plugin, Integer deathID){
        this.plugin = plugin;
        this.deathID = deathID;
    }
    public void openInventory(Player player){
        Inventory inventory = Bukkit.createInventory(null,54, Component.text("Aby wziąć itemy użyj shift"));
        Integer i=0;
        ReportObject report;
        try {
            report = plugin.getDatabaseManager().getReport(this.deathID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        deathID= Integer.valueOf(report.getReportedID());
        PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(player.getUniqueId().toString());
        try {
            plugin.getConfig().load("item.yml");
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        while(i<54){
            ItemStack itemStack = plugin.getConfig().getItemStack(deathID+"."+i);
            inventory.setItem(i,itemStack);
            i++;
        }
        player.openInventory(inventory);
        playerStatus.setLookingDeathId(report.getId());

    }
}
