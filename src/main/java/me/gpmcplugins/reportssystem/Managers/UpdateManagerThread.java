package me.gpmcplugins.reportssystem.Managers;

public class UpdateManagerThread extends Thread{
    public void run() {
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            UpdateManager.UpdateUpdatedState();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
