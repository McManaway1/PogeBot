package com.teambeez.plugins.defaults;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.teambeez.PogeBot;
import com.teambeez.containers.CommandData;
import com.teambeez.music.GuildManager;
import com.teambeez.plugins.AbstractPlugin;
import com.teambeez.plugins.CommandType;
import com.teambeez.util.MessageHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.awt.*;
import java.net.URL;

/**
 * PLEASE IGNORE THIS HORROR
 */
public class Music extends AbstractPlugin {
    public static Music instance;

    public Music() {
        super("Music", "Play Music", CommandType.FUN);
        instance = this;
    }

    @Override
    public void invoke(CommandData data) {
        if (data.getArguments().size() == 1) {
            EmbedBuilder eb = MessageHandler.getEmbedBuilder("Music Help", Color.CYAN);
            eb.setDescription(getHelp());
            MessageHandler.sendMessage(new MessageBuilder().setEmbed(eb.build()).build(), data.getChannel());
        } else if (data.getChannel().getGuild() == null);
            //MessageSender.sendPrivateMessage("You cannot use this command in a Private Channel", container.getEvent().getAuthor());
        else if (data.getMember().getVoiceState().getChannel() == null);
            //MessageSender.sendEmbedded("com.teambeez.plugins.defaults.Music Manager", "You must be in a Voice Channel", container);
        else if (data.getArguments().size() == 2) {
            switch (data.getArguments().get(1)) {
                case "P":
                case "p":
                    if (PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().isEmpty())
                        MessageHandler.sendMessage(new MessageBuilder().append("No Songs in Queue").build(), data.getChannel());
                    else {
                       /* connectToFirstVoiceChannel(data.getChannel().getGuild().getAudioManager());
                        PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getPlayer().startTrack(
                                PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().nextTrack(), true);*/
                    }
                    break;
                case "S":
                case "s":
                    if (PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().hasTrack())
                        PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().nextTrack();
                    break;
                case "PQ":
                case "pq":
                case "Pq":
                case "pQ":
                    if (PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().isEmpty());
                        //MessageSender.sendEmbedded("Music Manager", "No Songs to Purge", container);
                    else {
                        PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().purgeQueue();
                        //MessageSender.sendEmbedded("com.teambeez.plugins.defaults.Music Manager", "com.teambeez.plugins.defaults.Music Queue Purged", container);
                    }
                    break;
                case "L":
                case "l":
                    //Leave the Voice-Channel and stop playing song
                    PogeBot.getGuilds().get(data.getChannel().getGuild().getIdLong()).getScheduler().purgeQueue();
                    data.getChannel().getGuild().getAudioManager().closeAudioConnection();
                    break;
                default:
                    EmbedBuilder eb = MessageHandler.getEmbedBuilder("Music Help", Color.CYAN);
                    eb.setDescription(getHelp());
                    MessageHandler.sendPrivateMessage(new MessageBuilder().setEmbed(eb.build()).build(), data.getPrivateChannel());
                    break;
            }
        } else {
            if (!data.getArguments().get(1).equalsIgnoreCase("p")); //printHelp(container.getEvent().getAuthor());
            else if(isValidURL(data.getArguments().get(2)))
                loadAndPlay(data.getChannel().getGuild(), data.getChannel(), data.getArguments().get(2));
            else {
                YoutubeSearchProvider ytProvider = new YoutubeSearchProvider(new YoutubeAudioSourceManager());
                StringBuilder builder = new StringBuilder();
                for(int i = 2; i < data.getArguments().size(); i++) {
                    builder.append(data.getArguments().get(i) + " ");
                }
                BasicAudioPlaylist item = (BasicAudioPlaylist) ytProvider.loadSearchResult(builder.toString());
                loadAndPlay(data.getChannel().getGuild(), data.getChannel(), item.getTracks().get(0).getInfo().uri);
            }
        }

    }

    private void loadAndPlay(Guild g, TextChannel c, String trackUrl) {
        //System.out.println(trackUrl);
        GuildManager musicManager = PogeBot.getGuilds().get(g.getIdLong());
        musicManager.setChannel(c);

        PogeBot.getPlayerManager().loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                //MessageHandler.sendMessage(new MessageBuilder().append("Now Queued: **" + track.getInfo().title + "**").build(), c);

                play(g, musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                //channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(g, musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                //channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                //channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildManager musicManager, AudioTrack track) {
        connectToFirstVoiceChannel(guild.getAudioManager());
        musicManager.getScheduler().queue(track);
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }

    public void stopAll(Guild guild) {
        PogeBot.getGuilds().get(guild.getIdLong()).getScheduler().purgeQueue();
        PogeBot.getGuilds().get(guild.getIdLong()).getPlayer().stopTrack();
        guild.getAudioManager().closeAudioConnection();
    }

    private String getHelp() {
        return  String.format("%-14s : %s%n", "Music P", "If Queue Exists, Join Voice Channel") +
                String.format("%-14s : %s%n", "Music P [URL]", "Queue Music/Playlist via URL") +
                String.format("%-14s : %s%n", "Music P [Song]", "Queue Music based on it's Title") +
                String.format("%-14s : %s%n", "Music S", "Skip the Current Music in Queue") +
                String.format("%-14s : %s%n", "Music PQ", "Purge the Queue") +
                String.format("%-14s : %s%n", "Music L", "Leave The Voice Channel");
    }

    public static boolean isValidURL(String urlString)
    {
        try
        {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception)
        {
            return false;
        }
    }
}
