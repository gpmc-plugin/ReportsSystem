package me.gpmcplugins.reportssystem.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class IconItemstack {
    public static TextColor itemColor = NamedTextColor.RED;

    public static ItemStack bugIconItemStack = ChestGUI.setItemStackName(Component.text("BUG",itemColor,TextDecoration.BOLD),new ItemStack(Material.CREEPER_SPAWN_EGG));
    public static ItemStack anotherPlayerIconItemStack = ChestGUI.setItemStackName(Component.text("INNY GRACZ", itemColor, TextDecoration.BOLD), new ItemStack(Material.PLAYER_HEAD));
    public static ItemStack otherIconItemStack = ChestGUI.setItemStackName(Component.text("INNE", itemColor, TextDecoration.BOLD), new ItemStack(Material.WRITABLE_BOOK));
    public static ItemStack cheatingOrExploiningIconItemStack = ChestGUI.setItemStackName(Component.text("UŻYWANIE CHEATÓW LUB EXPLOITÓW", itemColor, TextDecoration.BOLD), new ItemStack(Material.DIAMOND_SWORD));
    public static ItemStack fraudIconItemStack = ChestGUI.setItemStackName(Component.text("OSZUSTWO", itemColor, TextDecoration.BOLD), new ItemStack(Material.NETHERITE_SCRAP));
    public static ItemStack swearingIconItemStack = ChestGUI.setItemStackName(Component.text("PRZEKLINANIE", itemColor, TextDecoration.BOLD), new ItemStack(Material.BARRIER));
    public static ItemStack speakingWrongIconItemStack = ChestGUI.setItemStackName(Component.text("MOWA NIENAWISCI", itemColor, TextDecoration.BOLD), new ItemStack(Material.NETHERITE_HOE));
    public static ItemStack offendingIconItemStack = ChestGUI.setItemStackName(Component.text("OBRAZANIE INNYCH UZYTKOWNIKOW", itemColor, TextDecoration.BOLD), new ItemStack(Material.WITHER_ROSE));
    public static ItemStack claimNewReportIconItemStack = ChestGUI.setItemStackName(Component.text("PRZEJMIJ NOWY REPORT", itemColor, TextDecoration.BOLD), new ItemStack(Material.REDSTONE_TORCH));
    public static ItemStack continueReportIconItemStack = ChestGUI.setItemStackName(Component.text("KONTYNUUJ PRZEJETY REPORT", itemColor, TextDecoration.BOLD), new ItemStack(Material.CHEST));
}
