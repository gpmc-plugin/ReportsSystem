package me.gpmcplugins.reportssystem.Update;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

import java.io.IOException;

public class UpdateThread extends Thread{
    ReportsSystem plugin;
    public UpdateThread(ReportsSystem pl)
    {
        plugin = pl;
    }
    public void run() {
        try {
            plugin.getUpdateManager().update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
