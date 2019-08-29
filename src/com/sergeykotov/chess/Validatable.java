package com.sergeykotov.chess;

@FunctionalInterface
public interface Validatable {
    boolean validate(CellPair cellPair);
}