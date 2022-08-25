package me.gpmcplugins.reportssystem.Update;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

public class UpdateThread extends Thread{
    ReportsSystem plugin;
    public UpdateThread(ReportsSystem pl)
    {
        plugin = pl;
    }
    public void run() {
        plugin.getUpdateManager().update();
    }
}
