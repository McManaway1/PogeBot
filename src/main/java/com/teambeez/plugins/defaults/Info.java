package com.teambeez.plugins.defaults;

import com.teambeez.containers.CommandData;
import com.teambeez.plugins.AbstractPlugin;
import com.teambeez.plugins.CommandType;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.MessageBuilder;

public class Info extends AbstractPlugin {
    public Info() {
        super("Info", "Display PogeBot Information", CommandType.UTIL);
    }

    @Override
    public void invoke(CommandData data) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("PogeBot Information", type.getColor(), data.getAuthor());

        embed.addField("Creator", "Poge", false);
        embed.addField("Version", "DEVELOPMENT", false);
        embed.addField("JDA Version", JDAInfo.VERSION, false);

        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }
}
