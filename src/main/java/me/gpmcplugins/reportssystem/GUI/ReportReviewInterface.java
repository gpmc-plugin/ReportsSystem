package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
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
                .setItem(11, claimNewReportIconItemStack, "report-review claimnewreportgui 0", false)
                .setItem(15, continueReportIconItemStack, "report-review continueclaimedreportgui 0", false)
                .showGUI(p);
    }

    public static void ClaimOrContinueReportMenu(Player p, int page, String adminid) {
        ChestGUI gui = new ChestGUI(54).setTitle("<gradient:#f857a6:#ff5858>Przejmij report</gradient>");

        if(adminid != null)
            gui.setTitle("<gradient:#f857a6:#ff5858>Kontynuuj report</gradient>");

        List<ReportObject> reportObjectList;
        boolean isLastPage;

        try {
            Integer reports = plugin.getDatabaseManager().getAdminReportsCount(adminid, DatabaseManager.openStatus.OPEN);
            reportObjectList = plugin.getDatabaseManager().getAdminReports(adminid,4,page, DatabaseManager.openStatus.OPEN);
            isLastPage = 4*(page+1)>=reports;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(reportObjectList.size() == 0)
        {
            gui.setItem(22, noReportsIconItemItemStack);
        }

        for (int i = 0; i < reportObjectList.size(); i++) {
            ReportObject reportObject = reportObjectList.get(i);
            int position = 9+i*9;
            ItemStack reportIconItemStack = ChestGUI.setItemStackName(Component.text(reportObject.id, itemColor, TextDecoration.BOLD), new ItemStack(Material.WRITABLE_BOOK));

            gui.setItem(position+1, reportIconItemStack)
                .setItem(position+2, GetItemReportByReportType(reportObject.type))
                .setItem(position+3, GetItemReportByType(reportObject.shortDescription))
                .setItem(position+6, acceptReportIconItemStack,
                        "report-review accept " + reportObject.id, true)
                .setItem(position+7, denyReportIconItemStack,
                        "report-review deny " + reportObject.id, true)
                .setItem(position, ChestGUI.getPlayerSkull(reportObject.reportingUser));

            if(adminid == null)
                gui.setItem(position+5, claimReportIconItemStack,
                        "report-review claim " + reportObject.id, true);

            if(reportObject.type == ReportCreator.ReportType.Death)
                gui.setItem(position+4, openIneventoryItemItemStack,
                        "report-view-death-inventory " + reportObject.id, false);
        }
        gui.setItem(49, backItemItemStack, "report-review", false);
        if(page != 0)
            gui.setItem(48, pageBackItemItemStack, "report-review claimnewreportgui " + (page-1), false);
        if(!isLastPage)
            gui.setItem(50, pageNextItemItemStack, "report-review claimnewreportgui " + (page+1), false);

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
