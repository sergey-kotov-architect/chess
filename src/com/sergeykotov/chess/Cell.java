package com.sergeykotov.chess;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Cell {
    private static Set<Cell> cellPool;

    public static void initializeCellPool(int xDimension, int yDimension) {
        Set<Cell> cells = new HashSet<>(xDimension * yDimension * 2);
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                cells.add(new Cell(x, y));
            }
        }
        cellPool = Collections.unmodifiableSet(cells);
    }

    public static Set<Cell> getCellPool() {
        return cellPool;
    }

    private final int x;
    private final int y;

    private Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}