package com.teambeez.plugins;

import com.teambeez.containers.CommandData;
import com.teambeez.plugins.defaults.*;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private static Map<String, DiscordPlugin> plugins;

    public PluginManager() {
        plugins = new HashMap<>();
    }

    public void loadPlugins() {
        /* Clear Plugins (Reload Use)*/
        plugins.clear();

        /* Load Default Plugins */
        Help help = new Help();
        Info info = new Info();
        Purge purge = new Purge();
        Reload reload = new Reload();
        Music music = new Music();
        plugins.put(help.getName().toLowerCase(), help);
        plugins.put(info.getName().toLowerCase(), info);
        plugins.put(purge.getName().toLowerCase(), purge);
        plugins.put(reload.getName().toLowerCase(), reload);
        plugins.put(music.getName().toLowerCase(), music);

        File dir = new File("plugins");
        ClassLoader cl = new DiscordPluginLoader(dir);
        if(dir.exists() && dir.isDirectory()) {
            String[] files = dir.list();
            if(files == null) return;

            for(String file : files) {
                try {
                    if(!file.endsWith(".class")) continue;

                    Object c = cl.loadClass(file.substring(0, file.indexOf("."))).newInstance();
                    if(c instanceof DiscordPlugin) {
                        DiscordPlugin dp = (DiscordPlugin) c;
                        plugins.put(dp.getName().toLowerCase(), dp);
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

    public void invokePlugin(String command, CommandData data) throws PluginNotFoundException {
        if(!plugins.containsKey(command)) throw new PluginNotFoundException();
         plugins.get(command).invoke(data);
    }

    public static Map<String, DiscordPlugin> getPlugins() {
        return Collections.unmodifiableMap(plugins);
    }
}
