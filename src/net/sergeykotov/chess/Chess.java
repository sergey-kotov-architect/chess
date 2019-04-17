package net.sergeykotov.chess;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Chess {
    private static final int X_DIMENSION = 8;
    private static final int Y_DIMENSION = 8;
    private static final int FIGURE_COUNT = 8;

    static {
        Cell.initializeCellPool(X_DIMENSION, Y_DIMENSION);
    }

    private static Set<Combination> getCombinations(Figure figure) {
        Set<Combination> combinations = new HashSet<>();
        for (Cell cell : Cell.getCellPool()) {
            combinations.add(new Combination(cell));
        }
        for (int i = 1; i < FIGURE_COUNT; i++) {
            Set<Combination> currentCombinations = new HashSet<>(combinations);
            combinations.clear();
            for (Combination currentCombination : currentCombinations) {
                for (Cell cell : currentCombination.newCells()) {
                    Combination combination = new Combination(currentCombination);
                    combination.offer(cell, figure);
                    combinations.add(combination);
                }
            }
        }
        return combinations.stream().filter(c -> c.size() == FIGURE_COUNT).collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        for (Figure figure : Figure.values()) {
            Set<Combination> combinations = getCombinations(figure);
            String output = figure + " " + combinations.size() + " combinations " + combinations;
            System.out.println(output);
        }
    }
}