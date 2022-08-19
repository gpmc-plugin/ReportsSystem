package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.Common.Math;
import me.gpmcplugins.reportssystem.GUI.ReportListInterface;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReportListCommand implements CommandExecutor {
    private ReportsSystem plugin;
    private Integer[] positions = {11, 13, 15, 20, 22, 24, 29, 31, 33, 38, 40, 42};

    public ReportListCommand(ReportsSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;

        if (args.length == 0) {
            return false;
        }
        else if (args.length == 1) {
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
        }
        else if (args.length == 2)
        {
            if (!Math.isNumeric(args[1])) {
                p.sendMessage(Component.text("Strona musi być liczbą!!!!", NamedTextColor.RED));
                return false;
            }
            else {
                int page = Integer.parseInt(args[1]);
                switch (args[0]) {
                    case "wszystkie":
                        ReportListInterface.AllReportsMenu(p, positions, positions.length, page);
                        return true;
                    case "otwarte":
                        ReportListInterface.OpenReportsMenu(p, positions, positions.length, page);
                        return true;
                    case "zamknięte":
                        ReportListInterface.ClosedReportsMenu(p, positions, positions.length, page-1);
                        return true;
                }
            }
        }
        return false;
    }
}
