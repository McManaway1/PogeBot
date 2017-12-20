package com.teambeez.packs;

import com.teambeez.containers.CommandData;
import net.dv8tion.jda.core.events.Event;

public interface IPack {
    void initialize();
    void invoke(CommandData data);
    void onEvent(Event event);
    void close();
}
