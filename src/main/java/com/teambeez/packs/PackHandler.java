package com.teambeez.packs;


import com.teambeez.containers.CommandData;
import com.teambeez.packs.essentials.Help;
import com.teambeez.packs.essentials.Info;
import com.teambeez.packs.essentials.Purge;
import com.teambeez.packs.essentials.Reload;
import net.dv8tion.jda.core.events.Event;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

public class PackHandler {
    private final Map<IPack, PackData> packs;

    public PackHandler() {
        packs = new HashMap<>();
    }

    /**
     * This method either:
     * A: Creates a 'pack' folder on the first boot of the DiscordBot
     * B: Dynamically Loads plugins from the 'pack' folder for invoking later
     */
    public void startPacks() {
        clearPacks();

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
                    Class<?> pluginClass = packLoader.loadClass(data.getMainClass());
                    IPack pack = (IPack) pluginClass.newInstance();
                    pack.initialize();

                    packs.put(pack, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (dir.mkdir()) ;//TODO PogeBot.LOG.info("Created Pack Directory");
            else throw new Error("Unable to create Pack Directory");
        }
    }

    public void alertPacks(Event event) {
        for(IPack pack : packs.keySet()) {
            pack.onEvent(event);
        }
    }

    /**
     * Invokes Packs
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
     * Helper Method called when reloading or closing the DiscordBot.
     * This is primarily used to allow databases to safely shutdown and have the opportunity to
     * save their data.
     */
    public void clearPacks() {
        packs.keySet().forEach(IPack::close);
        packs.clear();
    }

}
