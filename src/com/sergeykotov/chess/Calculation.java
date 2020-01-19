package com.sergeykotov.chess;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class Calculation {
    private static String calculationResult;

    public static Optional<String> getCalculationResult() {
        return calculationResult == null ? Optional.empty() : Optional.of(calculationResult);
    }

    private final int X_DIMENSION;
    private final int Y_DIMENSION;
    private final int FIGURE_COUNT;
    private final Figure FIGURE;

    public Calculation(int x_DIMENSION, int y_DIMENSION, int FIGURE_COUNT, Figure FIGURE) {
        X_DIMENSION = x_DIMENSION;
        Y_DIMENSION = y_DIMENSION;
        this.FIGURE_COUNT = FIGURE_COUNT;
        this.FIGURE = FIGURE;
    }

    public void calculate() {
        String format = "searching %d x %d chess board cell combinations to allocate %d non-attacking %s...";
        String figures = FIGURE.toString().toLowerCase() + "s";
        String output = String.format(format, X_DIMENSION, Y_DIMENSION, FIGURE_COUNT, figures);
        System.out.println(output);
        long startTime = System.currentTimeMillis();

        Set<Combination> combinations = getCombinations();

        long elapsedTime = System.currentTimeMillis() - startTime;
        String result = combinations.size() + " combinations have been found in " + elapsedTime + " milliseconds";
        System.out.println(result);
        String combinationsView = visualise(combinations);
        System.out.println(combinationsView);
        calculationResult = output + System.lineSeparator() + result + System.lineSeparator() + combinationsView;
    }

    private Set<Combination> getCombinations() {
        Cell.initializeCellPool(X_DIMENSION, Y_DIMENSION);
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

    private String visualise(Set<Combination> combinations) {
        return combinations.stream()
                .map(this::visualise)
                .reduce((c1, c2) -> c1 + System.lineSeparator() + c2)
                .orElse("");
    }

    private String visualise(Combination combination) {
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
}