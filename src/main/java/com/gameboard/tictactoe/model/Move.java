package com.gameboard.tictactoe.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "moves")
public class Move implements Serializable {
    @Column(name = "player")
    private int player;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    @Id
    private int gameId;


    @Column(name = "position")
    private int position;

    @Column(name = "symbol")
    private int symbol;

    @Column(name = "winner")
    private String winner;

    @Column(name = "last_move")
    private String lastMove;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }


}