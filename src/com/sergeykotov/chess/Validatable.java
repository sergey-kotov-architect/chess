package com.sergeykotov.chess;

@FunctionalInterface
public interface Validatable {
    boolean validate(Cell cell1, Cell cell2, int dx, int dy);
}