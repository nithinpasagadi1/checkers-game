package com.checkers;

import java.util.*;

public class MoveGenerator {
    private static final int[][] DIRS = { {-1,-1}, {-1,1}, {1,-1}, {1,1} };

    // Generates all moves; set mandatoryCapture to false to allow quiet moves even if captures exist
    public List<Move> generateAll(Board board, Player player, boolean mandatoryCapture) {
        List<Move> captureMoves = new ArrayList<>();
        for (int i = 1; i <= 32; i++) {
            Piece p = board.get(i);
            if (p != null && p.owner == player) {
                generateCapturesFrom(board, i, List.of(i), captureMoves, p.king);
            }
        }

        List<Move> quietMoves = new ArrayList<>();
        for (int i = 1; i <= 32; i++) {
            Piece p = board.get(i);
            if (p != null && p.owner == player) {
                boolean isKing = p.king;
                for (int[] d : DIRS) {
                    int dr = d[0], dc = d[1];
                    if (!isKing) {
                        if (player == Player.RED && dr != -1) continue;
                        if (player == Player.BLACK && dr != 1) continue;
                    }
                    int r = Board.rowOf(i), c = Board.colOf(i);
                    int nr = r + dr, nc = c + dc;
                    int nIdx = Board.toIndex(nr, nc);
                    if (nIdx != -1 && board.get(nIdx) == null) {
                        quietMoves.add(new Move(List.of(i, nIdx)));
                    }
                }
            }
        }

        if (mandatoryCapture) {
            if (!captureMoves.isEmpty()) return captureMoves;
            return quietMoves;
        } else {
            List<Move> all = new ArrayList<>(quietMoves.size() + captureMoves.size());
            all.addAll(quietMoves);
            all.addAll(captureMoves);
            return all;
        }
    }

    // Overload without mandatoryCapture defaults to true (original behavior)
    public List<Move> generateAll(Board board, Player player) {
        return generateAll(board, player, true);
    }

    // Recursive method to generate capture sequences from a position
    private void generateCapturesFrom(Board board, int from, List<Integer> path, List<Move> results, boolean wasKing) {
        Piece original = board.get(from);
        if (original == null) return;
        boolean foundAtLeastOne = false;

        for (int[] d : DIRS) {
            int dr = d[0], dc = d[1];
            // Men capture forward only
            if (!wasKing) {
                if (original.owner == Player.RED && dr != -1) continue;
                if (original.owner == Player.BLACK && dr != 1) continue;
            }

            int r = Board.rowOf(from), c = Board.colOf(from);
            int midR = r + dr, midC = c + dc;
            int landR = r + 2 * dr, landC = c + 2 * dc;
            int midIdx = Board.toIndex(midR, midC);
            int landIdx = Board.toIndex(landR, landC);
            if (midIdx == -1 || landIdx == -1) continue;

            Piece midPiece = board.get(midIdx);
            if (midPiece == null || midPiece.owner == original.owner) continue;
            if (board.get(landIdx) != null) continue; // landing square must be empty

            // Simulate move on a copied board
            Board b2 = board.copy();
            Piece moving = b2.get(from);
            b2.remove(midIdx);
            b2.remove(from);
            b2.set(landIdx, moving);

            // Promotion check
            boolean promoted = false;
            if (!moving.king && b2.isPromotionSquare(landIdx, moving.owner)) {
                moving.king = true;
                promoted = true;
            }

            List<Integer> newPath = new ArrayList<>(path);
            newPath.add(landIdx);

            if (!promoted) {
                // Continue searching further jumps from landing square
                generateCapturesFrom(b2, landIdx, newPath, results, moving.king);
                foundAtLeastOne = true;
            } else {
                // Promotion ends capture chain
                results.add(new Move(newPath));
            }
        }

        // If no further captures found but this path is a capture sequence, add it
        if (!foundAtLeastOne && path.size() > 1) {
            results.add(new Move(path));
        }
    }

    // Apply a move to the board (mutates the board)
    public void applyMove(Board board, Move move) {
        List<Integer> path = move.path;
        int from = path.get(0);
        Piece moving = board.get(from);
        board.remove(from);

        for (int i = 1; i < path.size(); i++) {
            int to = path.get(i);

            // If move is a jump (row diff == 2), remove jumped piece
            int r1 = Board.rowOf(from), c1 = Board.colOf(from);
            int r2 = Board.rowOf(to), c2 = Board.colOf(to);
            if (Math.abs(r2 - r1) == 2) {
                int midR = (r1 + r2) / 2;
                int midC = (c1 + c2) / 2;
                int midIdx = Board.toIndex(midR, midC);
                board.remove(midIdx);
            }

            from = to;
        }

        // Place moving piece at final square and check promotion
        int toIdx = path.get(path.size() - 1);
        if (!moving.king && board.isPromotionSquare(toIdx, moving.owner)) {
            moving.king = true;
        }
        board.set(toIdx, moving);
    }
}
