package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.Common.Math;
import me.gpmcplugins.reportssystem.GUI.ViewInventoryGui;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.objects.ReportDeath;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ViewDeathInventoryCommand implements CommandExecutor {
    private final ReportsSystem plugin;
    public ViewDeathInventoryCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length>0&& Math.isNumeric(args[0])){
            if(sender instanceof Player){
                Player player = (Player) sender;
                try {
                    ReportObject report = plugin.getDatabaseManager().getReport(Integer.valueOf(args[0]));
                    if(report==null){
                        sender.sendMessage("Report nie istnieje");
                        return true;
                    }
                    if(report.type!= ReportCreator.ReportType.Death){
                        sender.sendMessage("Report nie istnieje");
                        return true;
                    }
                    ReportDeath death = plugin.getDatabaseManager().getDeath(Integer.valueOf(report.reportedID));
                    if(death.getNoob().getUniqueId().toString().equals(player.getUniqueId().toString())||player.hasPermission("reportsystem.reportreview")){
                        new ViewInventoryGui(plugin,Integer.parseInt(args[0])).openInventory(player);
                    }
                    else
                        sender.sendMessage("to nie ty wtedy umarłeś");
                    return true;
                } catch (SQLException e) {
                    plugin.getDatabaseManager().throwError(e.getMessage());
                }
                return false;
            }
        }
        else
            sender.sendMessage("za mało argumentów lub są błędne");
        return false;
    }
}
