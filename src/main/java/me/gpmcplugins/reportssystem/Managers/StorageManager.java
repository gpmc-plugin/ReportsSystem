package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.Replay.ReplayObject;
import me.gpmcplugins.reportssystem.objects.PlayerReportCreationStatus;
import me.gpmcplugins.reportssystem.objects.ReportCreator;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    private final Map<Integer, ReportCreator> reportsInProgress = new HashMap<>();
    private final Map<String, PlayerReportCreationStatus> playerReportCreationStatuses = new HashMap<>();
    private int reportInProgressID = 0;
    private final Map<String, ReplayObject> replays = new HashMap<>();
    private final ReportsSystem plugin;

    public StorageManager(ReportsSystem plugin) {
        this.plugin = plugin;
    }

    public ReportCreator createReportTemplate(Integer id, ReportCreator.ReportType reportType, String reportedElementID, String reportingPlayer) {
        PlayerReportCreationStatus player = getUser(reportingPlayer);
        if (player.getReportID() != null)
            player.clearReport();
        ReportCreator reportCreator = new ReportCreator(reportType, reportingPlayer, reportedElementID, plugin);
        reportsInProgress.put(id, reportCreator);
        player.setReportID(id);
        return reportCreator;
    }

    public ReportCreator getReport(Integer id) {
        return reportsInProgress.getOrDefault(id, null);
    }

    public int getReportInProgressID() {
        return reportInProgressID;
    }

    public void incrementReportsID() {
        reportInProgressID++;
    }

    public void addUser(String uuid) {
        PlayerReportCreationStatus player = new PlayerReportCreationStatus(uuid, this.plugin);
        playerReportCreationStatuses.put(uuid, player);
    }

    public void removeUser(String uuid) {
        playerReportCreationStatuses.remove(uuid);
    }

    public PlayerReportCreationStatus getUser(String uuid) {
        return playerReportCreationStatuses.getOrDefault(uuid, null);
    }

    public ReplayObject createReplay(Player player, Integer fromID, Integer susID) {
        ReplayObject replayObject = new ReplayObject(player, plugin, fromID, susID);
        replays.put(player.getUniqueId().toString(), replayObject);
        return replayObject;

    }

    public ReplayObject getReplay(String uuid) {
        return replays.getOrDefault(uuid, null);
    }

    public void removeReplay(String uuid) {
        ReplayObject replayObject = getReplay(uuid);
        if (replayObject != null)
            replayObject.closeReply();
    }

    public void onDisable() {
        for (ReplayObject value : replays.values()) {
            value.closeReply();
        }
    }
}
