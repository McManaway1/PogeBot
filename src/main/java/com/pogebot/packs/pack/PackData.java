package com.pogebot.packs.pack;

import java.util.Map;

public class PackData {
    private String name;
    private String mainClass;
    private String description;
    private String version;
    private Map<String, Map<String, String>> commands;

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public String getMainClass() {
        return mainClass;
    }

    @SuppressWarnings("unused")
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    @SuppressWarnings("unused")
    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Map<String, String>> getCommands() {
        return commands;
    }

    @SuppressWarnings("unused")
    public void setCommands(Map<String, Map<String, String>> commands) {
        this.commands = commands;
    }
}
