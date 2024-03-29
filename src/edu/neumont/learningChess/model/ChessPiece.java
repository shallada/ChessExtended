package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public abstract class ChessPiece {

	protected Location location = null;
	protected Team team;
	protected int moveCount = 0;

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public boolean hasMoved() {
		return moveCount > 0;
	}

	public void incrementMoveCount() {
		moveCount++;
	}

	public void decrementMoveCount() {
		moveCount--;
	}

	public final String getName() {
		return this.getClass().getSimpleName();
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

	public abstract Iterator<Location> getLegalMoves(ChessBoard board);

	public boolean canAttack(ChessBoard board, Location target) {
		boolean attacks = false;
		for (Iterator<Location> e = getLegalMoves(board); !attacks && e.hasNext();) {
			Location location = e.next();
			attacks = target.equals(location);
		}
		return attacks;
	}

	public boolean isLegalMove(ChessBoard board, Move move) {
		boolean isValid = false;

		for (Iterator<Location> e = getLegalMoves(board); !isValid && e.hasNext();) {
			Location there = e.next();
			isValid = there.equals(move.getTo());
		}
		return isValid;
	}

	public static ChessPiece getChessPieceFromPieceType(PieceType pieceType,
			IPromotionListener promotionListener, ICheckChecker checkChecker) {
		ChessPiece chessPiece = null;

		switch (pieceType) {
		case KING:
			chessPiece = new King(checkChecker);
			break;
		case QUEEN:
			chessPiece = new Queen();
			break;
		case BISHOP:
			chessPiece = new Bishop();
			break;
		case KNIGHT:
			chessPiece = new Knight();
			break;
		case ROOK:
			chessPiece = new Rook();
			break;
		case PAWN:
			chessPiece = new Pawn(promotionListener);
			break;
		}

		return chessPiece;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String className = getClass().toString();
		className = className.substring(className.lastIndexOf("."));
		StringBuilder builder = new StringBuilder();
		builder.append("ChessPiece [location=");
		builder.append(location);
		builder.append(", Piece=");
		builder.append(className);
		builder.append(", team=");
		builder.append(team.isWhite() ? "white" : "black");
		builder.append(", moveCount=");
		builder.append(moveCount);
		builder.append("]");
		return builder.toString();
	}
}
