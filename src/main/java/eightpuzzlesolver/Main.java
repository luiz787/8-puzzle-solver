package eightpuzzlesolver;

import eightpuzzlesolver.algorithm.IterativeDeepeningSearch;
import eightpuzzlesolver.algorithm.Solution;

public class Main {
    public static void main(String[] args) {
        Integer[][] state = {
                {5, 8, 2},
                {1, null, 3},
                {4, 7, 6}
        };

        // var board = Board.fromInputString("1 2 3 4 0 5 6 7 8");
        var board = new Board(state);

        System.out.println(board);
        System.out.println("# of pieces in wrong place: " + board.numberOfPiecesOnWrongPlace());
        System.out.println("Sum of manhattan distances: " + board.sumOfManhattanDistances());

        System.out.println("\n\n");

        for (var possibleMove : board.possibleMoves()) {
            var newBoard = board.move(possibleMove);
            System.out.println(newBoard);
            System.out.println("# of pieces in wrong place: " + newBoard.numberOfPiecesOnWrongPlace());
            System.out.println("Sum of manhattan distances: " + newBoard.sumOfManhattanDistances());
            System.out.println("\n\n");
        }

        long start = System.currentTimeMillis();
        Solution solution = new IterativeDeepeningSearch().solve(board);
        System.out.println("[IDS] Steps: " + solution.steps());
        long end = System.currentTimeMillis();
        System.out.println("[IDS] Time in ms: " + (end - start));
    }
}
