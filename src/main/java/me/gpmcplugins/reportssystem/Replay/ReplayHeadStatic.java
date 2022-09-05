package me.gpmcplugins.reportssystem.Replay;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ReplayHeadStatic {
    public final static NamespacedKey actionNamespace = new NamespacedKey("reportssystemreply", "replyaction");

    public static ItemStack minusHead() {
        ItemStack head = createCustomSkull(1, Component.text("Zmniejsz prędkość"), new ArrayList<>(), "bfb7f558b6f67e8725326a69a85da26ae5b27c4473d25d5e9caac576b4d96433");
        ItemMeta itemMeta = head.getItemMeta();
        itemMeta.getPersistentDataContainer().set(actionNamespace, PersistentDataType.INTEGER, 4);
        head.setItemMeta(itemMeta);
        return head;
    }

    public static ItemStack plusHead() {
        ItemStack head = createCustomSkull(1, Component.text("Zwiększ prędkość"), new ArrayList<>(), "9aca891a7015cbba06e61c600861069fa7870dcf9b35b4fe15850f4b25b3ce0");
        ItemMeta itemMeta = head.getItemMeta();
        itemMeta.getPersistentDataContainer().set(actionNamespace, PersistentDataType.INTEGER, 3);
        head.setItemMeta(itemMeta);
        return head;


    }

    public static ItemStack rightHead() {
        ItemStack head = createCustomSkull(1, Component.text("Przesuń w prawo"), new ArrayList<>(), "8aa187fede88de002cbd930575eb7ba48d3b1a06d961bdc535800750af764926");
        ItemMeta itemMeta = head.getItemMeta();
        itemMeta.getPersistentDataContainer().set(actionNamespace, PersistentDataType.INTEGER, 2);
        head.setItemMeta(itemMeta);
        return head;
    }

    public static ItemStack leftHead() {
        return createCustomSkull(1, Component.text("Przesuń w lewo"), new ArrayList<>(), "f6dab7271f4ff04d5440219067a109b5c0c1d1e01ec602c0020476f7eb612180");

    }

    public static ItemStack runButton() {
        ItemStack itemStack = new ItemStack(Material.LIME_DYE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text("Kliknij aby rozpocząć odtwarzanie"));

        itemMeta.getPersistentDataContainer().set(actionNamespace, PersistentDataType.INTEGER, 0);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack resumeButton() {
        ItemStack itemStack = new ItemStack(Material.MAGENTA_DYE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text("Kliknij aby wznowić odtwarzanie"));
        itemMeta.getPersistentDataContainer().set(actionNamespace, PersistentDataType.INTEGER, 0);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack stopButton() {
        ItemStack itemStack = new ItemStack(Material.GRAY_DYE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text("Kliknij aby zatrzymać odtwarzanie"));
        itemMeta.getPersistentDataContainer().set(actionNamespace, PersistentDataType.INTEGER, 1);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack replayButton() {
        ItemStack itemStack = runButton();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text("Aby odtworzyć ponownie naciśnij ten guzik"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createCustomSkull(int amount, Component displayName, List<Component> lore, String texture) {
        texture = "http://textures.minecraft.net/texture/" + texture;

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.displayName(displayName);
        skullMeta.lore(lore);

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
        profile.getProperties().add(new ProfileProperty("textures", new String(encodedData)));
        skullMeta.setPlayerProfile(profile);
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
