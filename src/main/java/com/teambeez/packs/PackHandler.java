package com.teambeez.packs;


import com.teambeez.containers.CommandData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class PackHandler {
    private final List<IPack> packs;

    public PackHandler() {
        packs = new ArrayList<>();
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

        if(dir.exists() && dir.isDirectory()) {
            if((files = dir.listFiles()) == null) return;

            for(File file : files) {
                try {
                    if(!file.getName().endsWith(".jar")) continue;
                    PackLoader packLoader = new PackLoader(file.toURI().toURL());
                    PackData data =  packLoader.readJar(new JarFile(file.toURI().toURL().getPath()));
                    /* Check if Jar contained a 'pack.yml' file */
                    if(data == null) {
                        packLoader.close();
                        continue;
                    }
                    Class<?> pluginClass = packLoader.loadClass(data.getMainClass());
                    IPack pack = (IPack) pluginClass.newInstance();
                    pack.initialize();

                    packs.add(pack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if(dir.mkdir()) ;//TODO PogeBot.LOG.info("Created Pack Directory");
            else throw new Error("Unable to create Pack Directory");
        }
    }

    /**
     * Invokes Packs
     */
    public void invokePacks(CommandData data) {
        for(IPack pack : packs) {
            pack.invoke(data);
        }
    }


    /**
     * Helper Method called when reloading or closing the DiscordBot.
     * This is primarily used to allow databases to safely shutdown and have the opportunity to
     * save their data.
     */
    public void clearPacks() {
        packs.forEach(IPack::close);
        packs.clear();
    }

}
