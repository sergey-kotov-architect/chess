package com.sergeykotov.chess;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Calculation {
    public static String calculationResult;

    private static Set<Combination> getCombinations(int xDimension, int yDimension, int figureCount, Figure figure) {
        Cell.initializeCellPool(xDimension, yDimension);
        Set<Combination> combinations = Cell.getCellPool().stream().map(Combination::new).collect(Collectors.toSet());
        for (int i = 1; i < figureCount; i++) {
            Set<Combination> currentCombinations = new HashSet<>(combinations);
            combinations.clear();
            for (Combination currentCombination : currentCombinations) {
                for (Cell newCell : currentCombination.newCells()) {
                    if (currentCombination.validate(newCell, figure)) {
                        Combination combination = new Combination(currentCombination, newCell);
                        combinations.add(combination);
                    }
                }
            }
        }
        return combinations;
    }

    private static String visualise(Combination combination, int xDimension, int yDimension) {
        StringBuilder cells = new StringBuilder(combination + System.lineSeparator());
        for (int y = yDimension - 1; y >= 0; y--) {
            for (int x = 0; x < xDimension; x++) {
                String cell = combination.contains(x, y) ? "1 " : "0 ";
                cells.append(cell);
            }
            cells.append(System.lineSeparator());
        }
        return cells.toString();
    }

    private static String visualise(Set<Combination> combinations, int xDimension, int yDimension) {
        return combinations.stream()
                .map(c -> visualise(c, xDimension, yDimension))
                .reduce((c1, c2) -> c1 + System.lineSeparator() + c2)
                .orElse("");
    }

    public static void calculate(int xDimension, int yDimension, int figureCount, Figure figure) {
        String format = "searching %d x %d chess board cell combinations to allocate %d non-attacking %s...";
        String figures = figure.toString().toLowerCase() + "s";
        String output = String.format(format, xDimension, yDimension, figureCount, figures);
        System.out.println(output);
        long startTime = System.currentTimeMillis();

        Set<Combination> combinations = getCombinations(xDimension, yDimension, figureCount, figure);

        long elapsedTime = System.currentTimeMillis() - startTime;
        String result = combinations.size() + " combinations have been found in " + elapsedTime + " milliseconds";
        System.out.println(result);
        String combinationsView = visualise(combinations, xDimension, yDimension);
        System.out.println(combinationsView);
        calculationResult = output + System.lineSeparator() + result + System.lineSeparator() + combinationsView;
    }
}