package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class ReportContinueCommand implements CommandExecutor {
    private ReportsSystem plugin;
    public ReportContinueCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            PlayerReportCreationStatus playerCreationStatus = plugin.getStorageManager().getUser(((Player) sender).getUniqueId().toString());
            if(playerCreationStatus.getReportID()!=null){
                Integer reportState = playerCreationStatus.getState();
                ReportCreator report = plugin.getStorageManager().getReport(playerCreationStatus.getReportID());
                if(args.length>0) {
                    if (args[0].equals("delete")) {
                        playerCreationStatus.sendReportDeleted();
                        playerCreationStatus.clearReport();
                        return true;
                    }
                }
                switch (reportState){
                    case 0:
                        try{
                        report.setShortDescription(ReportCreator.ReportShortDescription.values()[Integer.parseInt(args[0])]);
                        }
                        catch(Exception e){
                            sender.sendMessage("Coś poszło nie tak");
                            return false;
                        }
                        playerCreationStatus.setState(reportState+1);
                        playerCreationStatus.getPlayer().openInventory(Bukkit.createInventory(null, InventoryType.CHEST));
                        playerCreationStatus.getPlayer().closeInventory();
                        playerCreationStatus.sendSavedMessage();
                        playerCreationStatus.sendDescriptionMessage();
                        return true;
                    case 1:
                        playerCreationStatus.sendSavedMessage();
                        playerCreationStatus.setState(2);
                        playerCreationStatus.sendSummary(report);
                        return true;
                    case 2:
                        playerCreationStatus.createReport(report);
                        return true;


                }

            }
            else
                sender.sendMessage("Nie tworzysz żadnego reportu");
        }
        else
            sender.sendMessage("Musisz być graczem żeby użyć tej komendy");
        return false;
    }
}
