package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        queue.add(new Path(List.of(initialState), 0));

        Map<Board, BestScoreContext> scoreMap = new HashMap<>();
        scoreMap.put(initialState, new BestScoreContext(0, List.of(initialState)));
        Set<Board> open = new HashSet<>();
        open.add(initialState);

        Set<Board> closed = new HashSet<>();
        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current.currentBoard().numberOfPiecesOnWrongPlace() == 0) {
                return new Solution(current.boards(), current.steps());
            }
            open.remove(current.currentBoard());
            scoreMap.remove(current.currentBoard());
            closed.add(current.currentBoard());

            for (var move : current.currentBoard().possibleMoves()) {
                var child = current.currentBoard().move(move);
                if (!closed.contains(child)) {
                    if (!open.contains(child)) {
                        Path newChildPath = current.addBoard(child);
                        scoreMap.put(child, new BestScoreContext(newChildPath));
                        queue.add(newChildPath);
                        open.add(child);
                    } else {
                        if (current.steps() + 1 < scoreMap.get(child).score()) {
                            queue.remove(new Path(scoreMap.get(child).path(), scoreMap.get(child).score()));
                            Path newChildPath = current.addBoard(child);
                            scoreMap.put(child, new BestScoreContext(newChildPath));
                            queue.add(newChildPath);
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }

    public int evaluate(Path path, Function<Board, Integer> heuristicFunction) {
        return path.steps() + heuristicFunction.apply(path.currentBoard());
    }
}
