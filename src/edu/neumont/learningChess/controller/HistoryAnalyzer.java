package edu.neumont.learningChess.controller;

import java.util.Stack;
import edu.neumont.learningChess.api.ChessGameState;

public interface HistoryAnalyzer {
	public void analyzeStack(Stack<ChessGameState> gameStates);
	public void analyzeStaleStack(Stack<ChessGameState> gameStates);
}
