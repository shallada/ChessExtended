package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;


public class Rook extends ChessPiece {

	public static final String NAME = "Rook";
	
	public String getName() {
		return NAME;
	}

	public Iterator<Location> getLegalMoves(ChessBoard board) {
		MoveIteration moves = new MoveIteration(board, location);
		moves.addPerps();
		return moves;
	}
}
