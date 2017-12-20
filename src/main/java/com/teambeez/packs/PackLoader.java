package com.teambeez.packs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.teambeez.packs.pack.PackData;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class is used to load Pack Jar-Files into API, this class will read the required
 * 'pack.yml' file inside of a jar.
 */
class PackLoader extends URLClassLoader {
    PackLoader(URL jarFileUrl) {
        super(new URL[]{jarFileUrl});
    }

    /**
     * Reads the required 'pack.yml' inside the jar
     *
     * @param jar The Jar-File to read.
     * @return The Data received from the 'pack.yml', or null if no file existed.
     */
    @Nullable
    PackData readJar(JarFile jar) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Enumeration<? extends JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().equals("pack.yml")) {
                try (InputStream is = jar.getInputStream(entry)) {
                    return mapper.readValue(is, PackData.class);
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }
}
