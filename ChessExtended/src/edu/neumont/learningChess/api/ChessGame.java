package edu.neumont.learningChess.api;



//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.Vector;
import java.util.Iterator;

import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.controller.GameController.PlayerType;
import edu.neumont.learningChess.model.ChessBoard.MoveDescription;
import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class ChessGame {

	
	private GameController gameController;

	public ChessGame(PlayerType whiteType, PlayerType blackType) {
		this.gameController = new GameController(whiteType, blackType);
	}
	
	public ChessGame(GameController gameController) {
		this.gameController = gameController;
	}

	public ChessGameState getGameState() {
		return gameController.getCurrentGameState();
	}

	public MoveDescription getMoveDescription(Move move,
			IPromotionListener promotionListener) {
		return gameController.getMostRecentMoveDescription();
	}
	
	public void makeMove(MoveDescription moveDescription) {
		gameController.tryMove(moveDescription);
	}

	public void unMakeMove() {
		gameController.untryMove();
	}
	
	public boolean isCheck() {
		return gameController.isInCheck(gameController.getCurrentTeam());
	}

	public boolean isCheckMate() {
		return gameController.isCheckmate();
	}

	public boolean isStaleMate() {
		return gameController.isStalemate();
	}
	
	public Iterator<Move> getPossibleMoves() {
		return gameController.getPossibleMovesForCurrentTeam();
	}
	
	public Iterator<Move> getPossibleMoves(Location location) {
		return gameController.getPossibleMoves(location);
	}
	
	public Iterator<ExtendedMove> getGameHistory() {
		return gameController.getGameHistory();
	}
}
