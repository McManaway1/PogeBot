package com.teambeez.packs.pack.essentials;

import com.teambeez.messages.EmbedCreator;
import com.teambeez.messages.MessageHandler;
import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Purge {
    public static void invoke(CommandData data) {
        MessageHistory history = data.getChannel().getHistory();
        Consumer<List<Message>> consumer = (m) -> scanMessages(m, data.getChannel());
        history.retrievePast(100).queue(consumer);

        EmbedCreator.create(data.getMember())
                .setTitle("Purge")
                .setColor(Color.RED)
                .setDescription("Purged all recent Bot Messages!")
                .sendEmbedMessage(data.getChannel(), (message) -> message.delete().queueAfter(3, TimeUnit.SECONDS));
    }

    private static void scanMessages(List<Message> history, TextChannel channel) {
        List<Message> delete = new ArrayList<>();
        for (Message m : history) {
            if (m.getAuthor().isBot())
                delete.add(m);
        }
        MessageHandler.deleteMessageList(delete, channel);
    }
}
