package com.teambeez.messages;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class MessageHandler {
    /* Send Messages */

    public static void sendMessage(Message message, TextChannel channel) {
        channel.sendMessage(message).queue();
    }

    public static void sendMessage(Message message, TextChannel channel, Consumer<Message> consumer) {
        channel.sendMessage(message).queue(consumer);
    }

    public static void sendMessage(String message, TextChannel channel) {
        channel.sendMessage(message).queue();
    }

    public static void sendMessage(String message, File file, TextChannel channel) {
        channel.sendMessage(message).addFile(file).queue();
    }

    public static void sendPrivateMessage(String message, Member member) {
        member.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage(message).queue());
    }

    public static void sendPrivateMessage(String message, File file, Member member) {
        member.getUser().openPrivateChannel().queue((channel) -> channel.sendMessage(message).addFile(file).queue());
    }

    /* Delete Messages */

    public static void deleteMessage(Message message) {
        message.delete().queue();
    }

    public static void deleteMessage(String reason, Message message) {
        message.delete().reason(reason).queue();
    }

    public static void deleteMessageList(List<Message> messages, TextChannel channel) {
        if (messages.size() > 1) channel.deleteMessages(messages).queue();
        else if (messages.size() == 1) deleteMessage(messages.get(0));
    }
}
