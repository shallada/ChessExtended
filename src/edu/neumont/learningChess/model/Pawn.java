package edu.neumont.learningChess.model;

import java.util.ArrayList;
import java.util.Iterator;

import edu.neumont.learningChess.api.Location;

public class Pawn extends ChessPiece {

	public interface IPromotionListener {
		public ChessPiece getPromotionPiece(Location location);
	}

	public static final String NAME = "Pawn";
	private static final int WORTH = 1;
	private ChessPiece promotionPiece = null;

	private IPromotionListener promotionListener = null;

	public Pawn(IPromotionListener promotionListener) {
		super(WORTH);
		this.setPromotionListener(promotionListener);
	}

	public String getName() {
		return NAME;
	}

	public ChessPiece getPromotionPiece(Location location) {
		if (promotionPiece == null) {
			promotionPiece = getPromotionListener().getPromotionPiece(location);
		}
		return promotionPiece;
	}

	public Location nextStep() {
		int step = team.isWhite() ? 1 : -1;
		return new Location(location.getRow() + step, location.getColumn());
	}

	public Location longStep() {
		int step = team.isWhite() ? 2 : -2;
		return new Location(location.getRow() + step, location.getColumn());
	}

	public Location strikeLeft() {
		int step;
		int left;
		if (team.isWhite()) {
			step = 1;
			left = -1;
		} else {
			step = -1;
			left = 1;
		}
		return new Location(location.getRow() + step, location.getColumn() + left);
	}

	public Location strikeRight() {
		int step;
		int right;
		if (team.isWhite()) {
			step = 1;
			right = 1;
		} else {
			step = -1;
			right = -1;
		}
		return new Location(location.getRow() + step, location.getColumn() + right);
	}

	public Iterator<Location> getLegalMoves(ChessBoard board) {
		return new PawnMoves(board, this);
	}

	public void setPromotionListener(IPromotionListener promotionListener) {
		this.promotionListener = promotionListener;
	}

	public IPromotionListener getPromotionListener() {
		return promotionListener;
	}

	public class PawnMoves implements Iterator<Location> {

		private Iterator<Location> iter;

		public PawnMoves(ChessBoard board, Pawn pawn) {

			ArrayList<Location> moves = new ArrayList<Location>();
			boolean pawnIsWhite = pawn.getTeam().isWhite();

			if (ChessBoard.isInBounds(pawn.longStep()) &&
					(!pawn.hasMoved()) &&
					(!board.hasPiece(pawn.nextStep())) &&
					(!board.hasPiece(pawn.longStep()))) {
				moves.add(pawn.longStep());
			}
			if (ChessBoard.isInBounds(pawn.nextStep()) && !board.hasPiece(pawn.nextStep())) {
				moves.add(pawn.nextStep());
			}
			if (ChessBoard.isInBounds(pawn.strikeLeft()) && board.hasPiece(pawn.strikeLeft(), !pawnIsWhite)) {
				moves.add(pawn.strikeLeft());
			}
			if (ChessBoard.isInBounds(pawn.strikeRight()) && board.hasPiece(pawn.strikeRight(), !pawnIsWhite)) {
				moves.add(pawn.strikeRight());
			}
			Location enPassantTarget = getEnPassantTarget(board);
			if ((enPassantTarget != null) && (enPassantTarget.getRow() == pawn.getLocation().getRow())) {
				int row = pawn.getLocation().getRow() + (pawn.getTeam().isWhite() ? 1 : -1);
				moves.add(new Location(row, enPassantTarget.getColumn()));
			}
			iter = moves.iterator();
		}

		private Location getEnPassantTarget(ChessBoard board) {
			Location target = null;
			MoveDescription description = board.getMostRecentMoveDescription();
			if (description != null) {
				Move move = description.getMove();
				if ((board.getPiece(move.getTo()) instanceof Pawn) &&
						(Math.abs(move.getFrom().getRow() - move.getTo().getRow()) == 2)) {
					target = move.getTo();
				}
			}

			return target;
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public Location next() {
			return iter.next();
		}

		@Override
		public void remove() {
			throw new RuntimeException("Can't remove pawn moves");
		}

	}
}
