package com.teambeez.packs.pack;

import net.dv8tion.jda.core.events.Event;

public abstract class AbstractPack implements IPack {
    @Override
    public void initialize() {
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    public void close() {
    }
}
