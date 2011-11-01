package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;




public class Queen extends ChessPiece {

	public Iterator<Location> getLegalMoves(ChessBoard board) {
		MoveIteration moves = new MoveIteration(board, location);
		moves.addDiags();
		moves.addPerps();
		return moves;
	}
	
}
