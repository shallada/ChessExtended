package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;




public class Knight extends ChessPiece {

	public static final String NAME = "Knight";
	private static final int WORTH = 3;

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
	

	public Knight() {
		super(WORTH);
	}
	
	public String getName() {
		return NAME;
	}
	
	public Iterator<Location> getLegalMoves(ChessBoard board) {
		MoveIteration moves = new MoveIteration(board, location);
		moves.addAdjustments(knightAdjustments);
		return moves;
	}
}
