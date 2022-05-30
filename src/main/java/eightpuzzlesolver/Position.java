package eightpuzzlesolver;

import java.util.Arrays;
import java.util.List;

public record Position(int x, int y) {

    public static Position of(Integer[] pos) {
        if (pos.length != 2) {
            throw new IllegalArgumentException("Position array should have 2 elements");
        }
        return new Position(pos[0], pos[1]);
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    Position move(Delta delta) {
        return new Position(x + delta.x(), y + delta.y());
    }

    boolean isValid() {
        return x >= 0 && x < 3
                && y >= 0 && y < 3;
    }

    List<Position> validNeighbors() {
        Delta[] deltas = {
                new Delta(-1, 0),
                new Delta(1, 0),
                new Delta(0, -1),
                new Delta(0, 1)
        };

        return Arrays.stream(deltas)
                .map(this::move)
                .filter(Position::isValid)
                .toList();
    }
}
