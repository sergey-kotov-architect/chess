package com.sergeykotov.chess;

public enum Figure {
    QUEEN(c -> c.cell1.getX() != c.cell2.getX() && c.cell1.getY() != c.cell2.getY() && c.dx != c.dy),
    KING(c -> c.dx > 1 && c.dy > 1),
    ROOK(c -> c.cell1.getX() != c.cell2.getX() && c.cell1.getY() != c.cell2.getY()),
    BISHOP(c -> c.dx != c.dy),
    KNIGHT(c -> (c.dx != 1 || c.dy != 2) && (c.dx != 2 || c.dy != 1));

    private Validatable validatable;

    Figure(Validatable validatable) {
        this.validatable = validatable;
    }

    public boolean validate(Cell cell1, Cell cell2) {
        CellPair cellPair = CellPair.create(cell1, cell2);
        return validatable.validate(cellPair);
    }
}