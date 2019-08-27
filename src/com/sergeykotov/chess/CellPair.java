package com.sergeykotov.chess;

public final class CellPair {
    public final Cell cell1;
    public final Cell cell2;
    public final int dx;
    public final int dy;

    private CellPair(Cell cell1, Cell cell2, int dx, int dy) {
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.dx = dx;
        this.dy = dy;
    }

    public static CellPair create(Cell cell1, Cell cell2) {
        int dx = Math.abs(cell1.getX() - cell2.getX());
        int dy = Math.abs(cell1.getY() - cell2.getY());
        return new CellPair(cell1, cell2, dx, dy);
    }
}