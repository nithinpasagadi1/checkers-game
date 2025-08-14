package com.checkers;

public class Piece {
    public final Player owner;
    public boolean king;

    public Piece(Player owner, boolean king) {
        this.owner = owner;
        this.king = king;
    }

    public Piece copy() { return new Piece(owner, king); }

    @Override
    public String toString() {
        if (owner == Player.RED) return king ? "R" : "r";
        else return king ? "B" : "b";
    }
}
