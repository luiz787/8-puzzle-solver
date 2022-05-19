package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Collections;
import java.util.List;

public record Solution(List<Board> path, int steps) {

    // FIXME bad constructor
    @Deprecated
    public Solution(int steps) {
        this(Collections.emptyList(), steps);
    }
}
