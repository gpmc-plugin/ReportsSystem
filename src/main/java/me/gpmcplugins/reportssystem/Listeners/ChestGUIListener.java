package me.gpmcplugins.reportssystem.Listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;


public class ChestGUIListener implements Listener {
    private JavaPlugin plugin;
    public ChestGUIListener(JavaPlugin plugin){
        this.plugin=plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = false)
    public void itemClickevent(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();
        if(e.getWhoClicked() instanceof Player){
            NamespacedKey onClickCommandKey = new NamespacedKey("chestgui", "onclickcommand");
            Player player = (Player) e.getWhoClicked();
            if(item==null)
                return;
            ItemMeta itemmeta = item.getItemMeta();
            if(itemmeta==null)
                return;
            String command = itemmeta.getPersistentDataContainer().get(onClickCommandKey, PersistentDataType.STRING);
            if(command!=null){
                e.setCancelled(true);
                player.performCommand(command);
            }
        }
    }

    public static ChestGUIListener setup(JavaPlugin plugin)
    {
        ChestGUIListener listener = new ChestGUIListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(listener,plugin);
        return listener;
    }
}