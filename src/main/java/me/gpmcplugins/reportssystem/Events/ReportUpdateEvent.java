package me.gpmcplugins.reportssystem.Events;

import me.gpmcplugins.reportssystem.objects.ReportObject;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReportUpdateEvent extends Event {
    private final ReportObject newState;
    private final ReportObject oldState;
    private final Player updatingPlayer;
    private final ChangeType changeType;

    public ReportUpdateEvent(ReportObject newState, ReportObject oldState, Player updatingPlayer, ChangeType changeType) {
        this.changeType = changeType;
        this.newState = newState;
        this.oldState = oldState;
        this.updatingPlayer = updatingPlayer;
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
    public Player getPlayer() {
        return this.updatingPlayer;
    }

    @SuppressWarnings("unused")
    public ReportObject getNewState() {
        return this.newState;
    }
    @SuppressWarnings("unused")
    public ReportObject getOldState() {
        return this.oldState;
    }
    @SuppressWarnings("unused")
    public ChangeType getChangeType() {
        return changeType;
    }
    @SuppressWarnings("unused")
    public enum ChangeType {
        AdminChange,
        StateChange
    }
}
