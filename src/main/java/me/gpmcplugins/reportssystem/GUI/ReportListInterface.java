package me.gpmcplugins.reportssystem.GUI;

import me.gpmcplugins.reportssystem.Managers.DatabaseManager;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.objects.ReportMessage;
import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.objects.ReportObject.ReportStatus;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ReportListInterface {
    private static ReportsSystem plugin;

    public static void setup(ReportsSystem plugin) {
        ReportListInterface.plugin = plugin;
    }

    public static void AllReportsMenu(Player p, Integer[] positions, Integer limit, Integer page) {
        Integer reportsCount;
        try {
            reportsCount = plugin.getDatabaseManager().getUserReportsCount(p.getUniqueId().toString(), DatabaseManager.OpenStatus.ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReportObject> reports;
        try {
            reports = (ArrayList<ReportObject>) plugin.getDatabaseManager().getUserReports(p.getUniqueId().toString(), limit, page, DatabaseManager.OpenStatus.ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ChestGUI gui = new ChestGUI(54);
        gui.setTitle("<gradient:#f857a6:#ff5858>Twoje zgłoszenia</gradient>");
        for (int i = 0; i < reports.size(); i++) {

            ReportObject report = reports.get(i);
            ItemStack reportBook = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta reportMeta = reportBook.getItemMeta();
            reportMeta.displayName(Component.text(String.format("Report o ID %s", report.id)));
            ArrayList<Component> reportLore = new ArrayList<>();
            reportLore.add(Component.text(String.format("Krótki opis: %s", report.shortDescription)));
            reportLore.add(Component.text(String.format("Opis: %s", report.description)));
            switch (report.type) {
                case Message:
                    ReportMessage reportMessage = plugin.getDatabaseManager().getMessage(Integer.parseInt(report.reportedID));
                    reportLore.add(Component.text(String.format("Zgłoszona wiadomość: %s", reportMessage.message)));
                    reportLore.add(Component.text(String.format("Autor zgłoszonej wiadomości: %s", reportMessage.player)));
                    break;
                case Death:
                    reportLore.add(Component.text("Kliknij by zobaczyć swój ekwipunek", NamedTextColor.WHITE, TextDecoration.ITALIC));
                    break;
                case User:
                    String reportedUser = Objects.requireNonNull(plugin.getServer().getPlayer(UUID.fromString(report.reportedID))).getName();
                    reportLore.add(Component.text(String.format("Zgłoszony użytkownik: %s", reportedUser)));
                    break;
            }
            reportMeta.lore(reportLore);
            reportBook.setItemMeta(reportMeta);

            if (report.reportStatus == ReportStatus.In_Progress) {reportBook.setType(Material.WRITABLE_BOOK);}
                if (report.type == ReportCreator.ReportType.Death) gui.setItem(positions[i], reportBook, String.format("rdi %s", report.id), false);
                else gui.setItem(positions[i], reportBook);
        }
        if (page != 0)
        {
            gui.setItem(48, IconItemstack.pageBackItemItemStack, "/r-l wszystkie" + (page-1), false);
        }
        if (limit*(page+1)<reportsCount)
        {
            gui.setItem(50, IconItemstack.pageNextItemItemStack, "/r-l wszystkie" + (page+1), false);
        }
        gui.showGUI(p);
    }

    public static void OpenReportsMenu(Player p, Integer[] positions, int limit, int page) {
        Integer reportsCount;
        try {
            reportsCount = plugin.getDatabaseManager().getUserReportsCount(p.getUniqueId().toString(), DatabaseManager.OpenStatus.OPEN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReportObject> reports;
        try {
            reports = (ArrayList<ReportObject>) plugin.getDatabaseManager().getUserReports(p.getUniqueId().toString(), limit, page, DatabaseManager.OpenStatus.OPEN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ChestGUI gui = new ChestGUI(54);
        gui.setTitle("<gradient:#f857a6:#ff5858>Twoje zgłoszenia</gradient>");
        for (int i = 0; i < reports.size(); i++) {

            ReportObject report = reports.get(i);
            ItemStack reportBook = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta reportMeta = reportBook.getItemMeta();
            reportMeta.displayName(Component.text(String.format("Report o ID %s", report.id)));
            ArrayList<Component> reportLore = new ArrayList<>();
            reportLore.add(Component.text(String.format("Krótki opis: %s", report.shortDescription)));
            reportLore.add(Component.text(String.format("Opis: %s", report.description)));
            switch (report.type) {
                case Message:
                    ReportMessage reportMessage = plugin.getDatabaseManager().getMessage(Integer.parseInt(report.reportedID));
                    reportLore.add(Component.text(String.format("Zgłoszona wiadomość: %s", reportMessage.message)));
                    reportLore.add(Component.text(String.format("Autor zgłoszonej wiadomości: %s", reportMessage.player)));
                    break;
                case Death:
                    reportLore.add(Component.text("Kliknij by zobaczyć swój ekwipunek", NamedTextColor.WHITE, TextDecoration.ITALIC));
                    break;
                case User:
                    String reportedUser = Objects.requireNonNull(plugin.getServer().getPlayer(UUID.fromString(report.reportedID))).getName();
                    reportLore.add(Component.text(String.format("Zgłoszony użytkownik: %s", reportedUser)));
                    break;
            }
            reportMeta.lore(reportLore);
            reportBook.setItemMeta(reportMeta);

            if (report.reportStatus == ReportStatus.In_Progress) {reportBook.setType(Material.WRITABLE_BOOK);}
            if (report.type == ReportCreator.ReportType.Death) gui.setItem(positions[i], reportBook, String.format("rdi %s", report.id), false);
            else gui.setItem(positions[i], reportBook);
        }
        if (page != 0)
        {
            gui.setItem(48, IconItemstack.pageBackItemItemStack, "/r-l otwarte" + (page-1), false);
        }
        if (limit*(page+1)<reportsCount)
        {
            gui.setItem(50, IconItemstack.pageNextItemItemStack, "/r-l otwarte" + (page+1), false);
        }
        gui.showGUI(p);
    }

    public static void ClosedReportsMenu(Player p, Integer[] positions, int limit, int page) {
        Integer reportsCount;
        try {
            reportsCount = plugin.getDatabaseManager().getUserReportsCount(p.getUniqueId().toString(), DatabaseManager.OpenStatus.NOT_OPEN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReportObject> reports;
        try {
            reports = (ArrayList<ReportObject>) plugin.getDatabaseManager().getUserReports(p.getUniqueId().toString(), limit, page, DatabaseManager.OpenStatus.NOT_OPEN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ChestGUI gui = new ChestGUI(54);
        gui.setTitle("<gradient:#f857a6:#ff5858>Twoje zgłoszenia</gradient>");
        for (int i = 0; i < reports.size(); i++) {

            ReportObject report = reports.get(i);
            ItemStack reportBook = new ItemStack(Material.WRITTEN_BOOK);
            ItemMeta reportMeta = reportBook.getItemMeta();
            reportMeta.displayName(Component.text(String.format("Report o ID %s", report.id)));
            ArrayList<Component> reportLore = new ArrayList<>();
            reportLore.add(Component.text(String.format("Krótki opis: %s", report.shortDescription)));
            reportLore.add(Component.text(String.format("Opis: %s", report.description)));
            switch (report.type) {
                case Message:
                    ReportMessage reportMessage = plugin.getDatabaseManager().getMessage(Integer.parseInt(report.reportedID));
                    reportLore.add(Component.text(String.format("Zgłoszona wiadomość: %s", reportMessage.message)));
                    reportLore.add(Component.text(String.format("Autor zgłoszonej wiadomości: %s", reportMessage.player)));
                    break;
                case Death:
                    reportLore.add(Component.text("Kliknij by zobaczyć swój ekwipunek", NamedTextColor.WHITE, TextDecoration.ITALIC));
                    break;
                case User:
                    String reportedUser = Objects.requireNonNull(plugin.getServer().getPlayer(UUID.fromString(report.reportedID))).getName();
                    reportLore.add(Component.text(String.format("Zgłoszony użytkownik: %s", reportedUser)));
                    break;
            }
            reportMeta.lore(reportLore);
            reportBook.setItemMeta(reportMeta);

            if (report.reportStatus == ReportStatus.In_Progress) {reportBook.setType(Material.WRITABLE_BOOK);}
            if (report.type == ReportCreator.ReportType.Death) gui.setItem(positions[i], reportBook, String.format("rdi %s", report.id), false);
            else gui.setItem(positions[i], reportBook);
        }
        if (page != 0)
        {
            gui.setItem(48, IconItemstack.pageBackItemItemStack, "/r-l zamknięte" + (page-1), false);
        }
        if (limit*(page+1)<reportsCount)
        {
            gui.setItem(50, IconItemstack.pageNextItemItemStack, "/r-l zamknięte" + (page+1), false);
        }
        gui.showGUI(p);
    }
}
