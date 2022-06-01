#!/usr/bin/env bash

mkdir -p target

javac -d ./target src/main/java/eightpuzzlesolver/algorithm/Algorithm.java src/main/java/eightpuzzlesolver/algorithm/AStarSearch.java src/main/java/eightpuzzlesolver/algorithm/BestScoreContext.java src/main/java/eightpuzzlesolver/algorithm/BreadthFirstSearch.java src/main/java/eightpuzzlesolver/algorithm/GreedySearch.java src/main/java/eightpuzzlesolver/algorithm/HillClimbingSearch.java src/main/java/eightpuzzlesolver/algorithm/IterativeDeepeningSearch.java src/main/java/eightpuzzlesolver/algorithm/Path.java src/main/java/eightpuzzlesolver/algorithm/Solution.java src/main/java/eightpuzzlesolver/algorithm/UniformCostSearch.java src/main/java/eightpuzzlesolver/heuristics/Heuristic.java src/main/java/eightpuzzlesolver/heuristics/NumberOfPiecesInWrongPlaceHeuristic.java src/main/java/eightpuzzlesolver/heuristics/SumOfManhattanDistancesHeuristic.java src/main/java/eightpuzzlesolver/Board.java src/main/java/eightpuzzlesolver/Delta.java src/main/java/eightpuzzlesolver/Main.java src/main/java/eightpuzzlesolver/Move.java src/main/java/eightpuzzlesolver/Position.java

cd target || exit

jar cvfe tp1.jar eightpuzzlesolver.Main *
