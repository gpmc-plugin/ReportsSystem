package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Events.ReportUpdateEvent;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class ReportObject {
    private final Integer id;
    private final OfflinePlayer reportingUser;
    private final ReportsSystem plugin;
    private final ReportCreator.ReportType type;
    private final ReportCreator.ReportShortDescription shortDescription;
    private final String reportedID;
    private final String description;
    private OfflinePlayer admin;
    private final Date timestamp;
    private final ReportStatus reportStatus;

    public ReportObject(Integer id, String reportingUser, String type, String shortDescription, String reportedID, String description, String admin, Long timestamp, ReportsSystem plugin, String status) {
        this.id = id;
        this.plugin = plugin;
        this.reportingUser = Bukkit.getOfflinePlayer(UUID.fromString(reportingUser));
        this.type = ReportCreator.ReportType.valueOf(type);
        this.shortDescription = ReportCreator.ReportShortDescription.valueOf(shortDescription);
        this.reportedID = reportedID;
        this.description = description;
        if (admin != null)
            this.admin = Bukkit.getOfflinePlayer(UUID.fromString(admin));
        this.timestamp = new Date(timestamp);
        if (status == null)
            this.reportStatus = ReportStatus.In_Progress;
        else
            this.reportStatus = ReportStatus.valueOf(status);

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public ReportCreator.ReportType getType() {
        return type;
    }

    public ReportsSystem getPlugin() {
        return plugin;
    }

    public Integer getId() {
        return id;
    }

    public OfflinePlayer getReportingUser() {
        return reportingUser;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public String getReportedID() {
        return reportedID;
    }

    @SuppressWarnings("unused")
    public OfflinePlayer getAdmin() {
        return admin;
    }

    public ReportCreator.ReportShortDescription getShortDescription() {
        return shortDescription;
    }

    public void setReportStatus(ReportStatus reportStatus,Player updatingPlayer){
        Connection conn = plugin.getDatabaseManager().getConn();
        String sql = "UPDATE \"main\".\"reports\" SET \"status\"=? WHERE \"id\"=?";
        try {
            PreparedStatement prstm = conn.prepareStatement(sql);
            if (reportStatus == ReportStatus.In_Progress)
                reportStatus = null;
            assert reportStatus != null;
            prstm.setString(1, reportStatus.toString());
            prstm.setInt(2, this.id);
            prstm.execute();

        } catch (SQLException e) {
            plugin.getDatabaseManager().throwError(e.getMessage());
            return;
        }
        try {
            ReportObject newState = plugin.getDatabaseManager().getReport(this.id);
            ReportUpdateEvent event = new ReportUpdateEvent(newState,this,updatingPlayer, ReportUpdateEvent.ChangeType.StateChange);
            plugin.getServer().getPluginManager().callEvent(event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAdmin(String uid,Player updatingPlayer){
        this.admin=Bukkit.getOfflinePlayer(UUID.fromString(uid));
        Connection conn = plugin.getDatabaseManager().getConn();
        String sql = "UPDATE \"main\".\"reports\" SET \"admin\"=? WHERE \"id\"=?";
        try {
            PreparedStatement prstm = conn.prepareStatement(sql);
            prstm.setString(1, uid);
            prstm.setInt(2, this.id);
            prstm.execute();

        } catch (SQLException e) {
            plugin.getDatabaseManager().throwError(e.getMessage());
        }
        try {
            ReportObject newState = plugin.getDatabaseManager().getReport(this.id);
            ReportUpdateEvent event = new ReportUpdateEvent(newState,this,updatingPlayer, ReportUpdateEvent.ChangeType.AdminChange);
            plugin.getServer().getPluginManager().callEvent(event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("unused")
    public ReportObject getClone(){
        try {
            return (ReportObject) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public enum ReportStatus {
        In_Progress,
        Accepted,
        Denided,
    }

    public ItemStack getItemStack()
    {
        ItemStack reportBook = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta reportMeta = reportBook.getItemMeta();
        ArrayList<Component> reportLore = new ArrayList<>();
        Component name = Component.text(Objects.requireNonNull(this.getReportingUser().getName()));
        reportLore.add(Component.text("Reportujacy Gracz: ").append(name));
        reportLore.add(Component.text(String.format("Report o ID %s", this.getId())));
        reportLore.add(Component.text(String.format("Krótki opis: %s", this.getShortDescription())));
        reportLore.add(Component.text(String.format("Opis: %s", this.getDescription())));

        String pattern = "HH:mm.ss dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(this.getTimestamp());
        reportLore.add(Component.text(String.format("Data i Czas Reportu: %s", date)));

        Component reportMessage = Component.text("Cos poszlo nie tak!");
        switch (this.getType()) {
            case Message:
                ReportMessage message = ReportsSystem.getInstance().getDatabaseManager().getMessage(this.getId());
                Component messageAuthor = Component.text(Objects.requireNonNull(message.getPlayer().getName()));
                reportLore.add(
                        Component.text(String.format("Zgloszona Wiadomosc: %s", message.getMessage())));
                reportMessage = Component.text("Report Wiadomosci Gracza ")
                        .append(messageAuthor);
                break;
            case Death:
                reportMessage = Component.text("Report Smierci Gracza ")
                        .append(name);
                break;
            case User:
                reportMessage = Component.text("Report Gracza ")
                        .append(Objects.requireNonNull(plugin.getServer().getPlayer(UUID.fromString(this.getReportedID()))).name());
        }
        reportMeta.displayName(reportMessage);
        reportMeta.lore(reportLore);
        reportBook.setItemMeta(reportMeta);

        if (this.getReportStatus() == ReportStatus.In_Progress) {
            reportBook.setType(Material.WRITABLE_BOOK);
        }

        return reportBook;
    }
}
