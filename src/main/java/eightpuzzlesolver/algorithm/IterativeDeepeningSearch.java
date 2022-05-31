package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.ArrayDeque;
import java.util.List;

public class IterativeDeepeningSearch implements Algorithm {

    @Override
    public Solution solve(Board initialState) {
        var totalVisitedAcrossIterations = 0;
        for (int depth = 0; ; depth++) {
            var result = depthLimitedSearch(initialState, depth);
            totalVisitedAcrossIterations += result.solution().totalExploredStates();
            switch (result.type()) {
                case SOLUTION -> {
                    return new Solution(result.solution().path(),
                            result.solution().steps(), totalVisitedAcrossIterations);
                }
                case FAILURE -> throw new IllegalStateException("No solution found");
            }
        }
    }

    enum DepthLimitedSearchResultType {
        SOLUTION,
        CUTOFF,
        FAILURE
    }

    record DepthLimitedSearchResult(DepthLimitedSearchResultType type, Solution solution) {
    }

    private DepthLimitedSearchResult depthLimitedSearch(Board initialState, int maxDepth) {
        var stack = new ArrayDeque<Path>();
        var totalVisited = 0;
        stack.add(new Path(List.of(initialState), 0));

        var result = DepthLimitedSearchResultType.FAILURE;
        while (!stack.isEmpty()) {
            var current = stack.removeLast();
            ++totalVisited;
            if (current.currentBoard().isSolved()) {
                return new DepthLimitedSearchResult(DepthLimitedSearchResultType.SOLUTION,
                        new Solution(current.boards(), current.steps(), totalVisited));
            }
            if (current.steps() > maxDepth) {
                result = DepthLimitedSearchResultType.CUTOFF;
            } else {
                for (var move : current.currentBoard().possibleMoves()) {
                    var child = current.currentBoard().move(move);
                    stack.add(current.addBoard(child));
                }
            }
        }
        return new DepthLimitedSearchResult(result, new Solution(null, -1, totalVisited));
    }
}
