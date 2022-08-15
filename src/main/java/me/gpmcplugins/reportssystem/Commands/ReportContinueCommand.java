package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
                switch (reportState){
                    case 0:
                        switch (args[0]) {
                            case "0":
                                report.setShortDescription(ReportCreator.ReportShortDescription.Death_Bug);
                                break;
                            case "1":
                                report.setShortDescription(ReportCreator.ReportShortDescription.Death_Other_Player);
                                break;
                            case "2":
                                report.setShortDescription(ReportCreator.ReportShortDescription.User_Cheating);
                                break;
                            case "3":
                                report.setShortDescription(ReportCreator.ReportShortDescription.User_Scam);
                                break;
                            case "4":
                                report.setShortDescription(ReportCreator.ReportShortDescription.Message_Bad_Words);
                                break;
                            case "5":
                                report.setShortDescription(ReportCreator.ReportShortDescription.Message_Scam);
                                break;
                            case "6":
                                report.setShortDescription(ReportCreator.ReportShortDescription.Message_Hate_Speach);
                                break;
                            case "7":
                                report.setShortDescription(ReportCreator.ReportShortDescription.Message_Offensive);
                                break;
                            default:
                                sender.sendMessage("Zły typ'");
                                return false;

                        }
                        sender.sendMessage("Działa");
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
