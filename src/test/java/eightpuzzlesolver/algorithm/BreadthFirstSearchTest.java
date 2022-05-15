package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstSearchTest {
    @ParameterizedTest
    @MethodSource
    void testBfsFindsOptimalSolution(String boardString, int expectedSteps) {
        assertEquals(expectedSteps, new BreadthFirstSearch().solve(Board.fromInputString(boardString)).steps());
    }

    static Stream<Arguments> testBfsFindsOptimalSolution() {
        return Stream.of(
                Arguments.of("1 2 3 4 5 6 7 8 0", 0),
                Arguments.of("1 2 3 4 5 6 7 0 8", 1),
                Arguments.of("1 2 3 4 0 5 7 8 6", 2),
                Arguments.of("1 0 3 4 2 5 7 8 6", 3),
                Arguments.of("1 5 2 4 0 3 7 8 6", 4),
                Arguments.of("1 5 2 0 4 3 7 8 6", 5),
                Arguments.of("1 5 2 4 8 3 7 6 0", 6),
                Arguments.of("1 5 2 4 8 0 7 6 3", 7),
                Arguments.of("0 5 2 1 8 3 4 7 6", 8),
                Arguments.of("1 0 2 8 5 3 4 7 6", 9),
                Arguments.of("5 8 2 1 0 3 4 7 6", 10),
                Arguments.of("5 8 2 1 7 3 4 0 6", 11),
                Arguments.of("5 8 2 1 7 3 0 4 6", 12),
                Arguments.of("5 8 2 0 7 3 1 4 6", 13),
                Arguments.of("5 8 2 7 0 3 1 4 6", 14),
                Arguments.of("8 0 2 5 7 3 1 4 6", 15),
                Arguments.of("8 7 2 5 0 3 1 4 6", 16),
                Arguments.of("5 0 8 7 3 2 1 4 6", 17),
                Arguments.of("8 7 2 5 4 3 1 6 0", 18),
                Arguments.of("8 0 7 5 3 2 1 4 6", 19),
                Arguments.of("8 7 0 5 4 2 1 6 3", 20),
                Arguments.of("8 0 7 5 4 2 1 6 3", 21),
                Arguments.of("0 8 7 5 4 2 1 6 3", 22),
                Arguments.of("8 4 7 5 6 2 1 0 3", 23),
                Arguments.of("8 4 7 5 6 2 1 3 0", 24),
                Arguments.of("8 4 7 5 6 0 1 3 2", 25),
                Arguments.of("0 4 7 8 6 2 5 1 3", 26),
                Arguments.of("8 0 7 5 4 6 1 3 2", 27),
                Arguments.of("8 4 7 6 2 3 5 1 0", 28),
                Arguments.of("8 0 6 5 7 3 1 2 4", 29),
                Arguments.of("0 8 7 5 6 4 1 2 3", 30),
                Arguments.of("8 6 7 2 5 4 3 0 1", 31)
        );
    }
}