package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;




public class Bishop extends ChessPiece {

	public static final String NAME = "Bishop";
	private static final int WORTH = 3;
	

	public Bishop() {
		super(WORTH);
	}

	public String getName() {
		return NAME;
	}
	
	public Iterator<Location> getLegalMoves(ChessBoard board) {
		MoveIteration moves = new MoveIteration(board, location);
		moves.addDiags();
		return moves;
	}
}
