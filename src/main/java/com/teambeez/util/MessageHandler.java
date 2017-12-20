package com.teambeez.util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class MessageHandler {
    public static void sendMessage(Message message, TextChannel channel) {
        if (channel != null && message != null)
            channel.sendMessage(message).queue();
    }

    public static void sendPrivateMessage(Message message, PrivateChannel channel) {
        if (channel != null && message != null)
            channel.sendMessage(message).queue();
    }

    public static void deleteMessage(Message message, TextChannel channel) {
        /* Check Permissions */
        if (channel == null || message == null) return;
        if (!message.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_MANAGE)) return;

        /* Delete Message */
        channel.deleteMessageById(message.getId()).queue();
    }

    public static void deleteMessages(List<Message> messages, TextChannel channel) {
        /* Check Permissions */
        if (messages == null || channel == null || messages.size() == 0) return;
        if (!messages.get(0).getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_MANAGE)) return;

        /* Mass Delete Messages */
        if (messages.size() > 1) channel.deleteMessages(messages).queue();
        else if (messages.size() == 1) deleteMessage(messages.get(0), channel);
    }

    public static EmbedBuilder getFooterEmbedBuilder(String title, Color c, User user) {
        EmbedBuilder eb = getEmbedBuilder(title, c);
        eb.setFooter(user.getName(), user.getAvatarUrl());
        return eb;
    }

    public static EmbedBuilder getEmbedBuilder(String title, Color c) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setColor(c);
        eb.setTimestamp(Instant.now());
        return eb;
    }
}
