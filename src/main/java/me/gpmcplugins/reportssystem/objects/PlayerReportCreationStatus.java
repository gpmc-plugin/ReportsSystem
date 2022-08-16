package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Statics.ReportTranslater;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerReportCreationStatus {
    private String uuid;
    private Player player;
    private ReportsSystem plugin;
    private Integer reportID = null;
    private Integer state=0;
    public PlayerReportCreationStatus(String uuid, ReportsSystem plugin){
        this.plugin=plugin;
        this.uuid=uuid;
        player = plugin.getServer().getPlayer(UUID.fromString(this.uuid));

    }
    public void clearReport(){
        reportID=null;
        state=0;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getReportID() {
        return reportID;
    }

    public Integer getState() {
        return state;
    }
    public void sendSavedMessage(){
        player.sendMessage(Component.text("Zapisano!", NamedTextColor.GREEN));
    }
    public void sendReportCreated(Integer id){
        player.sendMessage(Component.text("Wysłano report!\n Otrzymał on id: "+id, NamedTextColor.GREEN));
    }
    public void sendReportDeleted(){
        player.sendMessage(Component.text("Usunięto report!", NamedTextColor.GREEN));
    }
    public void sendDescriptionMessage(){
        Component message = Component.text("Aby dodać opis do swojego zgłoszenia napisz wiadomość na czacie. Aby to pominąć ")
                .append(
                        Component.text("kliknij tutaj!")
                        .style(Style.style(TextDecoration.UNDERLINED))
                        .clickEvent(ClickEvent.runCommand("/reportcontinue"))
                );
        player.sendMessage(message);
    }
    public void sendSummary(ReportCreator report){
        Component firstline = Component.text("Oto podsumowanie twojego zgłoszenia: ",NamedTextColor.GREEN);
        String secoundLine = "Reportujesz ";
        switch (report.getType()){
            case User:
                Player reported = plugin.getServer().getPlayer(UUID.fromString(report.getReportedElementID()));
                secoundLine+="użytkownika "+reported.getName();
                break;
            case Message:
                ReportMessage reportMessage = plugin.getDatabaseManager().getMessage(Integer.valueOf(report.getReportedElementID()));
                secoundLine+="wiadomość użytkownika "+ reportMessage.player.getName()+" o treści: "+reportMessage.message;
                break;

        }
        String thirdLine = "Z powodu: "+ ReportTranslater.fromReportShortDescription(report.getReportShortDescription())+"\n"+report.getDescription();
        player.sendMessage(firstline);
        player.sendMessage(Component.text(secoundLine,NamedTextColor.GREEN));
        player.sendMessage(Component.text(thirdLine,NamedTextColor.GREEN));
        Component delete = Component.text("[Usuń zgłoszenie]",NamedTextColor.RED)
                .clickEvent(ClickEvent.runCommand("/reportcontinue delete"));
        Component save = Component.text("  [Wyślij zgłoszenie]",NamedTextColor.GREEN)
                .clickEvent(ClickEvent.runCommand("/reportcontinue"));
        player.sendMessage(Component.empty().append(delete).append(save));
    }
    public void createReport(ReportCreator report){
        Integer reportid = report.createReport();
        if(reportid!=null)
            this.sendReportCreated(reportid);
        else
            player.sendMessage(Component.text("Coś poszło nie tak spróbuj ponownie",NamedTextColor.DARK_RED));
        this.clearReport();
    }

    public Player getPlayer() {
        return player;
    }
}
