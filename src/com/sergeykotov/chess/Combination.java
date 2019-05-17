package com.sergeykotov.chess;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Combination {
    private final Set<Cell> cells = new HashSet<>();

    public Combination(Cell cell) {
        cells.add(cell);
    }

    public Combination(Combination combination) {
        cells.addAll(combination.cells);
    }

    public Set<Cell> newCells() {
        return Cell.getCellPool().stream().filter(c -> !cells.contains(c)).collect(Collectors.toSet());
    }

    public void offer(Cell cell, Figure figure) {
        if (cells.stream().allMatch(c -> c.validate(cell, figure))) {
            cells.add(cell);
        }
    }

    public int size() {
        return cells.size();
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