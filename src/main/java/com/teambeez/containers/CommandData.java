package com.teambeez.containers;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.List;

public class CommandData {
    private final String command;
    private final List<String> arguments;
    private final MessageReceivedEvent event;

    public CommandData(String command, List<String> arguments, MessageReceivedEvent event) {
        this.command = command;
        this.arguments = arguments;
        this.event = event;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Member getMember() { return event.getMember(); }

    public TextChannel getChannel()
    {
        return event.getMessage().getTextChannel();
    }

    public PrivateChannel getPrivateChannel()
    {
        return event.getMessage().getPrivateChannel();
    }

    @Nullable
    public User getFirstMention() {
        List<User> mentions = event.getMessage().getMentionedUsers();
        if(mentions.isEmpty()) return null;
        return mentions.get(0);
    }
}
