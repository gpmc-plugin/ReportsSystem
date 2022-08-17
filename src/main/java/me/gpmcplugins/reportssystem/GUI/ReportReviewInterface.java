package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.*;

public class ReportReviewInterface {
    public static ReportsSystem plugin;

    public static void setup(ReportsSystem plugin)
    {
        ReportReviewInterface.plugin = plugin;
    }

    public static void MainReviewMenu(Player p)
    {
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz rodzaj akcji ktora chcesz wykonac</gradient>")
                .setItem(11, claimNewReportIconItemStack, "report-review claimnewreportgui")
                .setItem(15, continueReportIconItemStack, "report-review continueclaimedreportgui")
                .showGUI(p);
    }

    public static void ClaimNewReportMenu(Player p) {
        p.sendMessage("szylazcmd");
        ChestGUI gui = new ChestGUI(54).setTitle("<gradient:#f857a6:#ff5858>Wybierz rodzaj akcji ktora chcesz wykonac</gradient>");
        List<ReportObject> reportObjectList;
        try {
            reportObjectList = plugin.getDatabaseManager().getLastReports(4, 0,true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < reportObjectList.size(); i++) {
            ReportObject reportObject = reportObjectList.get(i);
            int position = 10+i*9;
            ItemStack reportIconItemStack = ChestGUI.setItemStackName(Component.text(reportObject.id, itemColor, TextDecoration.BOLD), new ItemStack(Material.WRITTEN_BOOK));



            p.sendMessage(reportObjectList.get(i).id.toString());
            gui.setItem(position, reportIconItemStack);
            gui.setItem(position+1, GetItemReportByReportType(reportObject.type));
            gui.setItem(position+2, GetItemReportByType(reportObject.shortDescription));
            gui.setItem(position+4, claimReportIconItemStack, "report-review claim " + reportObject.id);
            gui.setItem(position+5, acceptReportIconItemStack, "report-review accept " + reportObject.id);
            gui.setItem(position+6, denyReportIconItemStack, "report-review deny " + reportObject.id);
        }
        gui.showGUI(p);
    }

    public static @NotNull ItemStack GetItemReportByReportType(ReportCreator.ReportType reportType)
    {
        switch (reportType)
        {
            case User:
                return userReportIconItemStack;
            case Message:
                return  messageReportIconItemStack;
            case Death:
                return  deathReportIconItemStack;
        }
        return otherIconItemStack;
    }

    public static @NotNull ItemStack GetItemReportByType(ReportCreator.ReportShortDescription shortDescription)
    {
        switch (shortDescription)
        {
            case Other:
            case Death_Other_Player:
                return otherIconItemStack;
            case Death_Bug:
                return bugIconItemStack;
            case User_Scam:
            case Message_Scam:
                return fraudIconItemStack;
            case User_Cheating:
                return cheatingOrExploiningIconItemStack;
            case Message_Bad_Words:
                return swearingIconItemStack;
            case Message_Offensive:
                return offendingIconItemStack;
            case Message_Hate_Speach:
                return speakingWrongIconItemStack;
        }
        return otherIconItemStack;
    }
}
