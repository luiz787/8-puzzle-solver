package eightpuzzlesolver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {

    private static final Map<Integer, Long> EXPECTED_FREQUENCIES = Map.of(
            1, 1L,
            2, 1L,
            3, 1L,
            4, 1L,
            5, 1L,
            6, 1L,
            7, 1L,
            8, 1L
    );

    private final Integer[][] state;

    public Board(Integer[][] state) {
        if (state.length != 3 || state[0].length != 3) {
            throw new IllegalArgumentException("State should be a 3x3 integer matrix");
        }
        if (Arrays.stream(state)
                .flatMap(Arrays::stream)
                .filter(Objects::isNull).count() != 1) {
            throw new IllegalArgumentException("State should have only 1 empty cell");
        }

        var frequencies = Arrays.stream(state)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (!frequencies.equals(EXPECTED_FREQUENCIES)) {
            throw new IllegalArgumentException("State should have unique numbers in the [1-8] range");
        }

        this.state = state;
    }

    public static Board fromInputString(String inputString) {
        var numbers = Arrays.stream(inputString
                        .split(" "))
                .map(Integer::parseInt)
                .map(Board::mapZeroToNull)
                .toList();

        Integer[][] state = new Integer[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                state[i][j] = numbers.get(3 * i + j);
            }
        }

        return new Board(state);
    }

    public record Move(Position positionToMove, Position emptyPosition) {
    }

    public List<Move> possibleMoves() {
        Position emptyPosition = findEmptyPosition();

        var emptyPositionNeighbors = emptyPosition.validNeighbors();

        return emptyPositionNeighbors
                .stream()
                .map(neighbor -> new Move(neighbor, emptyPosition))
                .toList();
    }

    public Board move(Move movement) {
        var newState = copyState();
        var tmp = newState[movement.positionToMove.x()][movement.positionToMove.y()];

        newState[movement.positionToMove.x()][movement.positionToMove.y()] = null;
        newState[movement.emptyPosition.x()][movement.emptyPosition.y()] = tmp;

        return new Board(newState);
    }

    private Integer[][] copyState() {
        Integer[][] newState = new Integer[3][3];
        for (int i = 0; i < state.length; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, newState[0].length);
        }
        return newState;
    }

    private Position findEmptyPosition() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Objects.isNull(state[i][j])) {
                    return Position.of(i, j);
                }
            }
        }

        throw new IllegalStateException("Should never happen");
    }

    public int numberOfPiecesOnWrongPlace() {
        int total = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (!correctPosition(i, j)) {
                    total += 1;
                }
            }
        }
        return total;
    }

    record Delta(int x, int y) {}

    record Position(int x, int y) {

        static Position of(Integer[] pos) {
            if (pos.length != 2) {
                throw new IllegalArgumentException("Position array should have 2 elements");
            }
            return new Position(pos[0], pos[1]);
        }

        static Position of(int x, int y) {
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

    public int sumOfManhattanDistances() {
        int total = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Integer value = state[i][j];
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
        int dx = Math.abs(expected.x - actual.x);
        int dy = Math.abs(expected.y - actual.y);

        return dx + dy;
    }


    public Integer[] shouldBeAt(Integer value) {
        if (value == null) {
            return new Integer[]{2, 2};
        }
        return new Integer[]{(value - 1) / 3, (value - 1) % 3};
    }

    private boolean correctPosition(int i, int j) {
        var expectedValue = 3 * i + j + 1;
        if (expectedValue == 9) {
            return state[i][j] == null;
        }
        return Objects.equals(state[i][j], expectedValue);
    }

    public static Integer mapZeroToNull(Integer number) {
        return number == 0
                ? null
                : number;
    }

    @Override
    public String toString() {
        return Arrays
                .stream(state)
                .map(this::printLine)
                .collect(Collectors.joining("\n"));
    }

    private String printLine(Integer[] line) {
        return Arrays
                .stream(line)
                .map(num -> num == null ? " " : num.toString())
                .collect(Collectors.joining(" "));
    }
}
