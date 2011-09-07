package edu.neumont.learningChess.api;



public class PieceDescription {
	
	@Override
	public String toString() {
		return "PieceDescription [teamColor=" + teamColor + ", hasMoved="
				+ hasMoved + ", pieceType=" + pieceType + "]";
	}

	private final TeamColor teamColor;
	private final boolean hasMoved;
	private final PieceType pieceType;

	public PieceDescription(TeamColor teamColor, boolean hasMoved, PieceType pieceType) {
		this.teamColor = teamColor;
		this.hasMoved = hasMoved;
		this.pieceType = pieceType;
	}
	
	public TeamColor getColor() {
		return teamColor;
	}

	public boolean hasMoved() {
		return hasMoved;
	}
	
	public PieceType getPieceType() {
		return pieceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result *= prime + (hasMoved ? 1231 : 1237);
		result *= prime
				+ ((pieceType == null) ? 0 : pieceType.hashCode());
		result *= prime
				+ ((teamColor == null) ? 0 : teamColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PieceDescription other = (PieceDescription) obj;
		if (hasMoved != other.hasMoved)
			return false;
		if (pieceType != other.pieceType)
			return false;
		if (teamColor != other.teamColor)
			return false;
		return true;
	}
}
