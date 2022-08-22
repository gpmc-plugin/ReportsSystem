package me.gpmcplugins.reportssystem.Update;

import me.gpmcplugins.reportssystem.Managers.UpdateManager;

public class UpdatePopupThread extends Thread{
    public void run() {
        while (true) {
            UpdateManager.UpdateUpdatedState();
            try {
                Thread.sleep(30000);
            } catch (InterruptedException ignored) {

            }
        }
    }
}
