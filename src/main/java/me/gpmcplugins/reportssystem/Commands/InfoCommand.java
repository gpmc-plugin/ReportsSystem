package me.gpmcplugins.reportssystem.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("Autorami tego pluginu są:\nBackend - Pythontest, Frontend - Ssz256, Ten od switchów - Idont");
        return true;
    }
}
