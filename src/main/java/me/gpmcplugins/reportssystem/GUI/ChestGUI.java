package me.gpmcplugins.reportssystem.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.createInventory;

public class ChestGUI implements Listener {
    static final NamespacedKey itemIdKey = new NamespacedKey("chestgui", "itemid");
    static final NamespacedKey guiIdKey = new NamespacedKey("chestgui", "guiid");
    static final NamespacedKey onClickCommandKey = new NamespacedKey("chestgui", "onclickcommand");
    static Integer nextGuiId = 0;
    TextComponent title = Component.text("Chest GUI");
    //HashMap<Integer, ItemStack> items = new HashMap<>();
    ItemStack[] items;
    ItemStack backgroundItem;
    int size;
    int guiId;
    public ChestGUI(int size)
    {
        this.size = size;
        this.items = new ItemStack[this.size];
        this.guiId = ChestGUI.nextGuiId;
        ChestGUI.nextGuiId+=1;
    }

    public ChestGUI setTitle(TextComponent title)
    {
        this.title = title;
        return this;
    }

    public ChestGUI setTitle(String title)
    {
        this.title = Component.text(title);
        return this;
    }

    /*public ChestGUI setItem(int position, Integer id, ItemStack itemStack)
    {
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.INTEGER, id);
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, id);
        itemStack.setItemMeta(itemMeta);
        this.items[position] = itemStack;
        //this.items.put(position, itemStack);
        return this;
    }

    public ChestGUI setItem(int position, Integer id, ItemStack itemStack, String onClickCommand)
    {
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.INTEGER, id);
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, id);
        itemMeta.getPersistentDataContainer().set(onClickCommandKey, PersistentDataType.STRING, onClickCommand);
        itemStack.setItemMeta(itemMeta);
        this.items[position] = itemStack;
        //this.items.put(position, itemStack);
        return this;
    }*/

    public ChestGUI setItem(int position, ItemStack itemStack)
    {
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, guiId);
        itemStack.setItemMeta(itemMeta);
        this.items[position] = itemStack;
        //this.items.put(position, itemStack);
        return this;
    }

    public ChestGUI setItem(int position, ItemStack itemStack, String onClickCommand)
    {
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, guiId);
        itemMeta.getPersistentDataContainer().set(onClickCommandKey, PersistentDataType.STRING, onClickCommand);
        itemStack.setItemMeta(itemMeta);
        this.items[position] = itemStack;
        //this.items.put(position, itemStack);
        return this;
    }

    public ChestGUI setBackgroundItem(ItemStack itemStack)
    {
        backgroundItem = itemStack;
        return this;
    }

    public Inventory createInventory()
    {
        Inventory inventory = Bukkit.createInventory(null, this.size, title);
        for (int i = 0; i < this.size; i++) {
            if(items[i] != null)
                inventory.setItem(i, items[i]);
            else if(backgroundItem != null)
                inventory.setItem(i, backgroundItem);
        }
        /*for(Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            Integer key = entry.getKey();
            ItemStack value = entry.getValue();

            inventory.setItem(key, value);
        }*/
        return inventory;
    }

    public void showGUI(Player p)
    {
        p.openInventory(this.createInventory());
    }

    public static int getPosByXY(int x, int y)
    {
        return x*9+y;
    }

    public static ItemStack setItemStackName(Component displayName, ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
