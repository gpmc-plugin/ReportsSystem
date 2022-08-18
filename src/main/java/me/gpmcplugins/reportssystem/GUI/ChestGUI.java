package me.gpmcplugins.reportssystem.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class ChestGUI implements Listener {
    public static final NamespacedKey guiIdKey = new NamespacedKey("chestgui", "guiid");
    public static final NamespacedKey onClickCommandKey = new NamespacedKey("chestgui", "onclickcommand");
    public static final NamespacedKey shouldReloadKey = new NamespacedKey("chestgui", "shouldreload");
    static Integer nextGuiId = 0;
    TextComponent title = Component.text("Chest GUI");
    //HashMap<Integer, ItemStack> items = new HashMap<>();
    ItemStack[] items;
    ItemStack backgroundItem;
    static ItemStack deafultBackgroundItem;
    int size;
    int guiId;
    static HashMap<Integer, ChestGUI> instances = new HashMap<>();
    @SuppressWarnings("unused")
    public static void setDeafultBackgroundItem(ItemStack itemStack)
    {
        deafultBackgroundItem = itemStack;
    }

    @SuppressWarnings("unused")
    public ChestGUI(int size)
    {
        this.size = size;
        this.items = new ItemStack[this.size];
        this.guiId = ChestGUI.nextGuiId;
        ChestGUI.nextGuiId+=1;
        ChestGUI.instances.put(this.guiId, this);
    }

    @SuppressWarnings("unused")
    public ChestGUI setTitle(TextComponent title)
    {
        this.title = title;
        return this;
    }

    @SuppressWarnings("unused")
    public ChestGUI setTitle(String title)
    {
        this.title = (TextComponent) MiniMessage.miniMessage().deserialize(title);
        return this;
    }

    @SuppressWarnings("unused")
    public ChestGUI setItem(int position, ItemStack itemStack)
    {
        ItemStack stack = itemStack.clone();
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, guiId);
        stack.setItemMeta(itemMeta);
        this.items[position] = stack;
        return this;
    }

    @SuppressWarnings("unused")
    public ChestGUI setItem(int position, ItemStack itemStack, String onClickCommand, boolean shouldReload)
    {
        ItemStack stack = itemStack.clone();
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, guiId);
        itemMeta.getPersistentDataContainer().set(onClickCommandKey, PersistentDataType.STRING, onClickCommand);
        byte shouldReloadByte = 0;
        if(shouldReload)
            shouldReloadByte = 1;
        itemMeta.getPersistentDataContainer().set(shouldReloadKey, PersistentDataType.BYTE, shouldReloadByte);
        stack.setItemMeta(itemMeta);
        this.items[position] = stack;
        return this;
    }

    @SuppressWarnings("unused")
    public ChestGUI setBackgroundItem(ItemStack itemStack)
    {
        backgroundItem = itemStack;
        return this;
    }

    @SuppressWarnings("unused")
    public Inventory createInventory()
    {
        if(backgroundItem == null && deafultBackgroundItem != null)
            backgroundItem = deafultBackgroundItem.clone();
        for (int i = 0; i < this.size; i++) {
            if(backgroundItem != null && items[i] == null)
                this.setItem(i, backgroundItem);
        }
        Inventory inventory = Bukkit.createInventory(null, this.size, title);
        for (int i = 0; i < this.size; i++) {
            if(items[i] != null)
                inventory.setItem(i, items[i]);
        }
        return inventory;
    }

    @SuppressWarnings("unused")
    public void showGUI(Player p)
    {
        p.openInventory(this.createInventory());
    }

    @SuppressWarnings("unused")
    public static ItemStack setItemStackName(Component displayName, ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @SuppressWarnings("unused")
    public static ChestGUI getChestGUIById(int id)
    {
        return ChestGUI.instances.get(id);
    }
}
