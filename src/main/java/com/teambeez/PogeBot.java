package com.teambeez;

import com.teambeez.listeners.Listener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

/**
 * This is the Main class for the PogeBot Plugin API.
 * This class is used to build a Java Discord API Implementation
 */
public class PogeBot {

    /**
     * Constructs the PogeBot Object, which will start the JDA Process with a Presence.
     *
     * @param token The Private Discord Token for a Bot.
     */
    private PogeBot(String token) {
        /* Initialize Bot */
        try {
            JDA jda = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(new Listener()).buildAsync();

            /* Set Presence */
            Game presence = Game.playing("!help");
            jda.getPresence().setPresence(presence, false);
        } catch (LoginException e) {
            throw new Error("Failed to Authenticate");
        }
    }

    public static void main(String[] args) {
        /* Start Bot using Command Arguments */
        if (args.length == 1) new PogeBot(args[0]);
        else System.out.println("Invalid Arguments: <Key>");
    }
}
