#!/usr/bin/env bash

mkdir -p target

javac -d ./target src/main/java/eightpuzzlesolver/algorithm/Algorithm.java src/main/java/eightpuzzlesolver/algorithm/AStarSearch.java src/main/java/eightpuzzlesolver/algorithm/BestScoreContext.java src/main/java/eightpuzzlesolver/algorithm/BreadthFirstSearch.java src/main/java/eightpuzzlesolver/algorithm/GreedySearch.java src/main/java/eightpuzzlesolver/algorithm/HillClimbingSearch.java src/main/java/eightpuzzlesolver/algorithm/IterativeDeepeningSearch.java src/main/java/eightpuzzlesolver/algorithm/Path.java src/main/java/eightpuzzlesolver/algorithm/Solution.java src/main/java/eightpuzzlesolver/algorithm/UniformCostSearch.java src/main/java/eightpuzzlesolver/Board.java src/main/java/eightpuzzlesolver/Game.java src/main/java/eightpuzzlesolver/Main.java

cd target || exit

jar cvfe tp1.jar eightpuzzlesolver.Main *
