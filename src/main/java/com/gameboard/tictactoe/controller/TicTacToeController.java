package com.gameboard.tictactoe.controller;

import com.gameboard.tictactoe.IO.GameType;
import com.gameboard.tictactoe.IO.PlayerMove;
import com.gameboard.tictactoe.IO.TicTacToeIO;
import com.gameboard.tictactoe.service.TicTacToeService;
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
    public TicTacToeIO createGameBoard(HttpServletRequest request,@RequestBody(required = false) GameType gameType) {
        return ticTacToeService.createGameBoard(gameType != null ? gameType.getGameType() : "SINGLEPLAYER",request);
    }

    @GetMapping(path = "/board/sessionId/{sessionId}")
    public TicTacToeIO getGameBoard(HttpSession httpSession,@PathVariable("sessionId") int sessionId) {
        return ticTacToeService.getCurrentGameBoard(httpSession,sessionId);
    }

    @PutMapping(path = "/board/player/{player}/sessionId/{sessionId}")
    public TicTacToeIO putPlayerSymbol(@PathVariable(value = "player") int player,@PathVariable(value = "sessionId") int sessionId, HttpSession httpSession, @RequestBody PlayerMove playerMove) {
        return ticTacToeService.putPlayerSymbolAtPosition(player, sessionId, httpSession, playerMove);
    }

}