package com.teambeez.packs;


import com.teambeez.packs.pack.IPack;
import com.teambeez.packs.pack.PackData;
import com.teambeez.packs.pack.essentials.Help;
import com.teambeez.packs.pack.essentials.Info;
import com.teambeez.packs.pack.essentials.Purge;
import com.teambeez.packs.pack.essentials.Reload;
import com.teambeez.parsers.containers.CommandData;
import net.dv8tion.jda.core.events.Event;

import java.io.File;
import java.util.*;
import java.util.jar.JarFile;

public class PackHandler {
    private final Set<String> commands;
    private final Map<IPack, PackData> packs;

    public PackHandler() {
        packs = new HashMap<>();
        this.commands = new HashSet<>();
    }

    /**
     * @return if the entered command exists in a pack
     */
    public boolean commandExists(String command) {
        return commands.contains(command.toLowerCase());
    }

    /**
     * This method at startup will load all Packs currently stored in the 'packs' folder.
     * It will also allow for Packs to be dynamically reloaded during runtime, either to update an
     * existing Pack, or to initialize a new one.
     */
    public void startPacks() {
        clearPacks();
        addDefaultCommands();

        /* Load Default Plugins */
        File[] files;
        File dir = new File("packs");

        if (dir.exists() && dir.isDirectory()) {
            if ((files = dir.listFiles()) == null) return;

            for (File file : files) {
                try {
                    if (!file.getName().endsWith(".jar")) continue;
                    PackLoader packLoader = new PackLoader(file.toURI().toURL());
                    PackData data = packLoader.readJar(new JarFile(file.toURI().toURL().getPath()));
                    /* Check if Jar contained a 'pack.yml' file */
                    if (data == null) {
                        packLoader.close();
                        continue;
                    }
                    for (String command : data.getCommands().keySet()) {
                        commands.add(command.toLowerCase());
                    }

                    Class<?> pluginClass = packLoader.loadClass(data.getMainClass());
                    IPack pack = (IPack) pluginClass.newInstance();
                    pack.initialize();

                    packs.put(pack, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (dir.mkdir()) System.out.println("Created Pack Dir"); //TODO: PogeBot.LOG.info("Created Pack Directory");
            else throw new Error("Unable to create Pack Directory");
        }
    }

    /**
     * This method alerts all Packs of an event occurring, allows for Pack Creators to handle
     * events other than 'Startup' and 'MessageReceived'
     *
     * @param event The event triggered
     */
    public void alertPacks(Event event) {
        for (IPack pack : packs.keySet()) {
            pack.onEvent(event);
        }
    }

    /**
     * Whenever a Command is received, this method will be called to pass the CommandData object
     * to all loaded Packs in Memory.
     */
    public void invokePacks(CommandData data) {
        /* Scan if the command is from this API, then check if it belongs to a Pack */
        switch (data.getCommand()) {
            case "help":
                Help.invoke(data, packs.values());
                break;
            case "info":
                Info.invoke(data);
                break;
            case "purge":
                Purge.invoke(data);
                break;
            case "reload":
                Reload.invoke(data, this);
                break;
            default:
                for (IPack pack : packs.keySet()) {
                    pack.invoke(data);
                }
        }
    }

    /**
     * Adds all default commands to the ArrayList
     */
    private void addDefaultCommands() {
        Collections.addAll(commands, "help", "info", "purge", "reload");
    }


    /**
     * Helper Method called when reloading or closing the DiscordBot.
     * This is primarily used to allow databases to safely shutdown and have the opportunity to
     * save their data.
     */
    public void clearPacks() {
        commands.clear();

        packs.keySet().forEach(IPack::close);
        packs.clear();
    }

}
