package edu.neumont.learningChess.api;



//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.Vector;
import edu.neumont.learningChess.model.*;

public class ChessGame {


	public ChessGame() {
	}

//	public ChessGame(ChessGameState gameState) {
//	}
//
	public ChessGameState getGameState() {
		ChessGameState gameState = new ChessGameState();
		
		for (LocationEnumeration locations = new LocationEnumeration(); locations.hasMoreElements(); ) {
			Location location = locations.nextElement();
		}
		return gameState;
	}

//	public MoveDescription getMoveDescription(Move move,
//			IPromotionListener promotionListener) {
//
//	}
//
//	public void makeMove(MoveDescription moveDescription) {
//	}
//
//	public void unMakeMove() {
//	}
//	
//	public void setGameState(ChessGameState gameState) {
//	}
//
//
//	public boolean isCheck() {
//	}
//
//	public boolean isCheckMate() {
//	}
//
//	public boolean isStaleMate() {
//	}
//
//	public Enumeration<Move> getPossibleMoves() {
//	}
//
//	public Enumeration<Move> getPossibleMoves(Location location) {
//	}
//
//	public Enumeration<ExtendedMove> getGameHistory() {
//	}
}
