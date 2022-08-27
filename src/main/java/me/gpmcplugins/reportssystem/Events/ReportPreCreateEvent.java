package me.gpmcplugins.reportssystem.Events;

import me.gpmcplugins.reportssystem.objects.ReportCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReportPreCreateEvent extends Event implements Cancellable {
    private final ReportCreator reportCreator;
    private final Player creatingPlayer;
    public ReportPreCreateEvent(ReportCreator reportCreator, Player creatingPlayer){

        this.reportCreator = reportCreator;
        this.creatingPlayer = creatingPlayer;
    }
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled=cancel;
    }
    @SuppressWarnings("unused")
    public Player getPlayer(){
        return this.creatingPlayer;
    }
    @SuppressWarnings("unused")
    public ReportCreator getReportCreator() {
        return reportCreator;
    }
}
