package me.gpmcplugins.reportssystem.Managers;

import com.google.gson.JsonObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.json.*;
import org.json.simple.JSONObject;

public class UpdateManager {
    private static ReportsSystem plugin;
    public static boolean isUpdated;
    public UpdateManagerThread managerThread;

    public UpdateManager(ReportsSystem plugin)
    {
        UpdateManager.plugin = plugin;
        managerThread = new UpdateManagerThread();
        managerThread.start();
    }

    public static void UpdateUpdatedState()
    {
        plugin.getServer().getConsoleSender().sendMessage("----------");
        plugin.getServer().getConsoleSender().sendMessage("Checking for updates for ReportsSystem...");
        String content = NetworkManager.get("https://raw.githubusercontent.com/gpmc-plugin/ReportsSystem/main/VERSION");
        assert content != null;
        UpdateManager.isUpdated = UpdateManager.plugin.getDescription().getVersion().equals(content);
        if(UpdateManager.isUpdated)
            plugin.getServer().getConsoleSender().sendMessage("Not Found Any Updates");
        else
            plugin.getServer().getConsoleSender().sendMessage("Found Updates");
        if(!UpdateManager.isUpdated)
        {
            UpdateManager.plugin.getServer().broadcast(
                    Component.text("Wlasnie wyszedl nowy update do pluginu Reports System!\n" +
                            "Updaty tego plugina maja w sobie poprawki zwiazane z bezpieczenstwem oraz bledami wiec zainstaluj je szybko!\n",
                            NamedTextColor.AQUA)
                        .append(Component.text("[Kliknij tutaj aby zupdatowac plugin]",
                                NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/ReportsSystem-update"))), "reportsystem.report");
        }
    }

    public boolean update()
    {
        String content = NetworkManager.get("https://api.github.com/repos/gpmc-plugin/ReportsSystem/releases");
        JSONObject msg = new JSONObject(content);
        return false;
    }
}
