package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.ArrayDeque;
import java.util.HashSet;

public class BreadthFirstSearch implements Algorithm {

    @Override
    public Solution solve(Board initialState) {
        if (initialState.numberOfPiecesOnWrongPlace() == 0) {
            return new Solution(0);
        }

        var queue = new ArrayDeque<Path>();
        queue.add(new Path(initialState, 0));

        var explored = new HashSet<Board>();
        while (!queue.isEmpty()) {
            var current = queue.removeFirst();
            explored.add(current.board());

            for (var move : current.board().possibleMoves()) {
                var child = current.board().move(move);
                if (!explored.contains(child)
                        && queue.stream().map(Path::board).noneMatch(board -> board.equals(child))) {
                    if (child.numberOfPiecesOnWrongPlace() == 0) {
                        System.out.println("[BFS] Explored states: " + explored.size());
                        return new Solution(current.steps() + 1);
                    }
                    queue.add(new Path(child, current.steps() + 1));
                }
            }
        }

        throw new IllegalStateException("No solution found");
    }
}
