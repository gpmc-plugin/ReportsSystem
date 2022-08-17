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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ChestGUI implements Listener {
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
        this.title = (TextComponent) MiniMessage.miniMessage().deserialize(title);
        return this;
    }

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

    public ChestGUI setItem(int position, ItemStack itemStack, String onClickCommand)
    {
        ItemStack stack = itemStack.clone();
        if(position >= this.size)
        {
            position = this.size;
        }

        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(guiIdKey, PersistentDataType.INTEGER, guiId);
        itemMeta.getPersistentDataContainer().set(onClickCommandKey, PersistentDataType.STRING, onClickCommand);
        stack.setItemMeta(itemMeta);
        this.items[position] = stack;
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
        return inventory;
    }

    public void showGUI(Player p)
    {
        p.openInventory(this.createInventory());
    }

    public static ItemStack setItemStackName(Component displayName, ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
