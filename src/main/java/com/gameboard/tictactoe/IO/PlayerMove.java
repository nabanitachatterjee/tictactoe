package com.gameboard.tictactoe.IO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerMove {

    private int position;
    private int symbol;
    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlayerMove{");
        sb.append("position=").append(position);
        sb.append(", symbol=").append(symbol);
        sb.append('}');
        return sb.toString();
    }
}
