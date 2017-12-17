package com.teambeez;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.teambeez.listeners.Listener;
import com.teambeez.music.GuildManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the Main class for the PogeBot Plugin API.
 * This class is used to build a Java Discord API Implementation
 */
public class PogeBot {
    //public static final SimpleLog LOG = SimpleLog.getLog(PogeBot.class);

    private static Map<Long,GuildManager> guilds;
    private static AudioPlayerManager playerManager;

    public static AudioPlayerManager getPlayerManager() {
        return playerManager;
    }

    public static Map<Long, GuildManager> getGuilds() {

        return guilds;
    }

    private PogeBot(String info, String token) {
        /* Initialise Bot */
        try {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(new Listener())
                    .buildBlocking();
            jda.getPresence().setGame(Game.playing(info));

            guilds = new HashMap<>();
            playerManager = new DefaultAudioPlayerManager();

            AudioSourceManagers.registerRemoteSources(playerManager);
            AudioSourceManagers.registerLocalSource(playerManager);

            for(Guild g : jda.getGuilds()) {
                GuildManager musicManager = new GuildManager(playerManager);
                guilds.put(g.getIdLong(), musicManager);
                g.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            }

        }
        catch (LoginException e) { throw new Error("Failed to Authenticate"); }
        catch (InterruptedException e) { throw new Error("Login Interrupted"); }
        catch (RateLimitedException e) { throw new Error("Too many Login Attempts"); }

        /* Set Permission Manager */
        //TODO: System.setSecurityManager(new PluginSecurityManager());
    }


    public static void main(String[] args) {
        /* Start Bot using Command Arguments */
        if (args.length == 2) new PogeBot(args[0], args[1]);
        else System.out.println("Invalid Arguments: <com.teambeez.plugins.defaults.Info> <Key>");
    }
}
