package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.List;

record BestScoreContext(Integer score, List<Board> path) {
}
