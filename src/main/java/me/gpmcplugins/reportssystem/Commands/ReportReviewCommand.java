package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.GUI.ReportReviewInterface;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportReviewCommand implements CommandExecutor {

    private ReportsSystem plugin;
    public ReportReviewCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        if (args.length == 0)
        {
            p.sendMessage("length err");
            ReportReviewInterface.MainReviewMenu(p);
            return true;
        }
        switch (args[0])
        {
            case "claimnewreportgui":
                p.sendMessage("claimnewreportgui");
                ReportReviewInterface.ClaimNewReportMenu(p);
                break;
            case "continueclaimedreportgui":
                p.sendMessage("continueclaimedreportgui");
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
        }
        return true;
    }
    public void sendNotImplemented(CommandSender sender)
    {
        sender.sendMessage("Not implemented");
    }

}
