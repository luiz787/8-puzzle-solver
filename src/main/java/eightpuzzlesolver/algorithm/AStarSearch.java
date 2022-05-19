package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;

public class AStarSearch implements Algorithm {

    private final Function<Board, Integer> heuristicFunction;

    public AStarSearch(Function<Board, Integer> heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }

    @Override
    public Solution solve(Board initialState) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparing((path -> evaluate(path, heuristicFunction))));
        queue.add(new Path(initialState, 0));

        Map<Board, Integer> scoreMap = new HashMap<>();
        scoreMap.put(initialState, 0);
        Set<Board> open = new HashSet<>();
        open.add(initialState);

        Set<Board> closed = new HashSet<>();
        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current.board().numberOfPiecesOnWrongPlace() == 0) {
                return new Solution(current.steps());
            }
            open.remove(current.board());
            scoreMap.remove(current.board());
            closed.add(current.board());

            for (var move : current.board().possibleMoves()) {
                var child = current.board().move(move);
                if (!closed.contains(child)) {
                    if (!open.contains(child)) {
                        scoreMap.put(child, current.steps() + 1);
                        queue.add(new Path(child, current.steps() + 1));
                        open.add(child);
                    } else {
                        if (current.steps() + 1 < scoreMap.get(child)) {
                            queue.remove(new Path(child, scoreMap.get(child)));
                            scoreMap.put(child, current.steps() + 1);
                            queue.add(new Path(child, current.steps() + 1));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }

    public int evaluate(Path path, Function<Board, Integer> heuristicFunction) {
        return path.steps() + heuristicFunction.apply(path.board());
    }
}
