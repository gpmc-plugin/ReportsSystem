package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.format.TextColor;
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
                break;
            case "message":
                reportType = ReportType.Message;
                break;
            case "user":
                reportType = ReportType.User;
                break;
            default:
                return false;
        }
        switch (reportType) {
            case Death:
                sendNotImplemented(p);
                return false;
            case Message:
                int nextMsgId = plugin.getDatabaseManager().getNextMessageID();
                if (Integer.parseInt(args[1]) >= nextMsgId)
                {
                    p.sendMessage("Message ID is incorrect");
                    return false;
                }
                break;
            case User:
                String reportedUser = args[1];
                if (plugin.getServer().getPlayer(reportedUser) == null){
                    TextComponent txt = Component.text("Player with name ")
                            .color(NamedTextColor.RED)
                            .append(Component.text(reportedUser, NamedTextColor.RED, TextDecoration.BOLD))
                            .append(Component.text(" does not exist"));
                    p.sendMessage(txt);
                    return true;
                }
                break;
        }
        Integer reportID = plugin.getStorageManager().getReportInProgressID();
        plugin.getStorageManager().incrementReportsID();
        ReportCreator cos = plugin.getStorageManager().createReportTemplate(reportID,reportType,args[1],((Player) sender).getUniqueId().toString());
        if(cos == null)
            return false;
        return true;
    }
    public void sendNotImplemented(Player p)
    {
        p.sendMessage("Not implemented");
    }
}
