package com.sergeykotov.chess;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Chess {
    private static final int X_DIMENSION = 3;
    private static final int Y_DIMENSION = 2;
    private static final int FIGURE_COUNT = 2;
    private static final Figure FIGURE = Figure.KNIGHT;

    static {
        Cell.initializeCellPool(X_DIMENSION, Y_DIMENSION);
    }

    private static Set<Combination> getCombinations() {
        Set<Combination> combinations = Cell.getCellPool().stream().map(Combination::new).collect(Collectors.toSet());
        for (int i = 1; i < FIGURE_COUNT; i++) {
            Set<Combination> currentCombinations = new HashSet<>(combinations);
            combinations.clear();
            for (Combination currentCombination : currentCombinations) {
                for (Cell newCell : currentCombination.newCells()) {
                    if (currentCombination.validate(newCell, FIGURE)) {
                        Combination combination = new Combination(currentCombination, newCell);
                        combinations.add(combination);
                    }
                }
            }
        }
        return combinations;
    }

    private static String visualise(Combination combination) {
        StringBuilder cells = new StringBuilder(combination + System.lineSeparator());
        for (int y = Y_DIMENSION - 1; y >= 0; y--) {
            for (int x = 0; x < X_DIMENSION; x++) {
                String cell = combination.contains(x, y) ? "1 " : "0 ";
                cells.append(cell);
            }
            cells.append(System.lineSeparator());
        }
        return cells.toString();
    }

    public static String visualise(Set<Combination> combinations) {
        return combinations.stream()
                .map(Chess::visualise)
                .reduce((c1, c2) -> c1 + System.lineSeparator() + c2)
                .orElse("");
    }

    public static void main(String[] args) {
        String format = "searching %d x %d chess board cell combinations to allocate %d non-attacking %s...";
        String figures = FIGURE.toString().toLowerCase() + "s";
        String output = String.format(format, X_DIMENSION, Y_DIMENSION, FIGURE_COUNT, figures);
        System.out.println(output);
        long startTime = System.currentTimeMillis();

        Set<Combination> combinations = getCombinations();

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(combinations.size() + " combinations have been found in " + elapsedTime + " milliseconds");
        System.out.println(visualise(combinations));
    }
}