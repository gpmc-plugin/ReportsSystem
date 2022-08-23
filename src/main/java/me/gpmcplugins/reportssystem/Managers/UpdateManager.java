package me.gpmcplugins.reportssystem.Managers;

import com.rylinaux.plugman.util.PluginUtil;
import me.gpmcplugins.reportssystem.Update.UpdatePopupThread;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.json.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;

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
                                NamedTextColor.YELLOW).clickEvent(ClickEvent.runCommand("/rsu"))), "reportsystem.report");
        }
    }

    public void update() throws IOException {
        Server server = plugin.getServer();
        PluginLoader pluginLoader = plugin.getPluginLoader();
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
        File pluginFile = plugin.getPluginFile();

        //Remove Old Plugin
        PluginUtil.unload(ReportsSystem.getInstance());
        boolean success = pluginFile.delete();
        if(success)
            server.getConsoleSender().sendMessage("udało się");
        else {
            server.getConsoleSender().sendMessage("nie udało się :(");
            try {
                pluginLoader.loadPlugin(pluginFile);
            } catch (InvalidPluginException e) {
                throw new RuntimeException(e);
            }

        }

        JSONObject obj = properObject.getJSONArray("assets").getJSONObject(0);

        File file = Paths.get(pluginFile.getParentFile().getAbsolutePath(), obj.getString("name")).toFile();
        //some stackoverflow code
        URL url = new URL(obj.getString("browser_download_url"));
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();

        String filename = file.getName().replace(".jar", "");
        server.getConsoleSender().sendMessage(file.getName());
        PluginUtil.load(filename);
        PluginUtil.enable(PluginUtil.getPluginByName(filename));
        PluginUtil.reload(PluginUtil.getPluginByName(filename));
        //try {
        /*} catch (InvalidPluginException e) {
            server.getConsoleSender().sendMessage(e.getMessage());
            throw new RuntimeException(e);
        }*/
        /*for (int i = 0; i < array.length(); i++) {
            JSONObject obj = json.getJSONObject(i);
            if(obj == null)
                continue;
            //try {
                server.getConsoleSender().sendMessage(String.valueOf(obj));
                if(Objects.equals(obj.getString("content_type"), "application/java-archive"))
                {
                    File file = Paths.get(pluginFile.getParentFile().getAbsolutePath(), obj.getString("name")).toFile();
                    //some stackoverflow code
                    URL url = new URL(obj.getString("browser_download_url"));
                    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    fos.close();
                    rbc.close();
                    /*try {
                        pluginLoader.loadPlugin(file);
                    } catch (InvalidPluginException e) {
                        throw new RuntimeException(e);
                    }
                }
            /*} catch (NullPointerException ignore)
            {

            }
        }*/
    }
}
