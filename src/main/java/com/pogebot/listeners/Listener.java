package com.pogebot.listeners;

import com.pogebot.messages.MessageHandler;
import com.pogebot.packs.PackHandler;
import com.pogebot.parsers.CommandParser;
import com.pogebot.parsers.ParseException;
import com.pogebot.parsers.containers.CommandData;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
    private PackHandler packHandler;

    /**
     * When the Connection to Discord Servers is Successful, the ReadyEvent will
     * fire. This method catches the Event and uses it to load and initialize all Packs
     * in the 'packs' folder into the Application.
     *
     * @param event ReadyEvent
     */
    @Override
    public void onReady(ReadyEvent event) {
        /* Load Plugins */
        this.packHandler = new PackHandler();
        this.packHandler.startPacks();
    }

    /**
     * Whenever any event occurs from the Discord, all Pack's will be alerted of the
     * Event that occurred.
     *
     * @param event Any Received Event
     */
    @Override
    public void onGenericEvent(Event event) {
        if (this.packHandler == null) return;
        this.packHandler.alertPacks(event);
    }

    /**
     * Whenever a message occurs in chat, the API will attempt to check if
     * event is a Command. If it was a command the API will then alert all Pack's
     * about the Command processed.
     *
     * @param event Message Received Event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        /* Ignore Bot Commands */
        if (event.getAuthor().isBot()) return;

        try {
            /* Parse Received Message */
            CommandData data = CommandParser.parseCommand(event);

            /* Invoke Commands if they exist */
            if (packHandler.commandExists(data.getCommand())) {
                MessageHandler.deleteMessage("Command Request", event.getMessage());
                packHandler.invokePacks(data);
            }

        } catch (ParseException ignore) {
        }
    }

    /**
     * Used to detect when the bot is shutting down, to safely clear
     * all resources and save required files.
     */
    @Override
    public void onShutdown(ShutdownEvent event) {
        /* Shutdown Plugins */
        this.packHandler.clearPacks();
    }
}
