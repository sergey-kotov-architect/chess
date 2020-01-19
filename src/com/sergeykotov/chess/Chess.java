package com.sergeykotov.chess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

public final class Chess {
    public static void main(String[] args) {
        try (Scanner inputStream = new Scanner(System.in)) {
            String input;
            do {
                System.out.println(Cmd.getCommands());
                input = inputStream.nextLine();
            } while (parseInput(input.toUpperCase().trim()));
        }
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

    private static void calculate(String input) {
        String[] values = input.split("\\s+");
        int xDimension = Integer.parseInt(values[0]);
        int yDimension = Integer.parseInt(values[1]);
        int figureCount = Integer.parseInt(values[2]);
        Figure figure = Figure.valueOf(values[3]);
        Calculation calculation = new Calculation(xDimension, yDimension, figureCount, figure);
        calculation.calculate();
    }

    private static void export() {
        Optional<String> calculationResult = Calculation.getCalculationResult();
        if (!calculationResult.isPresent()) {
            System.out.println("there is no calculation result to export");
            return;
        }
        String file = "chess_calculation_result_" + System.currentTimeMillis() + ".txt";
        String folder = Paths.get("").toAbsolutePath().toString();
        Path path = Paths.get(folder + File.separator + file);
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
            writer.write(calculationResult.get());
        } catch (IOException e) {
            System.out.println("failed to export calculation result to txt-file: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        System.out.println("calculation result has been exported to txt-file " + path.toString());
    }
}