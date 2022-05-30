package eightpuzzlesolver.heuristics;

import eightpuzzlesolver.Board;

public class NumberOfPiecesInWrongPlaceHeuristic implements Heuristic {

    @Override
    public int evaluate(Board board) {
        return board.numberOfPiecesOnWrongPlace();
    }

}
