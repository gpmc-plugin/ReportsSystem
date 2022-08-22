package me.gpmcplugins.reportssystem.Managers;

import me.gpmcplugins.reportssystem.Update.UpdatePopupThread;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Server;
import org.json.*;

import java.io.File;

public class UpdateManager {
    private static ReportsSystem plugin;
    public static boolean isUpdated;
    public UpdatePopupThread managerThread;

    public UpdateManager(ReportsSystem plugin)
    {
        UpdateManager.plugin = plugin;
        managerThread = new UpdatePopupThread();
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
        Server server = plugin.getServer();
        String content = NetworkManager.get("https://api.github.com/repos/gpmc-plugin/ReportsSystem/releases");
        JSONArray json = new JSONArray(content);
        JSONObject properObject;
        properObject = null;
        int properObjectId = -1;
        for (int i = 0; i < json.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            int id = obj.getInt("id");
            if(id > properObjectId)
            {
                properObjectId = id;
                properObject = obj;
            }
        }
        assert properObject != null;
        server.getConsoleSender().sendMessage(properObject.getString("name"));

        //Remove Old Plugin
        String[] pluginList = server.getPluginsFolder().list();
        server.getPluginManager().disablePlugin(plugin);
        assert pluginList != null;
        for(String s : pluginList)
        {
            File file = new File(s);
            if(!file.isFile())
                continue;
            if(file.getName().toLowerCase().contains("reportssystem"))
            {
                server.getConsoleSender().sendMessage(file.getName());
                server.getConsoleSender().sendMessage(String.valueOf(file.delete()));
            }
        }
        return false;
    }
}
