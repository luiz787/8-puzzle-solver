package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;
import eightpuzzlesolver.heuristics.Heuristic;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearch implements Algorithm {

    private final Heuristic heuristic;

    public AStarSearch(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Solution solve(Board initialState) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparing((this::evaluate)));
        queue.add(new Path(List.of(initialState), 0));

        Map<Board, BestScoreContext> scoreMap = new HashMap<>();
        scoreMap.put(initialState, new BestScoreContext(0, List.of(initialState)));
        Set<Board> open = new HashSet<>();
        open.add(initialState);

        Set<Board> closed = new HashSet<>();
        while (!queue.isEmpty()) {
            var current = queue.remove();
            if (current.currentBoard().isSolved()) {
                return new Solution(current.boards(), current.steps(), closed.size());
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

    public int evaluate(Path path) {
        return path.steps() + heuristic.evaluate(path.currentBoard());
    }
}
