package com.gameboard.tictactoe.service;

import com.gameboard.tictactoe.IO.PlayerMove;
import com.gameboard.tictactoe.IO.TicTacToeIO;
import com.gameboard.tictactoe.exception.BoardUnavailableException;
import com.gameboard.tictactoe.exception.MovesNotAllowedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class TicTacToeService {

    public boolean boardHasEmptySpaces(int[] board) {

        boolean hasEmptySpaces = false;
        for (int j : board) {
            if (j == -1) {
                hasEmptySpaces = true;
                break;
            }
        }
        return hasEmptySpaces;
    }

    public void displayGameBoard(int[] board) {
        if (null != board && board.length != 0) {
            System.out.println((isEmpty(board[0]) ? " " : board[0]) + "|" + (isEmpty(board[1]) ? " " : board[1]) + "|" + (isEmpty(board[2]) ? " " : board[2]));
            System.out.println("-" + "+" + " " + "+" + "-");
            System.out.println((isEmpty(board[3]) ? " " : board[3]) + "|" + (isEmpty(board[4]) ? " " : board[4]) + "|" + (isEmpty(board[5]) ? " " : board[5]));
            System.out.println("-" + "+" + " " + "+" + "-");
            System.out.println((isEmpty(board[6]) ? " " : board[6]) + "|" + (isEmpty(board[7]) ? " " : board[7]) + "|" + (isEmpty(board[8]) ? " " : board[8]));
        } else {
            throw new BoardUnavailableException("Board is not available yet. Please create board to play.");
        }
    }

    private boolean isEmpty(int place) {
        return place == -1;
    }

    public boolean checkValidPosition(int[] board, PlayerMove playerMove) throws MovesNotAllowedException {
        boolean isValid = validatePosition(playerMove.getPosition(), board);
        if (!isValid) {
            throw new MovesNotAllowedException("The position entered is wrong. Please try again");
        }
        if(!(playerMove.getSymbol()==1 || playerMove.getSymbol()==0)){
            throw new MovesNotAllowedException("The symbol entered is wrong. Please try again");
        }
        board[playerMove.getPosition()] = playerMove.getSymbol();
        return checkWinner(board, playerMove.getSymbol());
    }

    public boolean computerMove(int[] board, PlayerMove playerMove) {

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
            if (playerMove.getSymbol() == 1) {
                board[emptySpaces[computerPosition]] = 0;
            } else {
                board[emptySpaces[computerPosition]] = 1;
            }
        } else {
            throw new MovesNotAllowedException("No empty position left.Match drawn.");
        }
        return checkWinner(board, 1);
    }

    public boolean checkWinner(int[] board, int symbol) {
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

    public int[] winnerBoard(int player, HttpSession httpSession,
                             PlayerMove playerMove, int[] board, TicTacToeIO ticTacToeIO, String gameType) {
        if (null == gameType || !gameType.equalsIgnoreCase("MULTIPLAYER")) {
            if (player == 1) {
                if (playerMove(player, httpSession, playerMove, board, ticTacToeIO))
                    return board;

            } else {
                ticTacToeIO.setErrorMessage("Only single player is playing");
                return board;
            }
            if (setComputerMove(httpSession, board, ticTacToeIO, playerMove)) return board;
        } else {
            if (setPlayerMoves(player, httpSession, playerMove, board, ticTacToeIO)) return board;
        }
        return board;

    }

    private boolean setPlayerMoves(int player, HttpSession httpSession, PlayerMove playerMove, int[] board, TicTacToeIO ticTacToeIO) {
        if (player == 1) {
            // check last session values
            // if unavailable, directly set the current session values
            // if available, validate, the player is not making wrong move, set current session

            validateLastMove(httpSession, playerMove);
            return playerMove(player, httpSession, playerMove, board, ticTacToeIO);

        } else if (player == 2) {
            validateLastMove(httpSession, playerMove);
            return playerMove(player, httpSession, playerMove, board, ticTacToeIO);
        } else {
            ticTacToeIO.setErrorMessage("Only 2 players are playing");
            return true;
        }
    }

    private void validateLastMove(HttpSession httpSession, PlayerMove playerMove) {
        if(null== httpSession.getAttribute("LastMove")){
            httpSession.setAttribute("LastMove", playerMove.getSymbol());
        }else{
            if(httpSession.getAttribute("LastMove").equals(playerMove.getSymbol())){
                throw new MovesNotAllowedException("You entered wrong symbol");
            }else{
                httpSession.setAttribute("LastMove", playerMove.getSymbol());
            }
        }
    }

    private boolean setComputerMove(HttpSession httpSession, int[] board, TicTacToeIO ticTacToeIO, PlayerMove playerMove) {
        if (computerMove(board, playerMove)) {
            System.out.println("Computer wins");
            ticTacToeIO.setWinner("CPU");
            httpSession.setAttribute("Winner", "CPU");
            return true;
        }
        return false;
    }

    private boolean playerMove(int player, HttpSession httpSession, PlayerMove playerMove, int[] board, TicTacToeIO ticTacToeIO) {
        if (checkValidPosition(board, playerMove)) {
            System.out.println("Player" + player + " wins");
            ticTacToeIO.setWinner(String.valueOf(player));
            httpSession.setAttribute("Winner", String.valueOf(player));
            return true;
        }
        return false;
    }

}
