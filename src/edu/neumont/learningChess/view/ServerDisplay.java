package edu.neumont.learningChess.view;

import java.util.Stack;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.controller.HistoryAnalyzer;

public class ServerDisplay implements IDisplay {
	
	private final GameController gameController;
	private final HistoryAnalyzer learningEngine;
	private boolean isCheckmate = false;

	public ServerDisplay(GameController gameController, HistoryAnalyzer learningEngine) {
		this.gameController = gameController;
		this.learningEngine = learningEngine;
	}
	
	@Override
	public Piece removePiece(Location location) {
		return null;
	}

	@Override
	public void placePiece(Piece piece, Location location) {

	}

	@Override
	public void setVisible(boolean visible) {

	}

	@Override
	public void addMoveHandler(IMoveHandler handler) {

	}
	
	private Stack<ChessGameState> chessGameStates = new Stack<ChessGameState>();

	@Override
	public void promptForMove(boolean isWhite) {
		addCurrentGameState();
	}
	
	private void addCurrentGameState() {
		ChessGameState currentGameState = gameController.getCurrentGameState();
		chessGameStates.push(currentGameState);
	}

	@Override
	public void notifyCheckmate(boolean isWhite) {
		isCheckmate = true;
		addCurrentGameState();
		endOfGameAnalysis();
	}
	
	private void endOfGameAnalysis() {
		if(isCheckmate)
			learningEngine.analyzeStack(chessGameStates);
		else
			learningEngine.analyzeStaleStack(chessGameStates);
			
	}

	@Override
	public void notifyStalemate() {
		isCheckmate = false;
		addCurrentGameState();
		endOfGameAnalysis();
	}
	
	@Override
	public void notifyCheck(boolean isWhite) {

	}

	@Override
	public void dispose() {
		
	}

}
