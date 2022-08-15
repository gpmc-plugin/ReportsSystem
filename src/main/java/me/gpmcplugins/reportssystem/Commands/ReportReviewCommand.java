package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportReviewCommand implements CommandExecutor {

    private ReportsSystem plugin;
    public ReportReviewCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        sender.sendMessage("Not implemented");
        return false;
    }

}
