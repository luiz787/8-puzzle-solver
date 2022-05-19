package eightpuzzlesolver;

import eightpuzzlesolver.algorithm.AStarSearch;
import eightpuzzlesolver.algorithm.BreadthFirstSearch;
import eightpuzzlesolver.algorithm.GreedySearch;
import eightpuzzlesolver.algorithm.IterativeDeepeningSearch;
import eightpuzzlesolver.algorithm.Solution;
import eightpuzzlesolver.algorithm.UniformCostSearch;

public class Main {
    public static void main(String[] args) {
        Integer[][] state = {
                {5, 8, 2},
                {1, null, 3},
                {4, 7, 6}
        };

        var board = Board.fromInputString("8 6 7 2 5 4 3 0 1");
        //var board = new Board(state);

        System.out.println(board);
        System.out.println("# of pieces in wrong place: " + board.numberOfPiecesOnWrongPlace());
        System.out.println("Sum of manhattan distances: " + board.sumOfManhattanDistances());

        long start = System.currentTimeMillis();
        Solution solution = new AStarSearch().solve(board, Board::numberOfPiecesOnWrongPlace);
        System.out.println("[A*] Steps: " + solution.steps());
        long end = System.currentTimeMillis();
        System.out.println("[A*] Time in ms: " + (end - start));
    }
}
