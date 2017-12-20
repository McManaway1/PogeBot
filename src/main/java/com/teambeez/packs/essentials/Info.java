package com.teambeez.packs.essentials;

import com.teambeez.parsers.containers.CommandData;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.MessageBuilder;

import java.awt.*;

public class Info {
    public static void invoke(CommandData data) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("PogeBot Information", Color.CYAN, data.getMember().getUser());

        embed.addField("Creator", "Poge", false);
        embed.addField("Version", "DEVELOPMENT", false);
        embed.addField("JDA Version", JDAInfo.VERSION, false);

        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }
}
