package com.gameboard.tictactoe.exception;

public class BoardUnavailableException extends IllegalStateException {

    public BoardUnavailableException(String s) {
        super(s);
    }
}