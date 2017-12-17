package com.teambeez.listeners;

import com.teambeez.PogeBot;
import com.teambeez.containers.CommandData;
import com.teambeez.music.GuildManager;
import com.teambeez.parsers.CommandParser;
import com.teambeez.parsers.ParseException;
import com.teambeez.plugins.PluginManager;
import com.teambeez.plugins.PluginNotFoundException;
import com.teambeez.plugins.defaults.Music;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    private static PluginManager pluginManager;

    /**
     * Used to detect when JDA-API successfully connects to Discord
     * @param event The Ready-Event
     */
    @Override
    public void onReady(ReadyEvent event) {
        /* TODO: Load Settings */

        /* Load Plugins */
        pluginManager = new PluginManager();
        pluginManager.loadPlugins();

        /* Logging Completion */
        //TODO PogeBot.LOG.info("PogeBot-API Initialized");
    }

    /**
     * Used to detect when a message is received. If the message
     * starts with the require prefix, it will attempt to run a command.
     * @param event The Message-Event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        /* Ignore Bot Commands */
        if(event.getAuthor().isBot()) return;

        try {
            /* Parse Received Message */
            CommandData data = CommandParser.parseCommand(event);

            /* Invoke Commands */
            this.pluginManager.invokePlugin(data.getCommand(), data);
            MessageHandler.deleteMessage(event.getMessage(), event.getTextChannel());

        } catch (PluginNotFoundException | ParseException ignore) { }
    }

    /**
     * Used to detect when the bot is shutting down, to safely clear
     * all resources and save required files.
     */
    @Override
    public void onShutdown(ShutdownEvent event) {
        /* TODO: Save Settings */
        /* TODO: Shutdown Plugins */
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        VoiceChannel channel = event.getChannelLeft();
        if(channel.getMembers().size() == 1 && channel.getMembers().get(0).getUser().equals(event.getJDA().getSelfUser())) {
            Music.instance.stopAll(event.getGuild());
        }
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }
}
