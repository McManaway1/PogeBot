package com.teambeez.listeners;

import com.teambeez.containers.CommandData;
import com.teambeez.parsers.CommandParser;
import com.teambeez.parsers.ParseException;
import com.teambeez.packs.PackHandler;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    private PackHandler packHandler;

    /**
     * Used to detect when JDA-API successfully connects to Discord
     * @param event The Ready-Event
     */
    @Override
    public void onReady(ReadyEvent event) {
        /* TODO: Load Settings */

        /* Load Plugins */
        this.packHandler = new PackHandler();
        this.packHandler.startPacks();
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
            this.packHandler.invokePacks(data);
            MessageHandler.deleteMessage(event.getMessage(), event.getTextChannel());

        } catch (ParseException ignore) { }
    }

    /**
     * Used to detect when the bot is shutting down, to safely clear
     * all resources and save required files.
     */
    @Override
    public void onShutdown(ShutdownEvent event) {
        /* TODO: Save Settings */
        /* Shutdown Plugins */
        this.packHandler.clearPacks();
    }
}
