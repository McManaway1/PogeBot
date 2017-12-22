package com.teambeez.packs.pack.essentials;

import com.teambeez.messages.EmbedCreator;
import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.JDAInfo;

public class Info {
    public static void invoke(CommandData data) {
        EmbedCreator.create()
                .setTitle("Bot Information")
                .addField("Creator", "Poge", false)
                .addField("Version", "DEVELOPMENT", false)
                .addField("JDA Version", JDAInfo.VERSION, false)
                .sendEmbedMessage(data.getChannel());
    }
}
