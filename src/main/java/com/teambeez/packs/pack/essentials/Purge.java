package com.teambeez.packs.pack.essentials;

import com.teambeez.parsers.containers.CommandData;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Purge {
    public static void invoke(CommandData data) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("Purge Command", Color.CYAN, data.getMember().getUser());

        MessageHistory history = data.getChannel().getHistory();
        Consumer<List<Message>> consumer = (m) -> scanMessages(m, data.getChannel());
        history.retrievePast(100).queue(consumer);

        embed.setDescription("Purged all recent PogeBot Messages!");
        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }

    private static void scanMessages(List<Message> history, TextChannel channel){
        List<Message> delete = new ArrayList<>();
        for(Message m : history){
            if(m.getAuthor().equals(m.getJDA().getSelfUser()))
                delete.add(m);
        }
        MessageHandler.deleteMessages(delete, channel);
    }
}
