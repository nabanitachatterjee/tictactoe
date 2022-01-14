package com.gameboard.tictactoe.IO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicTacToeIO {
    private int[] board;
    private String winner;
    private String errorMessage;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TicTacToeIO{");
        sb.append("board=").append(Arrays.toString(board));
        sb.append(", winner='").append(winner).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}