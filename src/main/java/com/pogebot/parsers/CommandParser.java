package com.pogebot.parsers;

import com.pogebot.parsers.containers.CommandData;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandParser {
    private static final String prefix = "!";

    public static CommandData parseCommand(MessageReceivedEvent event) throws ParseException {
        /* Create variable to Modify */
        String content = event.getMessage().getContentDisplay();

        /* Check if the message is a Command */
        if (!content.startsWith(prefix)) throw new ParseException();
        content = content.replaceFirst(prefix, "");

        /* Split and Return */
        String[] split = content.split(" ", 2);
        return new CommandData(split[0].toLowerCase(), split.length > 1 ? split[1] : "", event);
    }
}
