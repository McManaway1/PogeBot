package com.teambeez.packs.pack;

import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.events.Event;

public interface IPack {

    /**
     * Whenever the Pack is loaded into the API, this method is called to alert each pack
     * to perform all it's initial tasks (i.e Load Settings).
     */
    void initialize();

    /**
     * Whenever a Message is successfully Parsed as a Command, the CommandData Object storing all relevant data
     * is passed to all packs to be used here.
     *
     * @param data User Command Data
     */
    void invoke(CommandData data);

    /**
     * ADVANCED EVENTS:
     * Only required if advanced events are Required (i.e User Logs in).
     *
     * @param event Advanced Events (Needs to be Cast by Packs).
     */
    void onEvent(Event event);

    /**
     * Whenever the API shuts down or requests for Packs to be reloaded this method is called,
     * allows for each pack to perform it's shutdown procedures (i.e Save Settings).
     */
    void close();
}
