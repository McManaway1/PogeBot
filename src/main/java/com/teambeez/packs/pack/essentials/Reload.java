package com.teambeez.packs.pack.essentials;

import com.teambeez.messages.EmbedCreator;
import com.teambeez.packs.PackHandler;
import com.teambeez.parsers.containers.CommandData;

import java.awt.*;

public class Reload {
    public static void invoke(CommandData data, PackHandler handler) {
        EmbedCreator embed = new EmbedCreator(data.getMember())
                .setTitle("Reload Packs")
                .setColor(Color.CYAN);

        //TODO: Permissions
        if (data.getMember().getUser().getIdLong() != 132657708791758848L) {
            embed.setDescription("Invalid Permissions");
        } else {
            handler.startPacks();
            embed.setDescription("Reloaded all Packs");
        }
        embed.sendEmbedMessage(data.getChannel());
    }
}
