# checkers-game
A text-based Java Checkers game playable against the computer, featuring scoring, kinging, and full game logic.
# Java Console Checkers Game

A fully-functional, turn-based **Checkers (Draughts)** game written in Java, where a human can play against a computer. This project is built with extensibility, clean design principles.

---

## Game Overview

Checkers is a two-player strategy game played on an 8x8 board using 12 pieces per player. Players alternate moves with the objective to **capture all opponent pieces** or **block them from making any legal move**.

In this Java implementation:
- The game runs in the **console**.
- Displays the board state using **text-based representation**.
- Automatically handles rules like **captures**, **kinging**, and **win detection**.

---

## Basic Rules of Checkers

| Rule | Description |
|------|-------------|
| **Board** | 8x8 grid, only dark squares (numbered 1–32) are used |
| **Pieces** | Each player starts with 12 pieces: Red and Black |
| **Movement** | Diagonal forward by 1 square |
| **Captures** | Jump over an opponent's piece diagonally; multiple jumps allowed |
| **Kinging** | Reaching the far row upgrades the piece to a King (can move backward) |
| **Winning** | Game ends when a player has no pieces or legal moves |
| **Capture Rule** | By default, captures are optional (configurable) |

---

## Gameplay Instructions

1. When the game starts, you'll be asked to choose a side (Red or Black).
2. Moves are entered in one of two formats:
   - Normal move: `12-16`
   - Capture move (multi-jump supported): `12x19x26`
3. The board will display after every move.
4. Captures are automatically counted.
5. The game ends when:
   - All opponent's pieces are captured
   - Opponent cannot make any legal move
   - A player reaches 12 captures

---

## Project Structure

checkers-game/
├── src/
│ ├── main/
│ │ └── java/com/checkers/
│ │ ├── Game.java # Entry point, main game loop
│ │ ├── Board.java # Board state, 32 playable squares
│ │ ├── Piece.java # Piece data (owner, king status)
│ │ ├── Player.java # Enum: RED / BLACK
│ │ ├── Move.java # Encapsulates move path
│ │ ├── MoveParser.java # Parses user input into Move objects
│ │ ├── MoveGenerator.java # Generates legal moves, captures, applies moves
│ │ ├── BoardPrinter.java # Pretty-prints the board to console
│ │ └── ComputerPlayer.java # Simple strategy for computer moves
└── pom.xml # Maven project config


