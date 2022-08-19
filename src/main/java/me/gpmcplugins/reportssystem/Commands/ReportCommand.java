package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.GUI.ChestReportTypeInterface;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.objects.ReportDeath;
import me.gpmcplugins.reportssystem.objects.ReportMessage;
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
import java.util.ArrayList;
import java.util.List;

// a class for report command
public final class ReportCommand implements TabExecutor {
    private final ReportsSystem plugin;
    public ReportCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command.");
            return true;
        }

        Player p = (Player) sender;
        ReportType reportType;

        if (args.length < 2)
        {
            return true;
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
                ReportDeath reportDeath = plugin.getDatabaseManager().getDeath(Integer.valueOf(reportedId));
                if(!reportDeath.noob.getUniqueId().toString().equals(p.getUniqueId().toString())){
                    p.sendMessage(Component.text("Możesz reportować tylko swoje śmierci!",NamedTextColor.RED));
                }
                ChestReportTypeInterface.deathReportInterface((Player) sender);
                break;
            case Message:
                int nextMsgId = plugin.getDatabaseManager().getNextMessageID();
                if (Integer.parseInt(reportedId) >= nextMsgId)
                {
                    p.sendMessage("Message ID is incorrect");
                    return false;
                }
                ReportMessage reportMessage= plugin.getDatabaseManager().getMessage(Integer.valueOf(reportedId));
                if(p.getUniqueId().toString().equals(reportMessage.player.getUniqueId().toString())){
                    sender.sendMessage(Component.text("Nie możesz zreportować samego siebie!!", NamedTextColor.RED));
                    return true;
                }
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
                else{
                    if(reportedPlayer.getUniqueId().toString().equals(((Player) sender).getUniqueId().toString())) {
                        sender.sendMessage(Component.text("Nie możesz zreportować samego siebie!!", NamedTextColor.RED));
                        return true;
                    }
                    reportedId=reportedPlayer.getUniqueId().toString();
                }

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
    public @NotNull List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> tabArgs = new ArrayList<>();
        if (args.length == 1)
        {
            tabArgs.add("User");
            return tabArgs;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("user"))
        {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                tabArgs.add(player.getName());
            }
        }
        return tabArgs;
    }
}
