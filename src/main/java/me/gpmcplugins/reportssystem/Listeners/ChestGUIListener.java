package me.gpmcplugins.reportssystem.Listeners;

import me.gpmcplugins.reportssystem.GUI.ChestGUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public class ChestGUIListener implements Listener {
    public ChestGUIListener(){
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void itemClickevent(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();
        if(e.getWhoClicked() instanceof Player){
            Player player = (Player) e.getWhoClicked();

            if(item==null)
                return;
            ItemMeta itemmeta = item.getItemMeta();
            if(itemmeta==null)
                return;

            Integer id = itemmeta.getPersistentDataContainer().get(ChestGUI.guiIdKey, PersistentDataType.INTEGER);
            if(id != null)
            {
                String command = itemmeta.getPersistentDataContainer().get(ChestGUI.onClickCommandKey, PersistentDataType.STRING);
                if(command!=null){
                    player.performCommand(command);
                }
                Byte shouldReload = itemmeta.getPersistentDataContainer().get(ChestGUI.shouldReloadKey, PersistentDataType.BYTE);
                if(shouldReload != null && shouldReload != 0)
                {
                    ChestGUI gui = ChestGUI.getChestGUIById(id);
                    player.performCommand(gui.getReloadCommand());
                }
                e.setCancelled(true);
            }
        }
    }

    public static void setup(JavaPlugin plugin)
    {
        plugin.getLogger().log(Level.INFO, "ChestGUIListener has been set up successfully");
        ChestGUIListener listener = new ChestGUIListener();
        plugin.getServer().getPluginManager().registerEvents(listener,plugin);
    }
}
