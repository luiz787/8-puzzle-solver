package eightpuzzlesolver.heuristics;

import eightpuzzlesolver.Board;
import eightpuzzlesolver.Position;

public class SumOfManhattanDistancesHeuristic implements Heuristic {

    @Override
    public int evaluate(Board board) {
        int total = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Integer value = board.getState()[i][j];
                var expectedPosition = shouldBeAt(value);

                total += manhattanDistance(
                        Position.of(expectedPosition),
                        Position.of(i, j)
                );
            }
        }
        return total;
    }

    public int manhattanDistance(Position expected, Position actual) {
        int dx = Math.abs(expected.x() - actual.x());
        int dy = Math.abs(expected.y() - actual.y());

        return dx + dy;
    }


    public Integer[] shouldBeAt(Integer value) {
        if (value == null) {
            return new Integer[]{2, 2};
        }
        return new Integer[]{(value - 1) / 3, (value - 1) % 3};
    }
}
