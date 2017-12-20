package com.teambeez.parsers.containers;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A wrapper object to simplify data contained in the 'MessageReceivedEvent', this object is created
 * whenever a user submits a command to a channel the bot is listening to.
 */
public class CommandData {
    private final String command, arguments;
    private final MessageReceivedEvent event;

    public CommandData(String command, String arguments, MessageReceivedEvent event) {
        this.command = command;
        this.arguments = arguments;
        this.event = event;
    }

    /**
     * @return The command value which the user entered.
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return A string of all arguments after the command.
     */
    public String getArguments() {
        return arguments;
    }


    /**
     * @return A string array of all arguments after the command.
     */
    public String[] getArgumentsList() {
        return arguments.split(" ");
    }

    /**
     * @return The Guild the command was sent from.
     */
    public Guild getGuild() {
        return event.getGuild();
    }

    /**
     * @return The Member which sent the Command.
     */
    public Member getMember() {
        return event.getMember();
    }

    /**
     * @return The TextChannel the command was sent from.
     */
    public TextChannel getChannel() {
        return event.getMessage().getTextChannel();
    }

    /**
     * @return The first person mentioned in the arguments of the command. If none, will return null
     */
    @Nullable
    public User getFirstMention() {
        List<User> mentions = event.getMessage().getMentionedUsers();
        if (mentions.isEmpty()) return null;
        return mentions.get(0);
    }
}
