package edu.neumont.learningChess.model;

import java.util.ArrayList;
import java.util.Iterator;

import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class AIPlayer extends Player {

	private static int LOOK_AHEAD_DEPTH = 4;
	private ChessBoard board;
	private Team otherTeam;
	private ICheckChecker checkChecker;
	
	private static final int PAWN_VALUE = 1;
	private static final int ROOK_VALUE = 5;
	private static final int KNIGHT_VALUE = 3;
	private static final int BISHOP_VALUE = 3;
	private static final int QUEEN_VALUE = 9;
	private static final int KING_VALUE = 10000;

	public AIPlayer(ChessBoard board, Team team, Team otherTeam,
			ICheckChecker checkChecker) {
		super(team);
		this.board = board;
		this.otherTeam = otherTeam;
		this.checkChecker = checkChecker;
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
			for (Iterator<Move> i = us.getMoves(board); i.hasNext();) {
				Move move = i.next();
				if (isLegalMove(us, move, board, checkChecker)) {
					IPromotionListener pawnsListener = replacePawnPromotionListener(move.getFrom());
					// try the move
					board.tryMove(move);
					// find the best counter move
					SearchResult counter = findBestMove(them, us, depth - 1);
					// undo the move
					board.undoTriedMove();
					restorePawnPromotionListener(move.getFrom(), pawnsListener);
					// calculate the value of the move
					int moveValue = getValueOfLocation(move.getTo())
							- ((counter == null) ? 0 : counter.getValue());
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
		}
		return ((results == null) || (results.size() == 0)) ? null : results
				.get(SingletonRandom.nextInt(results.size()));
	}

	private IPromotionListener replacePawnPromotionListener(Location location) {
		IPromotionListener listener = null;
		ChessPiece piece = board.getPiece(location);
		if (piece != null && piece instanceof Pawn) {
			Pawn pawn = (Pawn) piece;
			listener = pawn.getPromotionListener();
			pawn.setPromotionListener(this.getPromotionListener());
		}
		return listener;
	}

	private void restorePawnPromotionListener(Location location,
			IPromotionListener pawnsListener) {
		if (pawnsListener != null) {
			Pawn pawn = (Pawn) board.getPiece(location);
			pawn.setPromotionListener(pawnsListener);
		}
	}

	private int getValueOfLocation(Location location) {
		int pieceValue = board.hasPiece(location) ? getPieceValue(board.getPiece(location)): 0;
		return pieceValue;
	}
	
	private int getPieceValue(ChessPiece piece) {
		int value = 0;
		if (piece instanceof Pawn) value = PAWN_VALUE;
		else if (piece instanceof Rook) value = ROOK_VALUE;
		else if (piece instanceof Knight) value = KNIGHT_VALUE;
		else if (piece instanceof Bishop) value = BISHOP_VALUE;
		else if (piece instanceof Queen) value = QUEEN_VALUE;
		else if (piece instanceof King) value = KING_VALUE;
		return value;
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

	public static void setDifficulty(int difficulty) {
		LOOK_AHEAD_DEPTH = difficulty;
	}
}
