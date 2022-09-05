package me.gpmcplugins.reportssystem.Replay;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class ReplayInventoryManager {
    private final ReplayObject replayObject;
    private final Map<Integer, ItemStack> normalIntventorySlots = new HashMap<>();

    public ReplayInventoryManager(ReplayObject replayObject) {
        this.replayObject = replayObject;
    }

    public void openInventory() {
        this.saveUserInventory();
        this.setUserNewInventory();
    }

    private void saveUserInventory() {
        Player player = replayObject.getPlayer();
        ItemStack emptyStack = new ItemStack(Material.AIR);
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            normalIntventorySlots.put(i, inventory.getItem(i));
            inventory.setItem(i, emptyStack);
        }
    }

    private void setUserNewInventory() {
        Player player = replayObject.getPlayer();
        PlayerInventory inventory = player.getInventory();
        inventory.setItem(2, ReplayHeadStatic.minusHead());
        inventory.setItem(3, ReplayHeadStatic.leftHead());
        inventory.setItem(4, ReplayHeadStatic.runButton());
        inventory.setItem(5, ReplayHeadStatic.rightHead());
        inventory.setItem(6, ReplayHeadStatic.plusHead());
    }

    public void revertInventory() {
        clearInventory();
        Player player = replayObject.getPlayer();
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = normalIntventorySlots.get(i);
            inventory.setItem(i, item);
        }

    }

    private void clearInventory() {
        Player player = replayObject.getPlayer();
        PlayerInventory inventory = player.getInventory();
        inventory.setArmorContents(new ItemStack[inventory.getArmorContents().length]);
        inventory.setContents(new ItemStack[inventory.getContents().length]);
        inventory.setItemInOffHand(new ItemStack(Material.AIR, 1));
    }
}
