package eightpuzzlesolver;

import eightpuzzlesolver.algorithm.AStarSearch;
import eightpuzzlesolver.algorithm.Solution;

public class Main {
    public static void main(String[] args) {
        Integer[][] state = {
                {5, 8, 2},
                {1, null, 3},
                {4, 7, 6}
        };

        var board = Board.fromInputString("0 8 7 5 4 2 1 6 3");
        //var board = new Board(state);

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
        Solution solution = new AStarSearch().solve(board, Board::sumOfManhattanDistances);
        System.out.println("[A*] Steps: " + solution.steps());
        long end = System.currentTimeMillis();
        System.out.println("[A*] Time in ms: " + (end - start));
    }
}
