package com.gameboard.tictactoe.controller;

import com.gameboard.tictactoe.IO.GameType;
import com.gameboard.tictactoe.IO.PlayerMove;
import com.gameboard.tictactoe.IO.TicTacToeIO;
import com.gameboard.tictactoe.model.Game;
import com.gameboard.tictactoe.service.TicTacToeService;
import com.gameboard.tictactoe.utilities.GameBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TicTacToeController {

    @Autowired
    private final TicTacToeService ticTacToeService;

    public TicTacToeController(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }

    @PostMapping(path = "/board")
    public TicTacToeIO createGameBoard(@RequestBody(required = false) GameType gameType) {
        return ticTacToeService.createGameBoard(gameType != null ? gameType.getGameType() : "SINGLEPLAYER");
    }

    @GetMapping(path = "/board/{sessionId}")
    public TicTacToeIO getGameBoard(@PathVariable("sessionId") int sessionId) {
        return ticTacToeService.getCurrentGameBoard(sessionId);
    }



    @PutMapping(path = "/board/{player}")
    public TicTacToeIO putPlayerSymbol(@PathVariable(value = "player") int player, HttpSession httpSession, @RequestBody PlayerMove playerMove) {
        int[] updatedBoard;
        String gameType1 = null;
        TicTacToeIO ticTacToeIO = new TicTacToeIO();
        int[] board = (int[]) httpSession.getAttribute("Board");
        GameType gameType = (GameType) httpSession.getAttribute("GameType");
        if (null != gameType) {
            gameType1 = gameType.getGameType();
        }
        if (null != board) {
            if (GameBoard.boardHasEmptySpaces(board)) {
                if (null == httpSession.getAttribute("Winner")) {
                    updatedBoard = ticTacToeService.winnerBoard(player, httpSession, playerMove, board, ticTacToeIO, gameType1);
                    ticTacToeIO.setBoard(board);
                } else {
                    ticTacToeIO.setErrorMessage("Game over! Winner is player " + httpSession.getAttribute("Winner"));
                    httpSession.setAttribute("Board", board);
                    return ticTacToeIO;
                }
            } else {
                ticTacToeIO.setErrorMessage("Match Drawn.You can play again.");
                httpSession.setAttribute("Board", board);
                return ticTacToeIO;
            }
        } else {
            ticTacToeIO.setErrorMessage("Board is not set");
            return ticTacToeIO;
        }

        httpSession.setAttribute("Board", updatedBoard);
        return ticTacToeIO;
    }


}