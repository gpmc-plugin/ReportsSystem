package me.gpmcplugins.reportssystem.Update;

import me.gpmcplugins.reportssystem.Managers.UpdateManager;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

public class UpdatePopupThread extends Thread {
    public void run() {
        while (true) {
            UpdateManager.UpdateUpdatedState();
            try {
                //noinspection BusyWait
                Thread.sleep(ReportsSystem.getInstance().getConfigManager().getConfig().getInt("UpdateCheckInterval"));
            } catch (InterruptedException ignored) {
                break;
            }
        }
    }
}
