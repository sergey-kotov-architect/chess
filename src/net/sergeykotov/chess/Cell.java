package net.sergeykotov.chess;

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

    public boolean validate(Cell cell, Figure figure) {
        int dx = Math.abs(x - cell.x);
        int dy = Math.abs(y - cell.y);
        switch (figure) {
            case QUEEN:
                return x != cell.x && y != cell.y && dx != dy;
            case KING:
                return dx > 1 && dy > 1;
            case ROOK:
                return x != cell.x && y != cell.y;
            case BISHOP:
                return dx != dy;
            case KNIGHT:
                return (dx != 1 || dy != 2) && (dx != 2 || dy != 1);
            default: {
                String message = "failed to validate cell pair [" + this + ", " + cell + "]: unknown figure " + figure;
                throw new UnsupportedOperationException(message);
            }
        }
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