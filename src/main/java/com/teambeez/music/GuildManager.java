package com.teambeez.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildManager {
    private final AudioPlayer player;
    private final TrackScheduler scheduler;
    private TextChannel channel;

    public GuildManager(AudioPlayerManager playerManager) {
        player = playerManager.createPlayer();
        scheduler = new TrackScheduler(this, player);
        player.addListener(scheduler);
    }

    public void setChannel(TextChannel channel) {
        this.channel = channel;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public TrackScheduler getScheduler() {
        return scheduler;
    }
}
