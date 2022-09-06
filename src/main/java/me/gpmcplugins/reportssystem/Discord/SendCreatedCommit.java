package me.gpmcplugins.reportssystem.Discord;

import me.gpmcplugins.reportssystem.objects.ReportObject;
import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;

import java.util.Objects;

public class SendCreatedCommit {

    public static void sendReportToDiscord(ReportObject reportObject)
    {
        Objects.requireNonNull(DiscordIntegration.getInstance().jda.getTextChannelById(
                Objects.requireNonNull(ReportsSystem.getInstance().getConfigManager().getConfig().getString("ReportLogChannelID")))).sendMessage("test");
    }
}
