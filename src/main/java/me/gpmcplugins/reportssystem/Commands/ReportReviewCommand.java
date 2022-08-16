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
        switch (args[0])
        {
            case "claimnewreportgui":
                sendNotImplemented(sender);
                break;
            case "continueclaimedreportgui":
                sendNotImplemented(sender);
                break;
            case "claim":
                if (args[1] == null)
                {
                    sender.sendMessage("you forgot about report id");
                    return false;
                }
                Integer claimId = Integer.parseInt(args[1]);
                break;
            case "accept":
                if (args[1] == null)
                {
                    sender.sendMessage("you forgot about report id");
                    return false;
                }
                sendNotImplemented(sender);
                Integer acceptId = Integer.parseInt(args[1]);
                break;
            case "deny":
                if (args[1] == null)
                {
                    sender.sendMessage("you forgot about report id");
                    return false;
                }
                Integer denyId = Integer.parseInt(args[1]);
                sendNotImplemented(sender);
                sendNotImplemented(sender);
                break;
            default:
                sendNotImplemented(sender);
                break;
        }
        return true;
    }
    public void sendNotImplemented(CommandSender sender)
    {
        sender.sendMessage("Not implemented");
    }

}
