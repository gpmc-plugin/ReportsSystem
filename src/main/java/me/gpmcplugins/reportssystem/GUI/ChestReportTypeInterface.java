package me.gpmcplugins.reportssystem.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.w3c.dom.Text;

import static me.gpmcplugins.reportssystem.GUI.IconItemstack.*;

public class ChestReportTypeInterface {
    public static void deathReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz powod reportu smierci</gradient>")
                .setItem(11, bugIconItemStack, "reportcontinue 0")
                .setItem(13, anotherPlayerIconItemStack, "reportcontinue 1")
                .setItem(15, otherIconItemStack, "reportcontinue 8")
                .showGUI(p);
    }

    public static void userReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Powod reportu gracza</gradient>")
                .setItem(11, cheatingOrExploiningIconItemStack, "reportcontinue 2")
                .setItem(13, fraudIconItemStack, "reportcontinue 3")
                .setItem(15, otherIconItemStack, "reportcontinue 8")
                .showGUI(p);
    }

    public static void messageReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle("<gradient:#f857a6:#ff5858>Wybierz powod reportu wiadomosci</gradient>")
                .setItem(11, swearingIconItemStack, "reportcontinue 4")
                .setItem(12, fraudIconItemStack, "reportcontinue 5")
                .setItem(13, speakingWrongIconItemStack, "/reportcontinue 6")
                .setItem(14, offendingIconItemStack, "reportcontinue 7")
                .setItem(15, otherIconItemStack, "reportcontinue 8")
                .showGUI(p);
    }
}
