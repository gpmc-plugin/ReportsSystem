package me.gpmcplugins.reportssystem.Replay;

import me.gpmcplugins.reportssystem.objects.ReportMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Timer;
import java.util.TimerTask;

public class ReplayPlayer {
    ReplayObject replayObject;
    private Integer actualID;
    private Timer timer;
    private double speed = 2;
    private boolean running = false;
    private double speedcalculated = 1/ speed;
    public ReplayPlayer(ReplayObject replayObject){
        this.replayObject=replayObject;
        this.actualID=replayObject.getFromID();
    }
    public void restart(){
        actualID= replayObject.getFromID();
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        this.speedcalculated = 1/speed;
        if(isRunning()) {
            stop();
            start();
        }
    }

    public void moveForward(Long messages){
        Integer aid = actualID;
        stop();
        for(int i=0;i<messages;i++){
            sendMessage();
            if(aid>actualID)
                break;
        }
        if(aid<actualID)
            start();
    }
    public void stop(){
        timer.cancel();
        running=false;
    }
    public void start(){
        running=true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendMessage();
            }
        },Long.parseLong(String.valueOf((int)Math.ceil(speedcalculated*1000))),Long.parseLong(String.valueOf((int)Math.ceil(speedcalculated*1000))));
    }
    public boolean isRunning(){
        return running;
    }
    private void sendMessage(){
        ReportMessage reportMessage = replayObject.getPlugin().getDatabaseManager().getMessage(actualID);
        if(reportMessage==null){
            stop();
            restart();
            replayObject.onEnd();
            return;
        }
        replayObject.getPlayer().sendMessage(Component.text("["+reportMessage.player.getName()+"] ", NamedTextColor.GREEN).append(Component.text(reportMessage.message,NamedTextColor.WHITE)));
        actualID++;
    }
}
