package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class HelpCommand implements CommandExecutor {

    private final ReportsSystem plugin;

    public HelpCommand(ReportsSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        TextComponent helpMsg = Component.text(String.format("Witaj w %s %s!\n", plugin.getName(), plugin.getDescription().getVersion()), NamedTextColor.AQUA)
                .append(Component.text("Użyj /report User, by zgłosić użytkownika\n")
                        .hoverEvent(HoverEvent.showText(Component.text("Kliknij, by użyć")))
                        .clickEvent(ClickEvent.suggestCommand("/report User ")))
                .append(Component.text("Kliknij na dowolną wiadomość napisaną przez gracza, bądź na dowolną "))
                .append(Component.text("swoją", NamedTextColor.AQUA, TextDecoration.BOLD))
                .append(Component.text(" śmierć, by wysłać szybki report\n"));

        if (sender.hasPermission("reportsystem.reportreview")) {
            helpMsg = helpMsg.append(Component.text("DLA ADMINÓW:\n", NamedTextColor.RED))
                    .append(Component.text("Aby przeglądać reporty graczy użyj /report-review")
                            .hoverEvent(HoverEvent.showText(Component.text("Kliknij, by użyć")))
                            .clickEvent(ClickEvent.suggestCommand("/report-review")));
        }
        sender.sendMessage(helpMsg.asComponent());
        return true;
    }
}