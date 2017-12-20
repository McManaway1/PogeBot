package com.teambeez.packs.pack.essentials;

import com.teambeez.parsers.containers.CommandData;
import com.teambeez.packs.PackHandler;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;

import java.awt.*;

public class Reload {
    public static void invoke(CommandData data, PackHandler handler) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("Reload", Color.CYAN, data.getMember().getUser());

        //TODO: Permissions
        if(data.getMember().getUser().getIdLong() != 132657708791758848L) {
            embed.setDescription("Invalid Permissions");
        }
        else {
            handler.startPacks();
            embed.setDescription("Reloaded all Packs");
        }

        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }
}
