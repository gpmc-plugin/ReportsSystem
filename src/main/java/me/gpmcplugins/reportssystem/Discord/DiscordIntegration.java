package me.gpmcplugins.reportssystem.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class DiscordIntegration {
    private static DiscordIntegration instance;
    public boolean isBuilt;
    JDABuilder builder;
    JDA jda;

    public DiscordIntegration(String token) throws LoginException {
        if (instance != null)
            throw new RuntimeException();

        instance = this;

        builder = JDABuilder.createDefault(token);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.playing("GPMC"));
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.setLargeThreshold(50);
        builder.addEventListeners(new Ping());
        this.build();
        jda.upsertCommand("ping", "Calculate ping of the bot").queue();
    }

    public void build() throws LoginException {
        jda = builder.build();
        isBuilt = true;
    }

    public static DiscordIntegration getInstance() {
        return instance;
    }
}
