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
import org.bukkit.plugin.java.JavaPlugin;

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
        p.sendMessage("aaaszylazcmdaaa");
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
            gui.setItem(position, ChestGUI.setItemStackName(Component.text(reportObject.id, itemColor, TextDecoration.BOLD), new ItemStack(Material.WRITTEN_BOOK)), "/say " + reportObjectList.get(i).id);
            gui.setItem(position+1, GetItemReportByType(reportObject.type));
        }
        gui.showGUI(p);
    }

    public static ItemStack GetItemReportByType(ReportCreator.ReportType reportType)
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
        return null;
    }

}
