package com.sergeykotov.chess;

public final class CellPair {
    public final Cell cell1;
    public final Cell cell2;
    public final int dx;
    public final int dy;

    public CellPair(Cell cell1, Cell cell2, int dx, int dy) {
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.dx = dx;
        this.dy = dy;
    }
}