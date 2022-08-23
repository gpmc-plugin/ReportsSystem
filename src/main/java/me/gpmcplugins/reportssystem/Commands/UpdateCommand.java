package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.Update.UpdateThread;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class UpdateCommand implements CommandExecutor {

    private final ReportsSystem plugin;

    public UpdateCommand(ReportsSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        UpdateThread thread = new UpdateThread(plugin);
        thread.start();
        return true;
    }
}