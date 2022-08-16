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

public class ChestReportTypeInterface {
    public static TextColor itemColor = NamedTextColor.RED;

    public static ItemStack bugIconItemStack = ChestGUI.setItemStackName(
            Component.text("BUG",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.CREEPER_SPAWN_EGG));
    public static ItemStack anotherPlayerIconItemStack = ChestGUI.setItemStackName(
            Component.text("INNY GRACZ",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.PLAYER_HEAD));
    public static ItemStack otherIconItemStack = ChestGUI.setItemStackName(
            Component.text("INNE",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.WRITABLE_BOOK));

    public static ItemStack cheatingOrExploiningIconItemStack = ChestGUI.setItemStackName(
            Component.text("UŻYWANIE CHEATÓW LUB EXPLOITÓW",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.DIAMOND_SWORD));

    public static ItemStack fraudIconItemStack = ChestGUI.setItemStackName(
            Component.text("OSZUSTWO",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.NETHERITE_SCRAP));

    public static ItemStack swearingIconItemStack = ChestGUI.setItemStackName(
            Component.text("PRZEKLINANIE",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.BARRIER));

    public static ItemStack speakingWrongIconItemStack = ChestGUI.setItemStackName(
            Component.text("MOWA NIENAWISCI",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.NETHERITE_HOE));

    public static ItemStack offendingIconItemStack = ChestGUI.setItemStackName(
            Component.text("OBRAZANIE INNYCH UZYTKOWNIKOW",
                    itemColor,
                    TextDecoration.BOLD),
            new ItemStack(Material.WITHER_ROSE));

    public static void deathReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle((TextComponent) MiniMessage.miniMessage().deserialize("<gradient:#f857a6:#ff5858>Wybierz powod reportu smierci</gradient>"))
                .setItem(11, bugIconItemStack, "reportcontinue 0")
                .setItem(13, anotherPlayerIconItemStack, "reportcontinue 1")
                .setItem(15, otherIconItemStack, "reportcontinue 8")
                .showGUI(p);
    }

    public static void userReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle((TextComponent) MiniMessage.miniMessage().deserialize("<gradient:#f857a6:#ff5858>Powod reportu gracza</gradient>"))
                .setItem(11, cheatingOrExploiningIconItemStack, "reportcontinue 2")
                .setItem(13, fraudIconItemStack, "reportcontinue 3")
                .setItem(15, otherIconItemStack, "reportcontinue 8")
                .showGUI(p);
    }

    public static void messageReportInterface(Player p){
        (new ChestGUI(27))
                .setTitle((TextComponent) MiniMessage.miniMessage().deserialize("<gradient:#f857a6:#ff5858>Wybierz powod reportu wiadomosci</gradient>"))
                .setItem(11, swearingIconItemStack, "reportcontinue 4")
                .setItem(12, fraudIconItemStack, "reportcontinue 5")
                .setItem(13, speakingWrongIconItemStack, "/reportcontinue 6")
                .setItem(14, offendingIconItemStack, "reportcontinue 7")
                .setItem(15, otherIconItemStack, "reportcontinue 8")
                .showGUI(p);
    }
}
