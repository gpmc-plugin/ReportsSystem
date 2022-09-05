package me.gpmcplugins.reportssystem.Replay;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;


public class ReplayObject {
    private final Player player;
    private final ReportsSystem plugin;
    private final Integer fromID;
    private final Integer susID;
    private final String replayID;
    private final ReplayInventoryManager replayInventoryManager;
    private ReplayPlayer replayPlayer;
    private boolean flightEnabled = false;
    private Location playerLocation;
    private Integer rewindSpeed = 10;
    public static NamespacedKey reportIDstatic = new NamespacedKey("reportssystemreply", "replyid");

    public ReplayObject(Player player, ReportsSystem plugin, Integer fromID, Integer susID) {
        this.plugin = plugin;
        this.player = player;
        this.fromID = fromID;
        this.susID = susID;
        this.replayID = "reply-" + player.getUniqueId() + "-" + new Date().getTime();
        this.replayInventoryManager = new ReplayInventoryManager(this);
    }

    public Player getPlayer() {
        return player;
    }

    public ReportsSystem getPlugin() {
        return plugin;
    }

    public void openReplay() {
        replayInventoryManager.openInventory();
        replayPlayer = new ReplayPlayer(this);
        player.getPersistentDataContainer().set(reportIDstatic, PersistentDataType.STRING, replayID);
        playerLocation = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getYaw());
        for (World world : plugin.getServer().getWorlds()) {
            if (world.getEnvironment() == World.Environment.THE_END) {
                player.teleport(new Location(world, 0, -1000000, 0));
                break;
            }
        }
        flightEnabled = player.getAllowFlight();
        player.setAllowFlight(true);
        player.setFlying(true);
        player.getInventory().setHeldItemSlot(4);
    }

    public void closeReply() {
        replayInventoryManager.revertInventory();
        player.teleport(playerLocation);
        player.setFlying(false);
        player.setAllowFlight(flightEnabled);
        player.getPersistentDataContainer().remove(reportIDstatic);
    }

    public void onEnd() {
        player.sendMessage(Component.text("Zakończono odtwarzanie powtórki. Możesz odtworzyć ją ponownie lub podjąć decyzje.", NamedTextColor.GREEN));
        player.getInventory().setItem(4, ReplayHeadStatic.replayButton());
        player.getInventory().setHeldItemSlot(4);
    }

    public void onStart() {
        player.getInventory().setItem(4, ReplayHeadStatic.stopButton());
        player.getInventory().setHeldItemSlot(4);
    }

    public void onStop() {
        player.getInventory().setItem(4, ReplayHeadStatic.resumeButton());
        player.getInventory().setHeldItemSlot(4);
    }

    public Integer getFromID() {
        return fromID;
    }

    @SuppressWarnings("unused")
    public Integer getSusID() {
        return susID;
    }

    public ReplayPlayer getReplayPlayer() {
        return replayPlayer;
    }

    public Integer getRewindSpeed() {
        return rewindSpeed;
    }

    @SuppressWarnings("unused")
    public void setRewindSpeed(Integer rewindSpeed) {
        this.rewindSpeed = rewindSpeed;
    }
}
