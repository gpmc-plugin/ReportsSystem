package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class HelpCommand implements CommandExecutor {

    private ReportsSystem plugin;
    public HelpCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize(String.format("<dark_green>Witaj w %s!\nUżyj /report, by zgłosić użytkownika\n<red>DLA ADMINÓW: </red>\nUżyj /report-review, by przeglądnąć zgłoszenie", plugin.getName())));
        return true;
    }
}