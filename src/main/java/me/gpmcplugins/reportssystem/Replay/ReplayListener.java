package me.gpmcplugins.reportssystem.Replay;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.persistence.PersistentDataType;

public class ReplayListener implements Listener {
    private final ReportsSystem plugin;

    public ReplayListener(ReportsSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void replayInventoryClickEvent(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();
            if (isInReplay(player))
                e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void replayMoveEvent(PlayerMoveEvent e) {

        if (isInReplay(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void replayChatEvent(AsyncChatEvent e) {
        if (isInReplay(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Component.text("Nie możesz wysyłać wiadomości będąc w trybie powtórki", NamedTextColor.RED));
        }
        for (Audience viewer : e.viewers()) {
            if (viewer instanceof Player) {
                Player player = (Player) viewer;
                if (isInReplay(player))
                    e.viewers().remove(viewer);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (isInReplay((Player) e.getEntity()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropEvent(PlayerDropItemEvent e) {
        if (isInReplay(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryChangeSlotEvent(PlayerSwapHandItemsEvent e) {
        if (isInReplay(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (isInReplay(e.getPlayer())) {
            Player player = e.getPlayer();
            plugin.getStorageManager().getReplay(player.getUniqueId().toString()).closeReply();
            plugin.getStorageManager().removeReplay(player.getUniqueId().toString());
        }
    }

    @EventHandler
    public void onPlayerosiagniecie(PlayerAdvancementCriterionGrantEvent e) {
        if (isInReplay(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerUseEvent(PlayerInteractEvent e) {
        if (isInReplay(e.getPlayer())) {
            ReplayObject replayObject = getFromPlayer(e.getPlayer());
            if (e.getItem() == null) return;
            int action = e.getItem().getItemMeta().getPersistentDataContainer().getOrDefault(ReplayHeadStatic.actionNamespace, PersistentDataType.INTEGER, -1);
            if (action == -1) return;
            e.setCancelled(true);
            if (action == 0) {
                replayObject.getReplayPlayer().start();
                replayObject.onStart();
            } else if (action == 1) {
                replayObject.getReplayPlayer().stop();
                replayObject.onStop();
            } else if (action == 2) {
                if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (replayObject.getReplayPlayer().isRunning()) {
                        replayObject.getReplayPlayer().moveForward(Long.valueOf(replayObject.getRewindSpeed()));
                        e.getPlayer().sendMessage(String.format("Wyświetlono %s wiadomości", replayObject.getRewindSpeed()));
                    }
                }
            } else if (action == 3) {
                double speed = replayObject.getReplayPlayer().getSpeed();
                replayObject.getReplayPlayer().setSpeed(speed * 2);
                replayObject.getPlayer().sendActionBar(Component.text(String.format("Ustawion prędkość na %s wiadomości na sekunde", speed * 2)));
            } else if (action == 4) {
                double speed = replayObject.getReplayPlayer().getSpeed();
                replayObject.getReplayPlayer().setSpeed(speed / 2);
                replayObject.getPlayer().sendActionBar(Component.text(String.format("Ustawion prędkość na %s wiadomości na sekunde", speed / 2)));
            }

        }
    }

    public static boolean isInReplay(Player e) {

        String reportId = e.getPersistentDataContainer().getOrDefault(ReplayObject.reportIDstatic, PersistentDataType.STRING, "-1");
        return !reportId.equals("-1");
    }

    public ReplayObject getFromPlayer(Player player) {
        return plugin.getStorageManager().getReplay(player.getUniqueId().toString());
    }
}
