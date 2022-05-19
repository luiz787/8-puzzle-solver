package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.List;

public record Path(List<Board> board, int steps) {

    // FIXME bad constructor
    @Deprecated
    public Path(Board board, int steps) {
        this(List.of(board), steps);
    }

    public Board currentBoard() {
        return board.get(board.size() - 1);
    }
}
