package me.gpmcplugins.reportssystem.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("Autorami tego pluginu są:");
        sender.sendMessage("    Backend - Pythontest");
        sender.sendMessage("    Frontend - Ssz256");
        sender.sendMessage("    Ten od switchów (ma nullpointer exeption) - Idont");
        return true;
    }
}
