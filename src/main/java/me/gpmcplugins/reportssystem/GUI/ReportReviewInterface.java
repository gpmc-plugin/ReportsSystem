package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.objects.ReportMessage;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.*;

public class ReportReviewInterface {
    public static ReportsSystem plugin;

    public static void setup(ReportsSystem plugin) {
        ReportReviewInterface.plugin = plugin;
    }

    public static void MainReviewMenu(Player p) {
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz rodzaj akcji ktora chcesz wykonac</gradient>")
                .setItem(11, claimNewReportIconItemStack, "report-review claimnewreportgui 0", false)
                .setItem(15, continueReportIconItemStack, "report-review continueclaimedreportgui 0", false)
                .showGUI(p);
    }

    public static void ClaimOrContinueReportMenu(Player p, int page, String adminid) {
        p.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);
        p.sendMessage(Component.text("Otwieramy dla ciebie to gui może nam to zająć chwilę prosimy o cierpliwość.", NamedTextColor.GREEN));
        new Thread(){
            public void run(){
                ChestGUI gui = new ChestGUI(54).setTitle("<gradient:#f857a6:#ff5858>Przejmij report</gradient>");
                String reportReviewArgument;
                if (adminid == null)
                    reportReviewArgument = "claimnewreportgui";
                else
                    reportReviewArgument = "continueclaimedreportgui";
                if (adminid != null)
                    gui.setTitle("<gradient:#f857a6:#ff5858>Kontynuuj report</gradient>");

                gui.setReloadCommand(String.format("report-review %s %s", reportReviewArgument, page));

                List<ReportObject> reportObjectList;
                boolean isLastPage;

                try {
                    Integer reports = plugin.getDatabaseManager().getAdminReportsCount(adminid, DatabaseManager.OpenStatus.OPEN);
                    reportObjectList = plugin.getDatabaseManager().getAdminReports(adminid, 4, page, DatabaseManager.OpenStatus.OPEN);
                    isLastPage = 4 * (page + 1) >= reports;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (reportObjectList.size() == 0) {
                    gui.setItem(22, noReportsIconItemItemStack);
                }

                for (int i = 0; i < reportObjectList.size(); i++) {
                    ReportObject reportObject = reportObjectList.get(i);
                    int position = 9 + i * 9;

                    ItemStack reportBook = reportObject.getItemStack();

                    gui.setItem(position + 1, reportBook)
                            .setItem(position + 2, GetItemReportByReportType(reportObject.getType()))
                            .setItem(position + 3, GetItemReportByType(reportObject.getShortDescription()))
                            .setItem(position + 6, acceptReportIconItemStack,
                                    "report-review accept " + reportObject.getId(), true)
                            .setItem(position + 7, denyReportIconItemStack,
                                    "report-review deny " + reportObject.getId(), true)
                            .setItem(position, ChestGUI.getPlayerSkull(reportObject.getReportingUser()));

                    if (adminid == null)
                        gui.setItem(position + 5, claimReportIconItemStack,
                                "report-review claim " + reportObject.getId(), true);

                    if (reportObject.getType() == ReportCreator.ReportType.Death)
                        gui.setItem(position + 4, openIneventoryItemItemStack,
                                "report-view-death-inventory " + reportObject.getId(), false);
                }
                gui.setItem(49, backItemItemStack, "report-review", false);
                if (page != 0)
                    gui.setItem(48, pageBackItemItemStack, "report-review " + reportReviewArgument + " " + (page - 1), false);
                if (!isLastPage)
                    gui.setItem(50, pageNextItemItemStack, "report-review  " + reportReviewArgument + " " + (page + 1), false);
                ReportsSystem.getInstance().getServer().getScheduler().runTask(ReportsSystem.getInstance(), () -> gui.showGUI(p));
                this.interrupt();
            }
        }.start();
    }

    public static @NotNull ItemStack GetItemReportByReportType(ReportCreator.ReportType reportType) {
        switch (reportType) {
            case User:
                return userReportIconItemStack;
            case Message:
                return messageReportIconItemStack;
            case Death:
                return deathReportIconItemStack;
        }
        return otherIconItemStack;
    }

    public static @NotNull ItemStack GetItemReportByType(ReportCreator.ReportShortDescription shortDescription) {
        switch (shortDescription) {
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
