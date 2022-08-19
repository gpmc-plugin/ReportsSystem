package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.objects.ReportObject.ReportStatus;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReportListInterface {
    private static ReportsSystem plugin;

    public static void setup(ReportsSystem plugin) {
        ReportListInterface.plugin = plugin;
    }

    public static void AllReportsMenu(Player p, Integer[] positions, Integer limit, Integer page) {
        Integer reportsCount;
        try {
            reportsCount = plugin.getDatabaseManager().getUserReportsCount(p.getUniqueId().toString(), DatabaseManager.openStatus.ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReportObject> reports;
        try {
            reports = (ArrayList<ReportObject>) plugin.getDatabaseManager().getUserReports(p.getUniqueId().toString(), limit, page, DatabaseManager.openStatus.ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ChestGUI gui = new ChestGUI(54);
        gui.setTitle((TextComponent) MiniMessage.miniMessage().deserialize("<gradient:#f857a6:#ff5858>Twoje zgłoszenia</gradient>"));
        for (int i = 0; i < reports.size(); i++) {

            ReportObject report = reports.get(i);
            ItemStack reportBook = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta reportMeta = reportBook.getItemMeta();
            reportMeta.displayName(Component.text(String.format("Report o ID %s", report.id)));
            ArrayList<Component> reportLore = new ArrayList<>();
            reportLore.add(Component.text(String.format("Krótki opis: %s", report.shortDescription)));
            reportLore.add(Component.text(String.format("Opis: %s", report.description)));
            reportMeta.lore(reportLore);
            reportBook.setItemMeta(reportMeta);

            if (report.reportStatus == ReportStatus.In_Progress) {
                reportBook.setType(Material.WRITABLE_BOOK);
                gui.setItem(positions[i], reportBook);
            } else {
                gui.setItem(positions[i], reportBook);
            }
        }
        gui.showGUI(p);
    }

    public static void OpenReportsMenu(Player p, Integer[] positions, int limit, int page) {
        ChestGUI gui = new ChestGUI(54);
        gui.setTitle((TextComponent) MiniMessage.miniMessage().deserialize("<gradient:#f857a6:#ff5858>Twoje otwarte zgłoszenia</gradient>"));
        ArrayList<ReportObject> reports;
        try {
            reports = (ArrayList<ReportObject>) plugin.getDatabaseManager().getUserReports(p.getUniqueId().toString(), limit, page, DatabaseManager.openStatus.OPEN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ClosedReportsMenu(Player p, Integer[] positions, int limit, int page) {
        ChestGUI gui = new ChestGUI(54);
        gui.setTitle((TextComponent) MiniMessage.miniMessage().deserialize("<gradient:#f857a6:#ff5858>Twoje otwarte zgłoszenia</gradient>"));
        ArrayList<ReportObject> reports;
        try {
            reports = (ArrayList<ReportObject>) plugin.getDatabaseManager().getUserReports(p.getUniqueId().toString(), limit, page, DatabaseManager.openStatus.NOT_OPEN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
