package com.teambeez.packs;

import java.util.Map;

public class PackData {
    private String name;
    private String main;
    private String description;
    private String version;
    private Map<String, Map<String, String>> commands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main + ".class";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Map<String, String>> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Map<String, String>> commands) {
        this.commands = commands;
    }
}
