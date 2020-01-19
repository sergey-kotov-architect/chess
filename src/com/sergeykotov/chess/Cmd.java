package com.sergeykotov.chess;

import java.util.Arrays;

public enum Cmd {
    CALC("n m k f - find n x m chess board cell combinations to allocate k non-attacking figures f",
            "\\d+\\s+\\d+\\s+\\d+\\s+(QUEEN|KING|ROOK|BISHOP|KNIGHT)"),
    EXPORT("export - create a txt-file in the current folder with last calculation result", "EXPORT"),
    EXIT("exit - close the program", "EXIT");

    private String description;
    private String regexp;

    Cmd(String description, String regexp) {
        this.description = description;
        this.regexp = regexp;
    }

    public String getDescription() {
        return description;
    }

    public String getRegexp() {
        return regexp;
    }

    public static String getCommands() {
        return Arrays.stream(Cmd.values())
                .map(Cmd::getDescription)
                .reduce((c1, c2) -> c1 + System.lineSeparator() + c2)
                .orElse("");
    }
}