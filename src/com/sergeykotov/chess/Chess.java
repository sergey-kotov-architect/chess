package com.sergeykotov.chess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class Chess {
    private static String calculationResult;

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

    private static void calculate(int xDimension, int yDimension, int figureCount, Figure figure) {
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

    private static void calculate(String input) {
        String[] values = input.split("\\s+");
        int xDimension = Integer.valueOf(values[0]);
        int yDimension = Integer.valueOf(values[1]);
        int figureCount = Integer.valueOf(values[2]);
        Figure figure = Figure.of(values[3]);
        calculate(xDimension, yDimension, figureCount, figure);
    }

    private static void export() {
        if (calculationResult == null) {
            System.out.println("there is no calculation result to export");
            return;
        }
        String file = "chess_calculation_result_" + System.currentTimeMillis() + ".txt";
        String folder = Paths.get("").toAbsolutePath().toString();
        Path path = Paths.get(folder + File.separator + file);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            writer.write(calculationResult);
        } catch (IOException e) {
            System.out.println("failed to export calculation result to txt-file: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        System.out.println("calculation result has been exported to txt-file " + path.toString());
    }

    private static boolean parseInput(String input) {
        if (input.matches(Cmd.EXIT.getRegexp())) {
            return false;
        }
        if (input.matches(Cmd.CALC.getRegexp())) {
            calculate(input);
        } else if (input.matches(Cmd.EXPORT.getRegexp())) {
            export();
        } else {
            System.out.println("command not found");
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner inputStream = new Scanner(System.in);
        String input;
        do {
            System.out.println(Cmd.getCommands());
            input = inputStream.nextLine();
        } while (parseInput(input.toLowerCase().trim()));
    }
}