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
        this.state = state;
    }

    public Integer[][] getState() {
        return state;
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

        validateNewState(state);
        return new Board(state);
    }

    private static void validateNewState(Integer[][] s) {
        if (s.length != 3 || s[0].length != 3) {
            throw new IllegalArgumentException("State should be a 3x3 integer matrix");
        }
        if (Arrays.stream(s)
                .flatMap(Arrays::stream)
                .filter(Objects::isNull).count() != 1) {
            throw new IllegalArgumentException("State should have only 1 empty cell");
        }

        var frequencies = Arrays.stream(s)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (!frequencies.equals(EXPECTED_FREQUENCIES)) {
            throw new IllegalArgumentException("State should have unique numbers in the [1-8] range");
        }
    }

    public List<Move> possibleMoves() {
        Position emptyPosition = findEmptyPosition();

        var emptyPositionNeighbors = emptyPosition.validNeighbors();

        return emptyPositionNeighbors
                .stream()
                .map(neighbor -> new Move(neighbor, emptyPosition))
                .toList();
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

    public Board move(Move movement) {
        var newState = copyState();
        var tmp = newState[movement.positionToMove().x()][movement.positionToMove().y()];

        newState[movement.positionToMove().x()][movement.positionToMove().y()] = null;
        newState[movement.emptyPosition().x()][movement.emptyPosition().y()] = tmp;

        return new Board(newState);
    }

    private Integer[][] copyState() {
        Integer[][] newState = new Integer[3][3];
        for (int i = 0; i < state.length; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, newState[0].length);
        }
        return newState;
    }

    public boolean isSolved() {
        return numberOfPiecesOnWrongPlace() == 0;
    }

    public int numberOfPiecesOnWrongPlace() {
        int total = 0;

        for (int piece = 1; piece < 9; ++piece) {
            if (pieceIsInWrongPlace(piece)) {
                ++total;
            }
        }
        return total;
    }

    public boolean pieceIsInWrongPlace(int piece) {
        Position piecePosition = findPiecePosition(piece);
        var expectedRow = (piece - 1) / 3;
        var expectedColumn = (piece - 1) % 3;

        var expectedPosition = new Position(expectedRow, expectedColumn);
        return !expectedPosition.equals(piecePosition);
    }

    private Position findPiecePosition(int piece) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != null && state[i][j] == piece) {
                    return new Position(i, j);
                }
            }
        }
        throw new IllegalStateException("Should never happen");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.deepEquals(state, board.state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    private String printLine(Integer[] line) {
        return Arrays
                .stream(line)
                .map(num -> num == null ? " " : num.toString())
                .collect(Collectors.joining(" "));
    }
}
