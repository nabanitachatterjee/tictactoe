package com.gameboard.tictactoe.IO;

public class GameType {
    private String gameType;
    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GameType{");
        sb.append("gameType='").append(gameType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
