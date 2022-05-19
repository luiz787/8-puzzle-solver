package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.ArrayList;
import java.util.List;

public record Path(List<Board> boards, int steps) {

    // FIXME bad constructor
    @Deprecated
    public Path(Board board, int steps) {
        this(List.of(board), steps);
    }

    public Path addBoard(Board board) {
        var newList = new ArrayList<>(boards);
        newList.add(board);
        return new Path(newList, steps + 1);
    }

    public Board currentBoard() {
        return boards.get(boards.size() - 1);
    }
}
