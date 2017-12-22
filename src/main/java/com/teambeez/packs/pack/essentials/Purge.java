package com.teambeez.packs.pack.essentials;

import com.teambeez.messages.EmbedCreator;
import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Purge {
    public static void invoke(CommandData data) {
        MessageHistory history = data.getChannel().getHistory();
        Consumer<List<Message>> consumer = (m) -> scanMessages(m, data.getChannel());
        history.retrievePast(100).queue(consumer);

        new EmbedCreator().setTitle("Purge")
                .setDescription("Purged all recent PogeBot Messages!")
                .sendEmbedMessage(data.getChannel());
    }

    private static void scanMessages(List<Message> history, TextChannel channel) {
        List<Message> delete = new ArrayList<>();
        for (Message m : history) {
            if (m.getAuthor().equals(m.getJDA().getSelfUser()))
                delete.add(m);
        }
        if (!delete.get(0).getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_MANAGE)) return;

        /* Mass Delete Messages */
        if (delete.size() > 1) channel.deleteMessages(delete).queue();
        else if (delete.size() == 1) channel.deleteMessageById(delete.get(0).getId()).queue();
    }
}
