package com.teambeez.plugins.defaults;

import com.teambeez.containers.CommandData;
import com.teambeez.plugins.AbstractPlugin;
import com.teambeez.plugins.CommandType;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Purge extends AbstractPlugin {
    public Purge() {
        super("Purge", "", CommandType.UTIL);
    }

    @Override
    public void invoke(CommandData data) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("Purge Command", type.getColor(), data.getAuthor());

        MessageHistory history = data.getChannel().getHistory();
        Consumer<List<Message>> consumer = (m) -> scanMessages(m, data.getChannel());
        history.retrievePast(100).queue(consumer);

        embed.setDescription("Purged all recent PogeBot Messages!");
        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }

    private void scanMessages(List<Message> history, TextChannel channel){
        List<Message> delete = new ArrayList<>();
        for(Message m : history){
            if(m.getAuthor().equals(m.getJDA().getSelfUser()))
                delete.add(m);
        }
        MessageHandler.deleteMessages(delete, channel);
    }
}
