package me.gpmcplugins.reportssystem.objects;

import org.bukkit.entity.Player;

public class ReportMessage {
    private final  Player player;
    private final String message;
    public ReportMessage(Player sender, String message){
        this.player=sender;
        this.message=message;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
