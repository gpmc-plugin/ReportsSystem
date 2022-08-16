package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ViewInventoryGui {
    Integer deathID;
    ReportsSystem plugin;
    public ViewInventoryGui(ReportsSystem plugin, Integer deathID){
        this.plugin = plugin;
        this.deathID = deathID;
    }
    public void openInventory(Player player){
        Inventory inventory = Bukkit.createInventory(null,45, Component.text("Read only"));
        Integer i=0;
        PlayerReportCreationStatus playerStatus = plugin.getStorageManager().getUser(player.getUniqueId().toString());
        try {
            plugin.getConfig().load("item.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        while(plugin.getConfig().getItemStack(deathID+"."+i)!=null){
            ItemStack itemStack = plugin.getConfig().getItemStack(deathID+"."+i);
            if(itemStack==null)
                break;
            inventory.setItem(i,itemStack);
            i++;
        }
        player.openInventory(inventory);
        playerStatus.setLookingDeathId(this.deathID);

    }
}
