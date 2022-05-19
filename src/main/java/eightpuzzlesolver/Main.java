package eightpuzzlesolver;

import eightpuzzlesolver.algorithm.AStarSearch;
import eightpuzzlesolver.algorithm.Algorithm;
import eightpuzzlesolver.algorithm.BreadthFirstSearch;
import eightpuzzlesolver.algorithm.GreedySearch;
import eightpuzzlesolver.algorithm.HillClimbingSearch;
import eightpuzzlesolver.algorithm.IterativeDeepeningSearch;
import eightpuzzlesolver.algorithm.Solution;
import eightpuzzlesolver.algorithm.UniformCostSearch;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length != 10 && args.length != 11) {
            usage();
            System.exit(1);
        }

        var boardString = Arrays
                .stream(args)
                .skip(1)
                .limit(9)
                .collect(Collectors.joining(" "));

        var shouldPrint = false;
        if (args.length == 11) {
            if ("PRINT".equals(args[10])) {
                shouldPrint = true;
            } else {
                usage();
                System.exit(1);
            }
        }

        var algorithm = getAlgorithm(args);

        if (algorithm == null) {
            usage();
            System.exit(1);
        }

        var board = Board.fromInputString(boardString);

        Solution solution = new AStarSearch(Board::numberOfPiecesOnWrongPlace).solve(board);
        System.out.println(args[0] + " Steps: " + solution.steps());
    }

    private static Algorithm getAlgorithm(String[] args) {
        switch (args[0]) {
            case "B":
                return new BreadthFirstSearch();
            case "I":
                return new IterativeDeepeningSearch();
            case "U":
                return new UniformCostSearch();
            case "A":
                return new AStarSearch(Board::sumOfManhattanDistances);
            case "G":
                return new GreedySearch(Board::sumOfManhattanDistances);
            case "H":
                return new HillClimbingSearch();
            default:
                usage();
                System.exit(1);
                return null;
        }
    }

    private static void usage() {
        System.out.println("usage: <program> <algorithm> <initial-state> <print>");
        System.out.println("<algorithm>:");
        System.out.println("  - B: Breadth-first search");
        System.out.println("  - I: Iterative deepening search");
        System.out.println("  - U: Uniform-cost search");
        System.out.println("  - A: A* search");
        System.out.println("  - G: Greedy best-first search");
        System.out.println("  - H: Hill Climbing search");
        System.out.println();
        System.out.println("<initial-state>: permutation of the numbers [0-8] describing the initial state");
        System.out.println("<print>: PRINT to print the sequence of states leading to the final solution (optional)");
    }
}
