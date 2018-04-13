package com.teambeez.packs.pack.essentials;

import com.teambeez.messages.EmbedCreator;
import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.JDAInfo;

import java.awt.*;

public class Info {
    public static void invoke(CommandData data) {
        EmbedCreator.create(data.getMember())
                .setTitle("Bot Information")
                .setColor(Color.CYAN)
                .addField("Creator", "Poge", false)
                .addField("Version", "0.5 Beta", false)
                .addField("JDA Version", JDAInfo.VERSION, false)
                .sendEmbedMessage(data.getChannel());
    }
}
