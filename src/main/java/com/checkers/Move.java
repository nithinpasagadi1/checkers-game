package com.checkers;

import java.util.List;
public class Move {
    public final List<Integer> path; // indices 1..32
    public Move(List<Integer> path) { this.path = List.copyOf(path); }
    public boolean isCapture() {
        return path.size() >= 2 && Math.abs(path.get(0) - path.get(1)) != 1; // heuristic; better to check row diff when applying
    }
    @Override public String toString() {
        String sep = path.size() > 2 ? "x" : "-";
        // choose 'x' if capture chain else '-' (parser knows actual)
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<path.size();i++){
            if (i>0) sb.append(sep);
            sb.append(path.get(i));
        }
        return sb.toString();
    }
}

