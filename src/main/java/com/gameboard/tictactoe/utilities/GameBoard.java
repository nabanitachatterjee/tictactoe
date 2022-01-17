package com.gameboard.tictactoe.utilities;

public final class GameBoard {

    public static int[] createFreshGameBoard() {
        return new int[]{-1, -1, -1, -1, -1, -1, -1, -1,-1};
    }

    public static boolean boardHasEmptySpaces(int[] board) {
        boolean hasEmptySpaces = false;
        for (int j : board) {
            if (j == -1) {
                hasEmptySpaces = true;
                break;
            }
        }
        return hasEmptySpaces;
    }

    public static boolean isEmpty(int place) {
        return place == -1;
    }

}
