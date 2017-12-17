package com.teambeez.packs;

import com.teambeez.containers.CommandData;

public interface IPack {
    void initialize();
    void invoke(CommandData data);
    void close();
}
