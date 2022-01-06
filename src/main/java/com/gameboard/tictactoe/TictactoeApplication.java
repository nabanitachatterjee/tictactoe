package com.gameboard.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class TictactoeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TictactoeApplication.class, args);
        playGameV2();
    }

    private static void playGameV2() {


        int[] board = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

        displayGameBoard(board);
        System.out.println("##########################");
        int winner = -1;
        while (boardHasEmptySpaces(board)) {
            // play the game
            if (firstPlayerMove(board)) {
                winner = 0;
                break;
            }
            if (secondPlayerMove(board)) {
                winner = 1;
                break;
            }
            displayGameBoard(board);
            System.out.println("##########################");

        }

        displayGameBoard(board);
        System.out.println("##########################");

        if (winner != -1) {
            System.out.println("Winner is " + winner);
        } else {
            System.out.println("Match is drawn");
        }
    }

    private static void displayGameBoard(int[] board) {
        System.out.println((isEmpty(board[0]) ? " " : board[0]) + "|" + (isEmpty(board[1]) ? " " : board[1]) + "|" + (isEmpty(board[2]) ? " " : board[2]));
        System.out.println("-" + "+" + " " + "+" + "-");
        System.out.println((isEmpty(board[3]) ? " " : board[3]) + "|" + (isEmpty(board[4]) ? " " : board[4]) + "|" + (isEmpty(board[5]) ? " " : board[5]));
        System.out.println("-" + "+" + " " + "+" + "-");
        System.out.println((isEmpty(board[6]) ? " " : board[6]) + "|" + (isEmpty(board[7]) ? " " : board[7]) + "|" + (isEmpty(board[8]) ? " " : board[8]));
    }

    private static boolean isEmpty(int place) {
        return place == -1;
    }

    private static boolean boardHasEmptySpaces(int[] board) {
        boolean hasEmptySpaces = false;
        for (int j : board) {
            if (j == -1) {
                hasEmptySpaces = true;
                break;
            }
        }
        return hasEmptySpaces;
    }

    private static boolean firstPlayerMove(int[] board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter position");
        int playerPos = sc.nextInt();
        boolean isValid = validatePosition(playerPos, board);
        while (!isValid) {
            System.out.println("The position entered is wrong. Please try again");
            playerPos = sc.nextInt();
            isValid = validatePosition(playerPos, board);
        }
        board[playerPos] = 0;
        return checkWinner(board, 0);
    }

    private static boolean secondPlayerMove(int[] board) {

        int[] emptySpaces = new int[9];
        int j = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == -1) {
                emptySpaces[j] = i;
                j += 1;
            }
        }
        if (j != 0) {
            Random random = new Random();
            int computerPosition = random.nextInt(j);
            board[emptySpaces[computerPosition]] = 1;
        }
        return checkWinner(board, 1);
    }

    private static boolean checkWinner(int[] board, int symbol) {
        return symbol == board[0] && symbol == board[1] && symbol == board[2] ||
                symbol == board[0] && symbol == board[3] && symbol == board[6] ||
                symbol == board[0] && symbol == board[4] && symbol == board[8] ||
                symbol == board[1] && symbol == board[4] && symbol == board[7] ||
                symbol == board[2] && symbol == board[5] && symbol == board[8] ||
                symbol == board[2] && symbol == board[4] && symbol == board[6] ||
                symbol == board[3] && symbol == board[4] && symbol == board[5] ||
                symbol == board[6] && symbol == board[7] && symbol == board[8];
    }

    private static boolean validatePosition(int playerPos, int[] board) {
        if (playerPos > 8 || playerPos < 0) {
            return false;
        }
        return board[playerPos] == -1;
    }

    private static void playGame() {
        System.out.println("Welcome to the TIC TAC TOE game board");
        char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        createGameBoard(board);
        System.out.println();
        boolean gotWinner = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (gotWinner) {
                    break;
                }
                enterPlayerSymbol(board);
                createGameBoard(board);
                if (checkWinner(board, 'X')) {
                    gotWinner = true;
                    break;
                }
                System.out.println();
                enterComputerPosition(board);
                createGameBoard(board);
                if (checkWinner(board, '0')) {
                    gotWinner = true;
                    break;
                }
                System.out.println();
            }
        }
    }

    private static void enterPlayerSymbol(char[][] board) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter position");
        int playerPos = sc.nextInt();
        boolean isValid = isValidPosition(board, playerPos);
        while (!isValid) {
            System.out.println("Enter valid position");
            playerPos = sc.nextInt();
            isValid = isValidPosition(board, playerPos);
        }
        setSymbol(board, 'X', playerPos);
    }

    private static void enterComputerPosition(char[][] board) {
        Random random = new Random();
        int computerPosition = random.nextInt(9);
        boolean isValid = isValidPosition(board, computerPosition);
        while (!isValid) {
            computerPosition = random.nextInt(9);
            isValid = isValidPosition(board, computerPosition);
        }
        setSymbol(board, '0', computerPosition);
    }

    private static void createGameBoard(char[][] board) {
        System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println("-" + "+" + " " + "+" + "-");
        System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println("-" + "+" + " " + "+" + "-");
        System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
    }

    private static void setSymbol(char[][] arrayPosition, char symbol, int position) {
        switch (position) {
            case 0:
                arrayPosition[0][0] = symbol;
                break;
            case 1:
                arrayPosition[0][1] = symbol;
                break;
            case 2:

                arrayPosition[0][2] = symbol;

                break;
            case 3:

                arrayPosition[1][0] = symbol;
                break;
            case 4:
                arrayPosition[1][1] = symbol;

                break;
            case 5:

                arrayPosition[1][2] = symbol;
                break;
            case 6:
                arrayPosition[2][0] = symbol;
                break;
            case 7:
                arrayPosition[2][1] = symbol;
                break;
            case 8:
                arrayPosition[2][2] = symbol;
                break;
            default:
                System.out.println("Not correct Position");

        }


    }

    private static boolean isValidPosition(char[][] arrayPosition, int position) {
        switch (position) {
            case 0:
                if (arrayPosition[0][0] == ' ') {
                    return true;
                }
                break;
            case 1:
                if (arrayPosition[0][1] == ' ') {
                    return true;
                }
                break;
            case 2:
                if (arrayPosition[0][2] == ' ') {
                    return true;
                }
                break;
            case 3:
                if (arrayPosition[1][0] == ' ') {
                    return true;
                }
                break;
            case 4:
                if (arrayPosition[1][1] == ' ') {
                    return true;
                }
                break;
            case 5:
                if (arrayPosition[1][2] == ' ') {
                    return true;
                }
                break;
            case 6:
                if (arrayPosition[2][0] == ' ') {
                    return true;
                }
                break;
            case 7:
                if (arrayPosition[2][1] == ' ') {
                    return true;
                }
                break;
            case 8:
                if (arrayPosition[2][2] == ' ') {
                    return true;
                }
                break;
            default:
                System.out.println("Please enter correct Position");

        }
        return false;
    }

    private static boolean checkWinner(char[][] board, char symbol) {
        if (symbol == board[0][0] && symbol == board[0][1] && symbol == board[0][2] ||
                symbol == board[1][0] && symbol == board[1][1] && symbol == board[1][2] ||
                symbol == board[2][0] && symbol == board[2][1] && symbol == board[2][2] ||
                symbol == board[0][0] && symbol == board[1][0] && symbol == board[2][0] ||
                symbol == board[0][1] && symbol == board[1][1] && symbol == board[2][1] ||
                symbol == board[0][2] && symbol == board[1][2] && symbol == board[2][2] ||
                symbol == board[0][0] && symbol == board[1][1] && symbol == board[2][2] ||
                symbol == board[0][2] && symbol == board[1][1] && symbol == board[2][0]
        ) {
            System.out.println("Winner is :" + symbol);
            return true;
        }
        return false;
    }
}