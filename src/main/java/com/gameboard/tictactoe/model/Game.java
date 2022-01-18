package com.gameboard.tictactoe.model;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    @Id
    private int id;

    @Column(name = "game_type")
    private String gameType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
