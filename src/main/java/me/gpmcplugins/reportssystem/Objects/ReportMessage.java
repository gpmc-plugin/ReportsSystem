package me.gpmcplugins.reportssystem.Objects;

import org.bukkit.entity.Player;

public class ReportMessage {
    public Player player;
    public String message;
    public ReportMessage(Player sender, String message){
        this.player=sender;
        this.message=message;
    }
}
