package com.teambeez.plugins.defaults;

import com.teambeez.containers.CommandData;
import com.teambeez.listeners.Listener;
import com.teambeez.plugins.AbstractPlugin;
import com.teambeez.plugins.CommandType;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.MessageBuilder;

public class Reload extends AbstractPlugin {
    public Reload() {
        super("Reload", "Reload All Plugins", CommandType.HIDE);
    }

    @Override
    public void invoke(CommandData data) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder embed = MessageHandler.getFooterEmbedBuilder("Reload", type.getColor(), data.getAuthor());

        //TODO: Permissions
        if(data.getAuthor().getIdLong() != 132657708791758848L) {
            embed.setDescription("Invalid Permissions");
        }
        else {
            Listener.getPluginManager().loadPlugins();
            embed.setDescription("Reloaded all Plugins");
        }

        MessageHandler.sendMessage(builder.setEmbed(embed.build()).build(), data.getChannel());
    }
}
