package com.teambeez.plugins;

import com.teambeez.containers.CommandData;

public interface DiscordPlugin {
    String getName();
    String getDescription();
    CommandType getType();
    void invoke(CommandData data);
}
