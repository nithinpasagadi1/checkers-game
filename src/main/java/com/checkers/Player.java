package com.checkers;
public enum Player { RED, BLACK;
    public Player opposite() { return this == RED ? BLACK : RED; }
}
