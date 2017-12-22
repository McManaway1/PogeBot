package com.teambeez.messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.function.Consumer;

public class EmbedCreator {
    private EmbedBuilder embed;

    public EmbedCreator() {
        this.embed = new EmbedBuilder();
    }

    public EmbedCreator setTitle(String title) {
        embed.setTitle(title);
        return this;
    }

    public EmbedCreator setDescription(String description) {
        embed.setDescription(description);
        return this;
    }

    public EmbedCreator addField(String title, String value, boolean inline) {
        embed.addField(title, value, inline);
        return this;
    }

    public EmbedCreator setImage(String url) {
        embed.setImage(url);
        return this;
    }

    public void sendEmbedMessage(TextChannel channel) {
        MessageBuilder message = new MessageBuilder().setEmbed(embed.build());
        channel.sendMessage(message.build()).queue();
    }

    public void sendEmbedMessage(TextChannel channel, Consumer<Message> consumer) {
        MessageBuilder message = new MessageBuilder().setEmbed(embed.build());
        channel.sendMessage(message.build()).queue(consumer);
    }
}
