package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class UniformCostSearch {
    public Solution solve(Board initialState) {
        var priorityQueue = new PriorityQueue<>(Comparator.comparing(Path::steps));
        priorityQueue.add(new Path(initialState, 0));

        Map<Board, Integer> scoreMap = new HashMap<>();
        scoreMap.put(initialState, 0);
        var explored = new HashSet<Board>();
        while (!priorityQueue.isEmpty()) {
            var current = priorityQueue.remove();
            if (current.board().numberOfPiecesOnWrongPlace() == 0) {
                System.out.println("[UCS] Explored states: " + explored.size());
                return new Solution(current.steps());
            }

            scoreMap.remove(current.board());
            explored.add(current.board());
            for (var move : current.board().possibleMoves()) {
                var child = current.board().move(move);

                var instancesOfChildInQueue = priorityQueue
                        .stream()
                        .filter(path -> path.board().equals(child)).toList();
                var stateAlreadyInQueue = scoreMap.get(child) != null;
                if (!explored.contains(child) && !stateAlreadyInQueue) {
                    scoreMap.put(child, current.steps() + 1);
                    priorityQueue.add(new Path(child, 1 + current.steps()));
                } else if (stateAlreadyInQueue) {
                    assert instancesOfChildInQueue.size() == 1;
                    var prevScore = scoreMap.get(child);
                    if (prevScore > 1 + current.steps()) {
                        priorityQueue.remove(new Path(child, prevScore));
                        priorityQueue.add(new Path(child, 1 + current.steps()));
                        scoreMap.put(child, current.steps() + 1);
                    }
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }
}
