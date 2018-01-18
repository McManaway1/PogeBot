package com.teambeez.parsers.containers;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.collections4.list.UnmodifiableList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A wrapper object to simplify data contained in the 'MessageReceivedEvent', this object is created
 * whenever a user submits a command to a channel the bot is listening to.
 */
public class CommandData {
    private final String command, arguments;
    private final Guild guild;
    private final Member member;
    private final TextChannel channel;
    private final List<User> mentions;

    public CommandData(String command, String arguments, MessageReceivedEvent event) {
        this.command = command;
        this.arguments = arguments;
        this.guild = event.getGuild();
        this.member = event.getMember();
        this.channel = event.getTextChannel();
        this.mentions = event.getMessage().getMentionedUsers();
    }

    public CommandData(String command, String arguments, Guild guild, Member member, TextChannel channel) {
        this(command, arguments, guild, member, channel, new ArrayList<>());
    }

    public CommandData(String command, String arguments, Guild guild, Member member, TextChannel channel, List<User> mentions) {
        this.command = command;
        this.arguments = arguments;
        this.guild = guild;
        this.member = member;
        this.channel = channel;
        this.mentions = mentions;
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
        return guild;
    }

    /**
     * @return The Member which sent the Command.
     */
    public Member getMember() {
        return member;
    }

    /**
     * @return The TextChannel the command was sent from.
     */
    public TextChannel getChannel() {
        return channel;
    }

    /**
     * @return The first person mentioned in the arguments of the command. If none, will return null
     */
    @Nullable
    public User getFirstMention() {
        if (mentions.isEmpty()) return null;
        return mentions.get(0);
    }

    /**
     * @return An unmodifiable List of all mentioned Users in a Command
     */
    public List<User> getMentions() {
        return Collections.unmodifiableList(new ArrayList<>(mentions));
    }
}
