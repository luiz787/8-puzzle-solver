package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.Comparator;
import java.util.List;

public class HillClimbingSearch implements Algorithm {

    private static final int MAX_LATERAL_MOVES = 10;

    @Override
    public Solution solve(Board initialState) {
        var current = new Path(List.of(initialState), 0);

        int currentStreakOfLateralMoves = 0;
        while (true) {
            var bestNeighbor = current
                    .currentBoard()
                    .possibleMoves()
                    .stream()
                    .map(current.currentBoard()::move)
                    .min(Comparator.comparing(Board::numberOfPiecesOnWrongPlace))
                    .orElseThrow(() -> new IllegalStateException("Should never happen"));

            if (bestNeighbor.numberOfPiecesOnWrongPlace() > current.currentBoard().numberOfPiecesOnWrongPlace()) {
                // Can't improve - best neighbor is worse than current
                return new Solution(current.boards(), current.steps());
            } else if (bestNeighbor.numberOfPiecesOnWrongPlace() == current.currentBoard().numberOfPiecesOnWrongPlace()) {
                // Best neighbor is equal than current, we might be on a shoulder. Side step if possible.
                if (currentStreakOfLateralMoves < MAX_LATERAL_MOVES) {
                    // Side-step
                    ++currentStreakOfLateralMoves;
                } else {
                    return new Solution(current.boards(), current.steps());
                }
            } else {
                currentStreakOfLateralMoves = 0;
            }
            current = current.addBoard(bestNeighbor);
        }
    }
}
