package eightpuzzlesolver.heuristics;

import eightpuzzlesolver.Board;

public interface Heuristic {
    int evaluate(Board board);
}
