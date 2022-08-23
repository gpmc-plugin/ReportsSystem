package me.gpmcplugins.reportssystem.Update;

import me.gpmcplugins.reportssystem.Managers.UpdateManager;

public class UpdatePopupThread extends Thread{
    public void run() {
        while (true) {
            UpdateManager.UpdateUpdatedState();
            try {
                //noinspection BusyWait
                Thread.sleep(5*60*1000);
            } catch (InterruptedException ignored) {
                break;
            }
        }
    }
}
