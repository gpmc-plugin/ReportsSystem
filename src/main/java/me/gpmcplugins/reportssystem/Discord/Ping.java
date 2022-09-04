package me.gpmcplugins.reportssystem.Discord;

import me.gpmcplugins.reportssystem.reportssystem.ReportsSystem;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionType;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public class Ping extends ListenerAdapter {
    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        if (event.getName().equals("ping"))
        {
            long time = System.currentTimeMillis();
            ReplyCallbackAction e = event.reply("Pong!").setEphemeral(true);
            e.flatMap(v -> event.getHook().editOriginalFormat("Pong: -%d ms", System.currentTimeMillis() - time));
            e.addActionRow(Button.primary("hello", "Hello!"), Button.success("retry", "Retry"));
            e.queue();
        }
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("hello")) {
            event.reply("Hi :)").queue();
        } else if (event.getComponentId().equals("retry")) {
            long time = System.currentTimeMillis();
            ReplyCallbackAction e = event.reply("Pong!").setEphemeral(true);
            e.flatMap(v -> event.getHook().editOriginalFormat("Pong: -%d ms", System.currentTimeMillis() - time));
            e.addActionRow(Button.primary("hello", "Hello!"), Button.success("retry", "retry"));
            e.queue();
        }
    }
}
