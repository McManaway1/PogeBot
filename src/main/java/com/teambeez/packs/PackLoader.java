package com.teambeez.packs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackLoader extends URLClassLoader {
    public PackLoader(URL jarFileUrl) {
        super(new URL[]{jarFileUrl});
    }

    @Nullable
    PackData readJar(JarFile jar) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Enumeration<? extends JarEntry> entries = jar.entries();

        while(entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if(entry.getName().equals("pack.yml")) {
                try (InputStream is = jar.getInputStream(entry)) {
                    return mapper.readValue(is, PackData.class);
                } catch (IOException ignored) { }
            }
        }
        return null;
    }
}
