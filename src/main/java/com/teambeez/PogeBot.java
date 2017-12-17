package com.teambeez;

import com.teambeez.listeners.Listener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import javax.security.auth.login.LoginException;

/**
 * This is the Main class for the PogeBot Plugin API.
 * This class is used to build a Java Discord API Implementation
 */
public class PogeBot {

    private PogeBot(String info, String token) {
        /* Initialise Bot */
        try {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(new Listener())
                    .buildAsync();
            jda.getPresence().setGame(Game.playing(info));
        }
        catch (LoginException e) { throw new Error("Failed to Authenticate"); }
        catch (RateLimitedException e) { throw new Error("Too many Login Attempts"); }
    }


    public static void main(String[] args) {
        /* Start Bot using Command Arguments */
        if (args.length == 2) new PogeBot(args[0], args[1]);
        else System.out.println("Invalid Arguments: <Info> <Key>");
    }
}
