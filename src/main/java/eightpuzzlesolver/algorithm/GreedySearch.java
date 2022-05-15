package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;

public class GreedySearch {
    public Solution solve(Board initialState, Function<Board, Integer> heuristicFunction) {
        var queue = new PriorityQueue<>(Comparator.comparing(Path::board, Comparator.comparing(heuristicFunction)));
        queue.add(new Path(initialState, 0));

        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current.board().numberOfPiecesOnWrongPlace() == 0) {
                return new Solution(current.steps());
            }
            for (var move : current.board().possibleMoves()) {
                var child = current.board().move(move);
                queue.add(new Path(child, current.steps() + 1));
            }
        }

        throw new IllegalStateException("No solution found");
    }
}
