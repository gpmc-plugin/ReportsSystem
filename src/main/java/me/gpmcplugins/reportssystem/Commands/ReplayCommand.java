package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.Replay.ReplayListener;
import me.gpmcplugins.reportssystem.Replay.ReplayObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplayCommand implements CommandExecutor {
    private final ReportsSystem plugin;

    public ReplayCommand(ReportsSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (ReplayListener.isInReplay((Player) sender)) {
                ((Player) sender).getPersistentDataContainer().remove(ReplayObject.reportIDstatic);
                plugin.getStorageManager().getReplay(((Player) sender).getUniqueId().toString()).closeReply();
                plugin.getStorageManager().removeReplay(((Player) sender).getUniqueId().toString());
            } else {
                Player player = (Player) sender;
                plugin.getStorageManager().createReplay(player, 0, 0).openReplay();
            }
        }
        return true;
    }
}
