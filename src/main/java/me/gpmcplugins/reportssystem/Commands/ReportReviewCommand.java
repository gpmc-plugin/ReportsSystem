package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.GUI.ReportReviewInterface;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ReportReviewCommand implements CommandExecutor {

    private final ReportsSystem plugin;
    public ReportReviewCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        Player p = (Player) sender;
        if (args.length == 0)
        {
            ReportReviewInterface.MainReviewMenu(p);
            return true;
        }

        switch (args[0])
        {
            case "claimnewreportgui":
                ReportReviewInterface.ClaimNewReportMenu(p);
                break;
            case "continueclaimedreportgui":
                ReportReviewInterface.ContinueClaimedReportMenu(p);
                break;
            case "claim":
                if (args[1] == null)
                {
                    sender.sendMessage("you forgot about report id");
                    return false;
                }
                Integer claimId = Integer.parseInt(args[1]);
                try {
                    ReportObject reportObject = plugin.getDatabaseManager().getReport(claimId);
                    reportObject.setAdmin(p.getUniqueId().toString());
                    p.sendMessage("Successfully claimed report with id " + reportObject.id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "accept":
                if (args[1] == null)
                {
                    sender.sendMessage("you forgot about report id");
                    return false;
                }
                Integer acceptId = Integer.parseInt(args[1]);
                try {
                    ReportObject reportObject = plugin.getDatabaseManager().getReport(acceptId);
                    reportObject.setReportStatus(ReportObject.ReportStatus.Accepted);
                    p.sendMessage("Successfully accepted report with id " + reportObject.id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "deny":
                if (args[1] == null)
                {
                    sender.sendMessage("you forgot about report id");
                    return false;
                }
                Integer denyId = Integer.parseInt(args[1]);
                try {
                    ReportObject reportObject = plugin.getDatabaseManager().getReport(denyId);
                    reportObject.setReportStatus(ReportObject.ReportStatus.Denided);
                    p.sendMessage("Successfully denied report with id " + reportObject.id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        return true;
    }
}
