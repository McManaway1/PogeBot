package com.teambeez.plugins;

import java.awt.*;

public enum CommandType {
    TEXT(Color.GREEN),
    IMAGE(Color.CYAN),
    FUN(Color.GREEN),
    UTIL(Color.RED),
    HIDE(Color.ORANGE);

    private Color color;

    CommandType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
