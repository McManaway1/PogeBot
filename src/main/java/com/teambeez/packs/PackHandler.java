package com.teambeez.packs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
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
        String[] files;
        File dir = new File("packs");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        if(dir.exists() && dir.isDirectory()) {
            if((files = dir.list()) == null) return;

            for(String file : files) {
                try {
                    if(!file.endsWith(".jar")) continue;
                    JarFile jarFile = new JarFile(dir.getName() + "/" + file);
                    Enumeration<? extends JarEntry> entries = jarFile.entries();

                    while(entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if(entry.getName().equals("pack.yml")) {
                            InputStream is = jarFile.getInputStream(entry);
                            PackData packData = mapper.readValue(is, PackData.class);
                            for(String s : packData.getCommands().keySet()) {
                                System.out.println(s);
                            }
                            is.close();
                        }
                    }

                } catch (Exception e) {
                    throw new Error("File " + file + " doesn't contain DiscordPlugin class");
                }
            }
        } else {
            if(dir.mkdir()) ;//TODO PogeBot.LOG.info("Created Plugins Directory");
            else throw new Error("Unable to create Plugin Directory");
        }
    }

    /**
     * Invokes Packs
     */
    public void invokePacks() {

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
