package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;




public class Knight extends ChessPiece {

	private static Adjustment[] knightAdjustments = {
		new Adjustment(2,1),
		new Adjustment(2,-1),
		new Adjustment(-2,1),
		new Adjustment(-2,-1),
		new Adjustment(1,2),
		new Adjustment(-1,2),
		new Adjustment(1,-2),
		new Adjustment(-1,-2)
	};
	

	public Iterator<Location> getLegalMoves(ChessBoard board) {
		MoveIteration moves = new MoveIteration(board, location);
		moves.addAdjustments(knightAdjustments);
		return moves;
	}
}
