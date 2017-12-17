package com.teambeez.plugins;

public abstract class AbstractPlugin implements DiscordPlugin {
    protected String name;
    protected String description;
    protected CommandType type;

    protected AbstractPlugin(String name, String description, CommandType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CommandType getType() {
        return type;
    }
}
