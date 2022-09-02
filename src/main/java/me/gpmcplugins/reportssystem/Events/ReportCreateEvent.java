package me.gpmcplugins.reportssystem.Events;

import me.gpmcplugins.reportssystem.objects.ReportObject;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReportCreateEvent extends Event {
    private final ReportObject report;
    private final Player creatingPlayer;
    public ReportCreateEvent(ReportObject report, Player creatingPlayer){

        this.report = report;
        this.creatingPlayer = creatingPlayer;
    }
    private static final HandlerList HANDLERS = new HandlerList();
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @SuppressWarnings("unused")
    public Player getPlayer(){
        return this.creatingPlayer;
    }
    @SuppressWarnings("unused")
    public ReportObject getReport() {
        return report;
    }
}
