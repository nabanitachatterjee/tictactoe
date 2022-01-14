package com.gameboard.tictactoe.exception;

public class MovesNotAllowedException extends RuntimeException {

    public MovesNotAllowedException(String s) {
        super(s);
    }
}