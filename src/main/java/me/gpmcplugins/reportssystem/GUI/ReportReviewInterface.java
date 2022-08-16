package me.gpmcplugins.reportssystem.GUI;

import org.bukkit.entity.Player;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.*;

public class ReportReviewInterface {
    public static void MainReviewMenu(Player p)
    {
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz rodzaj akcji ktora chcesz wykonac</gradient>")
                .setItem(11, claimNewReportIconItemStack, "report-review claimnewreportgui")
                .setItem(15, claimNewReportIconItemStack, "report-review continueclaimedreportgui")
                .showGUI(p);
    }

    public static void ClaimNewReportMenu(Player p) {
        p.sendMessage("szylazcmd");
        ChestGUI gui = new ChestGUI(27).setTitle("<gradient:#f857a6:#ff5858>Wybierz rodzaj akcji ktora chcesz wykonac</gradient>");

        gui.showGUI(p);
    }
}
