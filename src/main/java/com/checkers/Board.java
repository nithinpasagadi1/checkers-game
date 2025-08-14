package com.checkers;
import java.util.*;

public class Board {
    // Use 1..32 indexing; index 0 unused for simplicity.
    private final Piece[] sq = new Piece[33];
    private static final int[] idxToRow = new int[33];
    private static final int[] idxToCol = new int[33];
    private static final int[][] rowColToIdx = new int[8][8];
    static {
        for (int r=0;r<8;r++) for (int c=0;c<8;c++) rowColToIdx[r][c] = -1;
        int idx=1;
        for (int r=0;r<8;r++){
            for (int c=0;c<8;c++){
                if ((r + c) % 2 == 1) {
                    idxToRow[idx] = r;
                    idxToCol[idx] = c;
                    rowColToIdx[r][c] = idx;
                    idx++;
                }
            }
        }
    }

    public Board() { /* empty */ }

    public Board copy() {
        Board b = new Board();
        for (int i=1;i<=32;i++) {
            if (this.sq[i] != null) b.sq[i] = this.sq[i].copy();
        }
        return b;
    }

    public Piece get(int idx) { return sq[idx]; }
    public void set(int idx, Piece p) { sq[idx] = p; }
    public void remove(int idx) { sq[idx] = null; }

    public static int rowOf(int idx) { return idxToRow[idx]; }
    public static int colOf(int idx) { return idxToCol[idx]; }
    public static int toIndex(int r,int c) {
        if(r<0||r>7||c<0||c>7) return -1;
        return rowColToIdx[r][c];
    }

    public void initStartPosition() {
        // Top 3 rows (indices 1..12) -> BLACK
        for (int i=1;i<=12;i++) set(i, new Piece(Player.BLACK, false));
        // Middle empty
        for (int i=13;i<=20;i++) remove(i);
        // Bottom 3 rows (21..32) -> RED
        for (int i=21;i<=32;i++) set(i, new Piece(Player.RED, false));
    }

    public boolean isPromotionSquare(int idx, Player p) {
        int r = rowOf(idx);
        if (p == Player.RED) return r == 0;    // RED moves up (toward row 0)
        else return r == 7;                    // BLACK moves down (toward row 7)
    }
}

