package me.gpmcplugins.reportssystem.objects;

import org.bukkit.OfflinePlayer;

public class ReportMessage {
    private final OfflinePlayer player;
    private final String message;

    public ReportMessage(OfflinePlayer sender, String message) {
        this.player = sender;
        this.message = message;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
