package me.gpmcplugins.reportssystem.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("Autorami tego pluginu są:");
        sender.sendMessage(Component.text("    Backend - ")
                .append(Component.text("Pythontest", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(Component.text(
                                "jezeli cos nie dziala to prawdopodbnie jego wina",
                                NamedTextColor.YELLOW, TextDecoration.ITALIC)))));
        sender.sendMessage(Component.text("    Frontend - ")
                .append(Component.text("Ssz256", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(Component.text(
                                "ten od chest gui i blendow z nim zwiazanych",
                                NamedTextColor.YELLOW, TextDecoration.ITALIC)))));
        sender.sendMessage(Component.text("    Ten od switchów - ")
                .append(Component.text("Idont", NamedTextColor.LIGHT_PURPLE, TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(Component.text(
                                "Object reference not set to an instance of an object!",
                                NamedTextColor.YELLOW, TextDecoration.ITALIC)))));
        return true;
    }
}
