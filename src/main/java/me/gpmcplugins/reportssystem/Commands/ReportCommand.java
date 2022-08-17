package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.GUI.ChestReportTypeInterface;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import me.gpmcplugins.reportssystem.objects.ReportCreator.ReportType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

// a class for report command
public final class ReportCommand implements TabExecutor {
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

        if(args.length ==0)
        {
            p.sendMessage("");
        }

        switch (args[0].toLowerCase())
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
        String reportedId = args[1];
        switch (reportType) {
            case Death:
                int nextDeathId = plugin.getDatabaseManager().getNextDeathID();
                if (Integer.parseInt(reportedId) >= nextDeathId)
                {
                    p.sendMessage("Death ID is incorrect");
                    return false;
                }
                //((Player) sender).openBook(BookReportTypeInterface.deathReportInterface());
                ChestReportTypeInterface.deathReportInterface((Player) sender);
                break;
            case Message:
                int nextMsgId = plugin.getDatabaseManager().getNextMessageID();
                if (Integer.parseInt(reportedId) >= nextMsgId)
                {
                    p.sendMessage("Message ID is incorrect");
                    return false;
                }
                //((Player) sender).openBook(BookReportTypeInterface.messageReportInterface());
                ChestReportTypeInterface.messageReportInterface((Player) sender);
                break;
            case User:

                Player reportedPlayer = plugin.getServer().getPlayer(reportedId);
                if (reportedPlayer == null){
                    TextComponent txt = Component.text("Player with name ")
                            .color(NamedTextColor.RED)
                            .append(Component.text(reportedId, NamedTextColor.RED, TextDecoration.BOLD))
                            .append(Component.text(" does not exist"));
                    p.sendMessage(txt);
                    return true;
                }
                else
                    reportedId=reportedPlayer.getUniqueId().toString();
                //((Player) sender).openBook(BookReportTypeInterface.userReportInterface());
                ChestReportTypeInterface.userReportInterface((Player) sender);
                break;
        }
        Integer reportID = plugin.getStorageManager().getReportInProgressID();
        plugin.getStorageManager().incrementReportsID();
        ReportCreator report = plugin.getStorageManager().createReportTemplate(reportID,reportType,reportedId,((Player) sender).getUniqueId().toString());
        return report != null;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1)
        {
            List<String> enumArgs = new ArrayList<>();
            for (ReportType value : ReportType.values()) {
                enumArgs.add(value.name());
            }
            return enumArgs;
        }
        return null;
    }
}
