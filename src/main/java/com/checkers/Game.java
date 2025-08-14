package com.checkers;

import java.util.*;

public class Game {

    public static void main(String[] args) {
        Board b = new Board();
        b.initStartPosition();
        MoveGenerator gen = new MoveGenerator();
        Scanner sc = new Scanner(System.in);

        // Prompt for side choice
        Player humanPlayer = null;

        while (humanPlayer == null) {
            System.out.print("Choose your side (RED or BLACK): ");
            String sideInput = sc.nextLine();

            if (sideInput == null || sideInput.trim().isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            sideInput = sideInput.trim().toUpperCase();

            if (sideInput.equals("RED")) {
                humanPlayer = Player.RED;
            } else if (sideInput.equals("BLACK")) {
                humanPlayer = Player.BLACK;
            } else {
                System.out.println("Invalid choice. Please enter RED or BLACK.");
            }
        }

        Player computerPlayer = (humanPlayer == Player.RED) ? Player.BLACK : Player.RED;
        Player toMove = Player.RED; // Red always moves first

        int redCaptures = 0;
        int blackCaptures = 0;

        while (true) {
            BoardPrinter.print(b);
            System.out.println("Score -> Red: " + redCaptures + " | Black: " + blackCaptures);

            List<Move> legal = gen.generateAll(b, toMove, false);
            if (legal.isEmpty()) {
                System.out.println("No legal moves for " + toMove + ". " + toMove.opposite() + " wins!");
                break;
            }

            System.out.println(toMove + " to move. Legal moves:");
            for (int i = 0; i < legal.size(); i++) {
                System.out.println((i + 1) + ": " + legal.get(i));
            }

            Move chosen = null;

            if (toMove == humanPlayer) {
                // Human move
                System.out.print("Enter move (format 12-16 or 12x19x26) or index: ");
                String in = sc.nextLine();

                if (in == null || in.trim().isEmpty()) {
                    System.out.println("No move entered, please try again.");
                    continue;
                }
                in = in.trim();

                if (in.matches("\\d+")) {
                    int idx = Integer.parseInt(in) - 1;
                    if (idx >= 0 && idx < legal.size()) chosen = legal.get(idx);
                } else {
                    Move parsed = MoveParser.parse(in);
                    for (Move m : legal) {
                        if (m.path.equals(parsed.path)) {
                            chosen = m;
                            break;
                        }
                    }
                }

                if (chosen == null) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                // Computer move
                chosen = legal.get(new Random().nextInt(legal.size()));
                System.out.println("Computer chooses: " + chosen);
            }

            // Count captures in this move
            int capturedThisMove = 0;
            for (int i = 1; i < chosen.path.size(); i++) {
                int fromIdx = chosen.path.get(i - 1);
                int toIdx = chosen.path.get(i);
                int r1 = Board.rowOf(fromIdx), r2 = Board.rowOf(toIdx);
                if (Math.abs(r2 - r1) == 2) capturedThisMove++;
            }

            // Update score
            if (toMove == Player.RED) redCaptures += capturedThisMove;
            else blackCaptures += capturedThisMove;

            // Apply move
            gen.applyMove(b, chosen);

            // Check win by capture
            if (redCaptures == 12) {
                System.out.println("Red wins by capturing all black pieces!");
                break;
            }
            if (blackCaptures == 12) {
                System.out.println("Black (Computer) wins by capturing all red pieces!");
                break;
            }

            toMove = toMove.opposite();
        }

        sc.close();
    }
}
