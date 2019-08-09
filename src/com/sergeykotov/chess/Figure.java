package com.sergeykotov.chess;

public enum Figure {
    QUEEN, KING, ROOK, BISHOP, KNIGHT;

    public static Figure of(String s) {
        if ("q".equals(s) || "queen".equals(s)) {
            return QUEEN;
        } else if ("k".equals(s) || "king".equals(s)) {
            return KING;
        } else if ("r".equals(s) || "rook".equals(s)) {
            return ROOK;
        } else if ("b".equals(s) || "bishop".equals(s)) {
            return BISHOP;
        } else if ("n".equals(s) || "knight".equals(s)) {
            return KNIGHT;
        } else {
            throw new IllegalArgumentException("failed to get figure from string " + s);
        }
    }
}