package com.sergeykotov.chess;

public enum Figure {
    QUEEN((CellPair c) -> c.cell1.getX() != c.cell2.getX() && c.cell1.getY() != c.cell2.getY() && c.dx != c.dy),
    KING((CellPair c) -> c.dx > 1 && c.dy > 1),
    ROOK((CellPair c) -> c.cell1.getX() != c.cell2.getX() && c.cell2.getY() != c.cell2.getY()),
    BISHOP((CellPair c) -> c.dx != c.dy),
    KNIGHT((CellPair c) -> (c.dx != 1 || c.dy != 2) && (c.dx != 2 || c.dy != 1));

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
        return validatable.validate(new CellPair(cell1, cell2, dx, dy));
    }
}