package edu.neumont.learningChess.model;

import java.util.ArrayList;
import java.util.Iterator;


import edu.neumont.learningChess.api.Location;


public class AIPlayer extends Player {

	private static final int LOOK_AHEAD_DEPTH = 4;
	private ChessBoard board;
	private Team otherTeam;
	
	public AIPlayer(ChessBoard board, Team team, Team otherTeam) {
		super(team);
		this.board = board;
		this.otherTeam = otherTeam;
	}

	public class SearchResult {
		private Move move;
		private int value;
		public SearchResult(Move move, int value) {
			this.move = move;
			this.value = value;
		}
		public Move getMove() {
			return move;
		}
		public int getValue() {
			return value;
		}
	}
	
	@Override
	public Move getMove() {
		SearchResult result = findBestMove(team, otherTeam, LOOK_AHEAD_DEPTH);
		return result.getMove();
	}
	
	private SearchResult findBestMove(Team us, Team them, int depth) {
		ArrayList<SearchResult> results = null;
		if (depth > 0) {
			int bestValue = 0;
			// For each move on the team...
			for (Iterator<Move> i = us.getMoves(board); i.hasNext(); ) {
				Move move = i.next();
				// try the move
				board.tryMove(move);
				// find the best counter move
				SearchResult counter = findBestMove(them, us, depth-1);
				// undo the move
				board.undoTriedMove();
				// calculate the value of the move
				int moveValue = getValueOfLocation(move.getTo()) - ((counter == null)? 0: counter.getValue());
				// If the value is the best value,
				if ((results == null) || (moveValue > bestValue)) {
					// Save the move and update the best value
					SearchResult result = new SearchResult(move, moveValue);
					results = new ArrayList<SearchResult>();
					results.add(result);
					bestValue = moveValue;
				} else if (moveValue == bestValue) {
					SearchResult result = new SearchResult(move, moveValue);
					results.add(result);
				}
			}
		}
		return ((results == null) || (results.size() == 0))? null: results.get(SingletonRandom.nextInt(results.size()));
	}

	private int getValueOfLocation(Location location) {
		int pieceValue = board.hasPiece(location)? board.getPiece(location).getValue(): 0;
		return pieceValue;
	}

	@Override
	public Pawn.IPromotionListener getPromotionListener() {
		return new QueenPromoter();
	}
	
	public class QueenPromoter implements Pawn.IPromotionListener {

		@Override
		public ChessPiece getPromotionPiece(Location location) {
			return new Queen();
		}
	}
}
