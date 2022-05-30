package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BreadthFirstSearch implements Algorithm {

    @Override
    public Solution solve(Board initialState) {
        if (initialState.isSolved()) {
            return new Solution(List.of(initialState), 0);
        }

        var queue = new ArrayDeque<Path>();
        queue.add(new Path(List.of(initialState), 0));

        var explored = new HashSet<Board>();
        while (!queue.isEmpty()) {
            var current = queue.removeFirst();
            explored.add(current.currentBoard());

            for (var move : current.currentBoard().possibleMoves()) {
                var child = current.currentBoard().move(move);
                if (!explored.contains(child)
                        && queue.stream().map(Path::currentBoard).noneMatch(board -> board.equals(child))) {
                    if (child.isSolved()) {
                        var resultPath = new ArrayList<>(current.boards());
                        resultPath.add(child);
                        return new Solution(resultPath, current.steps() + 1);
                    }
                    queue.add(current.addBoard(child));
                }
            }
        }

        throw new IllegalStateException("No solution found");
    }
}
