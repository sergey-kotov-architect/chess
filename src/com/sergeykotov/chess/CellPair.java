package com.sergeykotov.chess;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPair cellPair = (CellPair) o;
        return Objects.equals(cell1, cellPair.cell1) &&
                Objects.equals(cell2, cellPair.cell2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cell1, cell2);
    }

    public static CellPair create(Cell cell1, Cell cell2) {
        int dx = Math.abs(cell1.getX() - cell2.getX());
        int dy = Math.abs(cell1.getY() - cell2.getY());
        return new CellPair(cell1, cell2, dx, dy);
    }
}