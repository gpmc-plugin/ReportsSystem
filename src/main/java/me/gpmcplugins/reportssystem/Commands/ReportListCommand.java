package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.Common.Math;
import me.gpmcplugins.reportssystem.GUI.ReportListInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReportListCommand implements TabExecutor {
    private final Integer[] positions = {11, 13, 15, 20, 22, 24, 29, 31, 33, 38, 40, 42};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;

        if (args.length == 0) {
            return false;
        } else if (args.length == 1) {
            switch (args[0]) {
                case "wszystkie":
                    ReportListInterface.AllReportsMenu(p, positions, positions.length, 0);
                    return true;
                case "otwarte":
                    ReportListInterface.OpenReportsMenu(p, positions, positions.length, 0);
                    return true;
                case "zamknięte":
                    ReportListInterface.ClosedReportsMenu(p, positions, positions.length, 0);
                    return true;
            }
        } else if (args.length == 2) {
            if (!Math.isNumeric(args[1])) {
                p.sendMessage(Component.text("Strona musi być liczbą!", NamedTextColor.RED));
                return false;
            } else {
                int page = Integer.parseInt(args[1]);
                switch (args[0]) {
                    case "wszystkie":
                        ReportListInterface.AllReportsMenu(p, positions, positions.length, page);
                        return true;
                    case "otwarte":
                        ReportListInterface.OpenReportsMenu(p, positions, positions.length, page);
                        return true;
                    case "zamknięte":
                        ReportListInterface.ClosedReportsMenu(p, positions, positions.length, page - 1);
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabArgs = new ArrayList<>();
        if (args.length == 1) {
            tabArgs.add("wszystkie");
            tabArgs.add("otwarte");
            tabArgs.add("zamknięte");
        }
        return tabArgs;
    }
}
