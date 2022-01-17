package com.gameboard.tictactoe.service;

import com.gameboard.tictactoe.IO.PlayerMove;
import com.gameboard.tictactoe.IO.TicTacToeIO;
import com.gameboard.tictactoe.exception.BoardUnavailableException;
import com.gameboard.tictactoe.exception.MovesNotAllowedException;
import com.gameboard.tictactoe.model.Game;
import com.gameboard.tictactoe.model.Move;
import com.gameboard.tictactoe.repository.GameBoardRepository;
import com.gameboard.tictactoe.repository.MovesRepository;
import com.gameboard.tictactoe.utilities.GameBoard;
import liquibase.pro.packaged.M;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.gameboard.tictactoe.utilities.GameBoard.*;

@Service
public class TicTacToeService {

    private final GameBoardRepository gameBoardRepository;
    private final MovesRepository movesRepository;

    public TicTacToeService(GameBoardRepository gameBoardRepository, MovesRepository movesRepository) {
        this.gameBoardRepository = gameBoardRepository;
        this.movesRepository = movesRepository;
    }

    public TicTacToeIO createGameBoard(String gameType, HttpServletRequest request) {
        Game game = new Game();
        if ("MULTIPLAYER".equalsIgnoreCase(gameType)) {
            game.setGameType("MULTI");
        }
        Game savedGame = gameBoardRepository.save(game);
        TicTacToeIO response = new TicTacToeIO();
        response.setBoard(createFreshGameBoard());
        request.getSession().setAttribute("Board", response.getBoard());
        response.setSessionId(savedGame.getId());
        return response;
    }

    public TicTacToeIO getCurrentGameBoard(HttpSession httpSession, int sessionId) {
        TicTacToeIO response = new TicTacToeIO();
        int[] board = (int[]) httpSession.getAttribute("Board");
        if (null == board) {
            board = createFreshGameBoard();
        }
        Optional<Game> game = queryGamesForId(sessionId);
        if (game.isEmpty()) {
            throw new BoardUnavailableException("Board is not available. Please create board to play.");
        }

        Move move = queryMoveForSessionId(sessionId);
        checkWinner(response, move);
        if (response.getWinner() == null && !GameBoard.boardHasEmptySpaces(board)) {
            response.setErrorMessage("Match Drawn.You can play new game.");
        }
        response.setBoard(board);
        response.setSessionId(sessionId);
        return response;
    }

    private void checkWinner(TicTacToeIO response, Move move) {
        try {
            if (move.getWinner().equalsIgnoreCase("Y")) {
                response.setWinner(String.valueOf(move.getPlayer()));
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Move queryMoveForSessionId(int sessionId) {
        Move move = new Move();
        try {
            move = movesRepository.getById(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return move;
    }

    public boolean checkValidPositionAndSymbol(int[] board, PlayerMove playerMove, int player, int sessionId) throws MovesNotAllowedException {
        boolean isValid = validatePosition(playerMove.getPosition(), board);
        validateLastMove(playerMove, player, sessionId);
        if (!isValid) {
            throw new MovesNotAllowedException("The position entered is wrong. Please try again");
        }
        if (!(playerMove.getSymbol() == 1 || playerMove.getSymbol() == 0)) {
            throw new MovesNotAllowedException("The symbol entered is wrong. Please try again");
        }
        board[playerMove.getPosition()] = playerMove.getSymbol();
        addMoves(player, sessionId, playerMove);
        Move move = new Move();
        move.setGameId(sessionId);
        move.setWinner("N");
        move.setLastMove("Y");
        movesRepository.save(move);
        return checkWinner(board, playerMove.getSymbol());
    }

    public boolean computerMove(int[] board, PlayerMove playerMove, int sessionId) {
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
        addMoves(0, sessionId, playerMove);
        //    movesRepository.updateLastMove("N",sessionId,1);
        return checkWinner(board, 1);
    }

    public boolean checkWinner(int[] board, int symbol) {
        return symbol == board[0] && symbol == board[1] && symbol == board[2] || symbol == board[0] && symbol == board[3] && symbol == board[6] || symbol == board[0] && symbol == board[4] && symbol == board[8] || symbol == board[1] && symbol == board[4] && symbol == board[7] || symbol == board[2] && symbol == board[5] && symbol == board[8] || symbol == board[2] && symbol == board[4] && symbol == board[6] || symbol == board[3] && symbol == board[4] && symbol == board[5] || symbol == board[6] && symbol == board[7] && symbol == board[8];
    }

    private static boolean validatePosition(int playerPos, int[] board) {
        if (playerPos > 8 || playerPos < 0) {
            return false;
        }
        return board[playerPos] == -1;
    }

    public int[] currentBoard(int player, PlayerMove playerMove, int[] board, TicTacToeIO ticTacToeIO, String gameType, int sessionId) {
        if (null == gameType || !gameType.equalsIgnoreCase("MULTI")) {
            if (player == 1) {
                if (playerMove(player, playerMove, board, ticTacToeIO, sessionId)) {
                    //    movesRepository.updateWinner("Y", sessionId, player);
                    return board;
                }

            } else {
                ticTacToeIO.setErrorMessage("Only single player is playing");
                return board;
            }
            if (setComputerMove(board, ticTacToeIO, playerMove, sessionId)) {
                //         movesRepository.updateWinner("Y", sessionId, 0);
                return board;
            }
        } else {
            if (setPlayerMoves(player, playerMove, board, ticTacToeIO, sessionId)) {
                return board;
            }
        }
        return board;

    }

    private boolean setPlayerMoves(int player, PlayerMove playerMove, int[] board, TicTacToeIO ticTacToeIO, int sessionId) {
        if (player == 1 && playerMove(player, playerMove, board, ticTacToeIO, sessionId)) {
            addMoves(player, sessionId, playerMove);
            return true;

        } else if (player == 2 && playerMove(player, playerMove, board, ticTacToeIO, sessionId)) {
            addMoves(player, sessionId, playerMove);
            return true;
        } else {
            ticTacToeIO.setErrorMessage("Only 2 players are playing");
            return true;
        }
    }

    private void validateLastMove(PlayerMove playerMove, int player, int sessionId) {
        //query last move from moves for particular sessionid
        Move move = movesRepository.getById(sessionId);
       // if (optionalMove.isPresent()) {
            //Move move = optionalMove.get();
            if (move.getPlayer() != player && move.getSymbol() == playerMove.getSymbol()) {
                throw new MovesNotAllowedException("You entered wrong symbol");
            }
        //}
    }

    private boolean setComputerMove(int[] board, TicTacToeIO ticTacToeIO, PlayerMove playerMove, int sessionId) {
        if (computerMove(board, playerMove, sessionId)) {
            System.out.println("Computer wins");
            ticTacToeIO.setWinner("CPU");
            return true;
        }
        return false;
    }

    private boolean playerMove(int player, PlayerMove playerMove, int[] board, TicTacToeIO ticTacToeIO, int sessionId) {
        if (checkValidPositionAndSymbol(board, playerMove, player, sessionId)) {
            System.out.println("Player" + player + " wins");
            ticTacToeIO.setWinner(String.valueOf(player));
            return true;
        }
        return false;
    }

    public Optional<Game> queryGamesForId(int sessionId) {
        try {
            Game game = gameBoardRepository.getById(sessionId);
            game.getGameType();
            return Optional.of(game);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public TicTacToeIO putPlayerSymbolAtPosition(int player, int sessionId, HttpSession httpSession, PlayerMove playerMove) {
        int[] updatedBoard;
        TicTacToeIO ticTacToeIO = new TicTacToeIO();
        int[] board = (int[]) httpSession.getAttribute("Board");
        if (null == board) {
            board = createFreshGameBoard();
        }
        ticTacToeIO.setSessionId(sessionId);
        Optional<Game> optionalGame = queryGamesForId(sessionId);
        Game game;
        if (optionalGame.isPresent()) {
            game = optionalGame.get();
        } else {
            throw new BoardUnavailableException("Board is not yet available, please create a board to play");
        }
        Move move = queryMoveForSessionId(sessionId);
        checkWinner(ticTacToeIO, move);
        if (ticTacToeIO.getWinner() != null) {
            ticTacToeIO.setBoard(board);
            return ticTacToeIO;
        } else {
            if (GameBoard.boardHasEmptySpaces(board)) {
                if (!checkWinner(board, playerMove.getSymbol())) {
                    updatedBoard = currentBoard(player, playerMove, board, ticTacToeIO, game.getGameType(), sessionId);
                    ticTacToeIO.setBoard(board);
                } else {
                    ticTacToeIO.setErrorMessage("Game over! Winner is player " + ticTacToeIO.getWinner());
                    httpSession.setAttribute("Board", board);
                    return ticTacToeIO;
                }
            } else {
                ticTacToeIO.setErrorMessage("Match Drawn.You can play again.");
                httpSession.setAttribute("Board", board);
                return ticTacToeIO;
            }
        }
        httpSession.setAttribute("Board", updatedBoard);
        return ticTacToeIO;
    }

    private void addMoves(int player, int sessionId, PlayerMove playerMove) {
        Move move = new Move();
        move.setPlayer(player);
        move.setGameId(sessionId);
        move.setSymbol(playerMove.getSymbol());
        move.setPosition(playerMove.getPosition());
        move.setWinner("N");
        move.setLastMove("Y");
        movesRepository.save(move);
    }
}
