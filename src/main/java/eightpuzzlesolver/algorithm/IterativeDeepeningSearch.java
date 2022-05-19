package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.ArrayDeque;

public class IterativeDeepeningSearch implements Algorithm {

    @Override
    public Solution solve(Board initialState) {
        for (int depth = 0; ; depth++) {
            var result = depthLimitedSearch(initialState, depth);
            switch (result.type()) {
                case SOLUTION -> {
                    return result.solution();
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

    record DepthLimitedSearchResult(DepthLimitedSearchResultType type, Solution solution) {}

    private DepthLimitedSearchResult depthLimitedSearch(Board initialState, int maxDepth) {
        var stack = new ArrayDeque<Path>();
        stack.add(new Path(initialState, 0));

        var result = DepthLimitedSearchResultType.FAILURE;
        while (!stack.isEmpty()) {
            var current = stack.removeLast();
            if (current.currentBoard().numberOfPiecesOnWrongPlace() == 0) {
                return new DepthLimitedSearchResult(DepthLimitedSearchResultType.SOLUTION, new Solution(current.steps()));
            }
            if (current.steps() > maxDepth) {
                result = DepthLimitedSearchResultType.CUTOFF;
            } else {
                for (var move : current.currentBoard().possibleMoves()) {
                    var child = current.currentBoard().move(move);
                    stack.add(new Path(child, current.steps() + 1));
                }
            }
        }
        return new DepthLimitedSearchResult(result, null);
    }
}
