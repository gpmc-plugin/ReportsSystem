package me.gpmcplugins.reportssystem.GUI;

import org.bukkit.entity.Player;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.*;

public class ChestReportTypeInterface {
    public static void deathReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz powod reportu smierci</gradient>")
                .setItem(11, bugIconItemStack, "report-continue 0")
                .setItem(13, anotherPlayerIconItemStack, "report-continue 1")
                .setItem(15, otherIconItemStack, "report-continue 8")
                .showGUI(p);
    }

    public static void userReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Powod reportu gracza</gradient>")
                .setItem(11, cheatingOrExploiningIconItemStack, "report-continue 2")
                .setItem(13, fraudIconItemStack, "report-continue 3")
                .setItem(15, otherIconItemStack, "report-continue 8")
                .showGUI(p);
    }

    public static void messageReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz powod reportu wiadomosci</gradient>")
                .setItem(11, swearingIconItemStack, "report-continue 4")
                .setItem(12, fraudIconItemStack, "report-continue 5")
                .setItem(13, speakingWrongIconItemStack, "report-continue 6")
                .setItem(14, offendingIconItemStack, "report-continue 7")
                .setItem(15, otherIconItemStack, "report-continue 8")
                .showGUI(p);
    }
}
