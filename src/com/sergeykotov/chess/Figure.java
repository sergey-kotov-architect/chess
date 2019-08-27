package com.sergeykotov.chess;

public enum Figure {
    QUEEN((Cell cell1, Cell cell2, int dx, int dy) -> cell1.getX() != cell2.getX() && cell1.getY() != cell2.getY() && dx != dy),
    KING((Cell cell1, Cell cell2, int dx, int dy) -> dx > 1 && dy > 1),
    ROOK((Cell cell1, Cell cell2, int dx, int dy) -> cell1.getX() != cell2.getX() && cell2.getY() != cell2.getY()),
    BISHOP((Cell cell1, Cell cell2, int dx, int dy) -> dx != dy),
    KNIGHT((Cell cell1, Cell cell2, int dx, int dy) -> (dx != 1 || dy != 2) && (dx != 2 || dy != 1));

    private Validatable validatable;

    Figure(Validatable validatable) {
        this.validatable = validatable;
    }

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

    public boolean validate(Cell cell1, Cell cell2) {
        int dx = Math.abs(cell1.getX() - cell2.getX());
        int dy = Math.abs(cell1.getY() - cell2.getY());
        return validatable.validate(cell1, cell2, dx, dy);
    }
}