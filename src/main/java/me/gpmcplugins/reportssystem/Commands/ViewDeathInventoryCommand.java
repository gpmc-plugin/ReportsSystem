package me.gpmcplugins.reportssystem.Commands;

import me.gpmcplugins.reportssystem.GUI.ViewInventoryGui;
import me.gpmcplugins.reportssystem.objects.ReportDeath;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ViewDeathInventoryCommand implements CommandExecutor {
    private ReportsSystem plugin;
    public ViewDeathInventoryCommand(ReportsSystem plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length>0){
            if(sender instanceof Player){
                Player player = (Player) sender;
                try {
                    ReportDeath death = plugin.getDatabaseManager().getDeath(Integer.valueOf(args[0]));
                    if(death==null){
                        sender.sendMessage("podane id śmierci nie istnieje");
                        return true;
                    }
                    if(death.noob.getUniqueId().toString()==player.getUniqueId().toString()){
                        new ViewInventoryGui(plugin,Integer.parseInt(args[0])).openInventory(player);
                    }
                    else
                        sender.sendMessage("to nie ty wtedy umarłeś");
                } catch (SQLException e) {
                    plugin.getDatabaseManager().throwError(e.getMessage());
                }
            }
        }
        else
            sender.sendMessage("za mało argumentów");
        return false;
    }
}
