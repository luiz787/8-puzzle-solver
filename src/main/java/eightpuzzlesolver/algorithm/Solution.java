package eightpuzzlesolver.algorithm;

import eightpuzzlesolver.Board;

import java.util.List;

public record Solution(List<Board> path, int steps, int totalExploredStates) {
}
