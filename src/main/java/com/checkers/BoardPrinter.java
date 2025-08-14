package com.checkers;
public class BoardPrinter {
    public static void print(Board b) {
        System.out.println("  a b c d e f g h");
        for (int r=0;r<8;r++) {
            StringBuilder line = new StringBuilder((8-r) + " ");
            for (int c=0;c<8;c++) {
                if ((r+c)%2==0) { line.append("  "); continue; } // light square
                int idx = Board.toIndex(r,c);
                Piece p = b.get(idx);
                if (p==null) line.append(String.format("%2d", idx));
                else line.append(" " + p.toString());
                if (c<7) line.append("");
            }
            System.out.println(line + " " + (8-r));
        }
        System.out.println("  a b c d e f g h\n");
    }
}

