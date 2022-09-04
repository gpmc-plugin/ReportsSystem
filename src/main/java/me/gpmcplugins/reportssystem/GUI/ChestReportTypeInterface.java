package me.gpmcplugins.reportssystem.GUI;

import org.bukkit.entity.Player;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.*;

public class ChestReportTypeInterface {
    public static void deathReportInterface(Player p) {
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz powod reportu smierci</gradient>")
                .setItem(11, bugIconItemStack, "report-continue 0", false)
                .setItem(13, anotherPlayerIconItemStack, "report-continue 1", false)
                .setItem(15, otherIconItemStack, "report-continue 8", false)
                .showGUI(p);
    }

    public static void userReportInterface(Player p) {
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Powod reportu gracza</gradient>")
                .setItem(11, cheatingOrExploiningIconItemStack, "report-continue 2", false)
                .setItem(13, fraudIconItemStack, "report-continue 3", false)
                .setItem(15, otherIconItemStack, "report-continue 8", false)
                .showGUI(p);
    }

    public static void messageReportInterface(Player p) {
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz powod reportu wiadomosci</gradient>")
                .setItem(11, swearingIconItemStack, "report-continue 4", false)
                .setItem(12, fraudIconItemStack, "report-continue 5", false)
                .setItem(13, speakingWrongIconItemStack, "report-continue 6", false)
                .setItem(14, offendingIconItemStack, "report-continue 7", false)
                .setItem(15, otherIconItemStack, "report-continue 8", false)
                .showGUI(p);
    }
}
