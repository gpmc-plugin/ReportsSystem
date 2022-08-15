package me.gpmcplugins.reportssystem.GUI;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public class BookReportTypeInterface {
    public static ItemStack deathReportInterface(){
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle("Nowy Report");
        bookMeta.setAuthor("Reports System");
        Component page = Component.empty();
        Component information = Component.text("Kliknij odpowiednie: \n");
        Component bug = Component.text("\nBUG\n", NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 0"));
        Component otherPlayer=Component.text("\nINNY GRACZ\n", NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 1"));
        Component other=Component.text("\nINNE\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 8"));
        page=page.append(information).append(bug).append(otherPlayer).append(other);
        bookMeta.addPages(page);
        itemStack.setItemMeta(bookMeta);
        return itemStack;
    }
    public static ItemStack userReportInterface(){
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle("Nowy Report");
        bookMeta.setAuthor("Reports System");
        Component page = Component.empty();
        Component information = Component.text("Kliknij odpowiednie: \n");
        Component one = Component.text("\nUŻYWANIE CHEATÓW LUB EXPLOITÓW\n", NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 2"));
        Component two=Component.text("\nOSZUSTWO\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 3"));
        Component other=Component.text("\nINNE\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 8"));
        page=page.append(information).append(one).append(two).append(other);
        bookMeta.addPages(page);
        itemStack.setItemMeta(bookMeta);
        return itemStack;
    }
    public static ItemStack messageReportInterface(){
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle("Nowy Report");
        bookMeta.setAuthor("Reports System");
        Component page = Component.empty();
        Component information = Component.text("Kliknij odpowiednie: \n");
        Component one = Component.text("\nPRZEKLINIANIE\n", NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 4"));
        Component two=Component.text("\nOSZUSTWO\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 5"));
        Component three=Component.text("\nMOWA NIENAWIŚCI\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 6"));
        Component four=Component.text("\nOBRAŻANIE INNYCH UŻYTKOWNIKÓW\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 7"));
        Component other=Component.text("\nINNE\n",NamedTextColor.DARK_PURPLE, TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/reportcontinue 8"));

        page=page.append(information).append(one).append(two).append(three).append(four).append(other);
        bookMeta.addPages(page);
        itemStack.setItemMeta(bookMeta);
        return itemStack;
    }
}
