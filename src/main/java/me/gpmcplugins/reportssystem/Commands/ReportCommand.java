package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.gpmcplugins.reportssystem.objects.ReportCreator.ReportType;

// a class for report command
public final class ReportCommand implements CommandExecutor {
    private ReportsSystem plugin;
    public ReportCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player p = (Player) sender;
        ReportType reportType;

        switch(args[0])
        {
            case "death":
                reportType = ReportType.Death;
                sendNotImplemented(p);
                break;
            case "message":
                reportType = ReportType.Message;
                sendNotImplemented(p);
                break;
            case "user":
                reportType = ReportType.User;
                sendNotImplemented(p);
                break;
        }


        return true;
    }
    public void sendNotImplemented(Player p)
    {
        p.sendMessage("Not implemented");
    }
}
