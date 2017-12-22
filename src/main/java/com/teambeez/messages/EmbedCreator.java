package com.teambeez.messages;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.function.Consumer;

public class EmbedCreator {
    private User user;
    private EmbedBuilder embed;

    public EmbedCreator() {
        this.embed = new EmbedBuilder();
    }

    public EmbedCreator(Member member) {
        this.embed = new EmbedBuilder();
        this.user = member.getUser();
    }

    public static EmbedCreator create() {
        return new EmbedCreator();
    }

    public static EmbedCreator create(Member member) {
        return new EmbedCreator(member);
    }

    public EmbedCreator setTitle(String title) {
        embed.setTitle(title);
        return this;
    }

    public EmbedCreator setColor(Color color) {
        embed.setColor(color);
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

    public EmbedCreator setMember(Member member) {
        this.user = member.getUser();
        return this;
    }

    public void sendEmbedMessage(TextChannel channel) {
        generateFooter();
        MessageBuilder message = new MessageBuilder().setEmbed(embed.build());
        MessageHandler.sendMessage(message.build(), channel);
    }

    public void sendEmbedMessage(TextChannel channel, Consumer<Message> consumer) {
        generateFooter();
        MessageBuilder message = new MessageBuilder().setEmbed(embed.build());
        MessageHandler.sendMessage(message.build(), channel, consumer);
    }

    private void generateFooter() {
        if(user == null) return;
        embed.setFooter(user.getName(), user.getAvatarUrl());
    }
}
