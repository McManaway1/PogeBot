package com.teambeez.packs.pack.essentials;

import com.teambeez.messages.EmbedCreator;
import com.teambeez.packs.pack.PackData;
import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.utils.tuple.ImmutablePair;
import net.dv8tion.jda.core.utils.tuple.Pair;

import java.awt.*;
import java.util.Collection;

public class Help {
    public static void invoke(CommandData data, Collection<PackData> packs) {
        EmbedCreator embed = new EmbedCreator(data.getMember())
                .setTitle("Pack-Command List <Version 0.6>")
                .setColor(Color.CYAN);

        Pair<String, String> commandData = getCommands(null, true);
        embed.addField("Default Pack", "*Commands Prebuilt into the Discord API*", false);
        embed.addField("Commands", commandData.getLeft(), true);
        embed.addField("Description", commandData.getRight(), true);

        if (packs.size() < 8) {
            for (PackData packData : packs) {
                commandData = getCommands(packData, false);
                embed.addField(packData.getName(), "*" + packData.getDescription() + "*", false);
                embed.addField("Commands", commandData.getLeft(), true);
                embed.addField("Description", commandData.getRight(), true);
            }
            embed.setDescription("**Below lists each Pack and all of it's Commands**");
        } else {
            embed.setDescription("**This API currently doesn't handle more than seven Command-Packs**");
        }
        embed.sendEmbedMessage(data.getChannel());
    }

    private static Pair<String, String> getCommands(PackData data, boolean internal) {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        if (!internal) {
            for (String command : data.getCommands().keySet()) {
                left.append(command).append("\n");
                right.append(data.getCommands().get(command).get("description")).append("\n");
            }
        } else {
            left.append("Info\n").append("Purge\n").append("Reload\n");
            right.append("Display all Information about the DiscordBot\n")
                    .append("Remove all recent Posts created by the DiscordBot\n")
                    .append("Admin Command used to reload all Packs\n");
        }

        return new ImmutablePair<>(left.toString(), right.toString());
    }
}
