package edu.neumont.learningChess.api;

import java.util.List;


// TODO this class is a list of move histories from a completed game
public class MoveHistory {
	private final List<ExtendedMove> moves;
	public MoveHistory(List<ExtendedMove> moves) {
		this.moves = moves;
		
	}
	public List<ExtendedMove> getMoves() {
		return moves;
	}

}
