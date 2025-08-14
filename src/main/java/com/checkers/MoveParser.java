package com.checkers;
import java.util.*;
public class MoveParser {
    // Accepts "12-16" or "12x19x26"
    public static Move parse(String s) {
        s = s.trim();
        String sep = s.contains("x") ? "x" : "-";
        String[] parts = s.split("[x-]");
        List<Integer> path = new ArrayList<>();
        for (String p : parts) {
            path.add(Integer.parseInt(p.trim()));
        }
        return new Move(path);
    }
}

