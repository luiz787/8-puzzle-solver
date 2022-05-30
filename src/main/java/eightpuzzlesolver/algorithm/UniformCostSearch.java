package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class UniformCostSearch implements Algorithm {

    @Override
    public Solution solve(Board initialState) {
        var priorityQueue = new PriorityQueue<>(Comparator.comparing(Path::steps));
        priorityQueue.add(new Path(List.of(initialState), 0));

        Map<Board, BestScoreContext> scoreMap = new HashMap<>();
        scoreMap.put(initialState, new BestScoreContext(0, List.of(initialState)));
        var explored = new HashSet<Board>();
        while (!priorityQueue.isEmpty()) {
            var current = priorityQueue.remove();
            if (current.currentBoard().isSolved()) {
                return new Solution(current.boards(), current.steps());
            }

            scoreMap.remove(current.currentBoard());
            explored.add(current.currentBoard());
            for (var move : current.currentBoard().possibleMoves()) {
                var child = current.currentBoard().move(move);

                var instancesOfChildInQueue = priorityQueue
                        .stream()
                        .filter(path -> path.currentBoard().equals(child)).toList();
                var stateAlreadyInQueue = scoreMap.get(child) != null;
                if (!explored.contains(child) && !stateAlreadyInQueue) {
                    Path newChildPath = current.addBoard(child);
                    scoreMap.put(child, new BestScoreContext(newChildPath));
                    priorityQueue.add(newChildPath);
                } else if (stateAlreadyInQueue) {
                    assert instancesOfChildInQueue.size() == 1;
                    var prevScore = scoreMap.get(child);
                    if (prevScore.score() > 1 + current.steps()) {
                        priorityQueue.remove(new Path(prevScore.path(), prevScore.score()));
                        Path newChildPath = current.addBoard(child);
                        priorityQueue.add(newChildPath);
                        scoreMap.put(child, new BestScoreContext(newChildPath));
                    }
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }
}
