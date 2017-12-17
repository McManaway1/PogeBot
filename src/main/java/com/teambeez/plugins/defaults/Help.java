package com.teambeez.plugins.defaults;

import com.teambeez.containers.CommandData;
import com.teambeez.plugins.AbstractPlugin;
import com.teambeez.plugins.CommandType;
import com.teambeez.plugins.DiscordPlugin;
import com.teambeez.plugins.PluginManager;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;

import java.util.StringJoiner;

public class Help extends AbstractPlugin {
    public Help() {
        super("Help", "", CommandType.HIDE);
    }

    @Override
    public void invoke(CommandData data) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("Available Commands", type.getColor(), data.getAuthor());

        for(CommandType type : CommandType.values()) {
            if(type.equals(CommandType.HIDE)) continue;
            StringJoiner joiner = new StringJoiner(", ");
            for (DiscordPlugin plugin : PluginManager.getPlugins().values()) {
                if(plugin.getType().equals(type)) joiner.add(plugin.getName());
            }
            if(joiner.length() > 0) embed.addField(type.name(), joiner.toString(), false);
        }

        embed.setDescription("Use !help <command> for an extended description");
        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }
}
