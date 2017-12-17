package com.teambeez.parsers;

import com.teambeez.containers.CommandData;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private static final String prefix = "!";

    public static CommandData parseCommand(MessageReceivedEvent event) throws ParseException {
        /* Create variable to Modify */
        String content = event.getMessage().getContent();

        /* Check if the message is a Command */
        if(!content.startsWith(prefix)) throw new ParseException();
        content = content.replaceFirst(prefix, "");

        /* Split and Return */
        List<String> split = Arrays.asList(content.split(" "));
        return new CommandData(split.get(0).toLowerCase(), split, event);
    }
}
