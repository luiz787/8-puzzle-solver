package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class UniformCostSearch {
    public Solution solve(Board initialState) {
        var priorityQueue = new PriorityQueue<>(Comparator.comparing(Path::steps));
        priorityQueue.add(new Path(initialState, 0));

        var explored = new HashSet<Board>();
        while (!priorityQueue.isEmpty()) {
            var current = priorityQueue.remove();
            if (current.board().numberOfPiecesOnWrongPlace() == 0) {
                System.out.println("[UCS] Explored states: " + explored.size());
                return new Solution(current.steps());
            }

            explored.add(current.board());
            for (var move : current.board().possibleMoves()) {
                var child = current.board().move(move);

                var instancesOfChildInQueue = priorityQueue
                        .stream()
                        .filter(path -> path.board().equals(child)).toList();
                var stateAlreadyInQueue = !instancesOfChildInQueue.isEmpty();
                if (!explored.contains(child) && !stateAlreadyInQueue) {
                    priorityQueue.add(new Path(child, 1 + current.steps()));
                } else if (stateAlreadyInQueue) {
                    assert instancesOfChildInQueue.size() == 1;
                    var instance = instancesOfChildInQueue.get(0);
                    if (instance.steps() > 1 + current.steps()) {
                        priorityQueue.remove(instance);
                        priorityQueue.add(new Path(child, 1 + current.steps()));
                    }
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }
}
