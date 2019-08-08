package com.sergeykotov.chess;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Combination {
    private final Set<Cell> cells;

    public Combination(Cell cell) {
        Set<Cell> cellSet = new HashSet<>();
        cellSet.add(cell);
        cells = Collections.unmodifiableSet(cellSet);
    }

    public Combination(Combination combination, Cell cell) {
        Set<Cell> cellSet = new HashSet<>(combination.cells);
        cellSet.add(cell);
        cells = Collections.unmodifiableSet(cellSet);
    }

    public Set<Cell> newCells() {
        return Cell.getCellPool().stream().filter(c -> !cells.contains(c)).collect(Collectors.toSet());
    }

    public boolean validate(Cell cell, Figure figure) {
        return cells.stream().allMatch(c -> c.validate(cell, figure));
    }

    public boolean contains(int x, int y) {
        return cells.stream().anyMatch(c -> c.getX() == x && c.getY() == y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combination that = (Combination) o;
        return Objects.equals(cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}