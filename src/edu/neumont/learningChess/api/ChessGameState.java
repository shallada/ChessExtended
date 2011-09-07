package edu.neumont.learningChess.api;



import java.util.Arrays;

public class ChessGameState {

	private Location pawnMovedTwoLocation;
	private TeamColor movingTeamColor;
	private static final int NUMBER_ROWS = 8;
	private static final int NUMBER_COLS = 8;
	private final PieceDescription[][] pieceDescriptions;

	public ChessGameState() {
		pieceDescriptions = new PieceDescription[NUMBER_ROWS][NUMBER_COLS];
	}

	public PieceDescription getPieceDescription(Location location) {
		return pieceDescriptions[location.getRow()][location.getColumn()];
	}

	public void setPieceDescription(Location location, PieceDescription pieceDescription) {
		pieceDescriptions[location.getRow()][location.getColumn()] = pieceDescription;
	}

	public TeamColor getMovingTeamColor() {
		return movingTeamColor;
	}

	public void setMovingTeamColor(TeamColor movingTeamColor) {
		this.movingTeamColor = movingTeamColor;
	}

	public Location getPawnMovedTwoLocation() {
		return pawnMovedTwoLocation;
	}

	public void setPawnMovedTwoLocation(Location pawnMovedTwoLocation) {
		this.pawnMovedTwoLocation = pawnMovedTwoLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((movingTeamColor == null) ? 0 : movingTeamColor.hashCode());
		result = prime * result + ((pawnMovedTwoLocation == null) ? 0 : pawnMovedTwoLocation.hashCode());
		result = prime * result + Arrays.hashCode(pieceDescriptions);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChessGameState other = (ChessGameState) obj;
		if (movingTeamColor != other.movingTeamColor) {
			return false;
		}
		if (pawnMovedTwoLocation == null) {
			if (other.pawnMovedTwoLocation != null) {
				return false;
			}
		} else if (!pawnMovedTwoLocation.equals(other.pawnMovedTwoLocation)) {
			return false;
		}
		if (!isEqual(pieceDescriptions, other.pieceDescriptions)) {
			return false;
		}
		return true;
	}

	private boolean isEqual(PieceDescription[][] myPieceDescriptions, PieceDescription[][] theirPieceDescriptions) {
		for (int i = 0; i < theirPieceDescriptions.length; i++) {
			for (int j = 0; j < theirPieceDescriptions[0].length; j++) {
				PieceDescription myPieceDescription = myPieceDescriptions[i][j];
				PieceDescription theirPieceDescription = theirPieceDescriptions[i][j];
				if (myPieceDescription == null) {
					if (theirPieceDescription != null)
						return false;
				} else {
					if (!myPieceDescription.equals(theirPieceDescription))
						return false;
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChessGameState [pawnMovedTwoLocation=");
		builder.append(pawnMovedTwoLocation);
		builder.append(", movingTeamColor=");
		builder.append(movingTeamColor);
		builder.append(", pieceDescriptions=");
		builder.append(toString(pieceDescriptions));
		builder.append("]");
		return builder.toString();
	}

	private String toString(PieceDescription[][] pieceDescriptions) {
		StringBuilder builder = new StringBuilder();
		for (int r = 0; r < pieceDescriptions.length; r++) {
			for (int c = 0; c < pieceDescriptions[0].length; c++) {
				PieceDescription pieceDescription = pieceDescriptions[r][c];
				if (pieceDescription != null) {
					StringBuilder pieceDescriptionSB = new StringBuilder();
					pieceDescriptionSB.append(pieceDescription.toString());
					pieceDescriptionSB.append("(");
					pieceDescriptionSB.append(r);
					pieceDescriptionSB.append(",");
					pieceDescriptionSB.append(c);
					pieceDescriptionSB.append(")");
					pieceDescriptionSB.append("\t");
					builder.append(pieceDescriptionSB.toString());
					builder.append("\r\n");
				} else {
//					builder.append("null ");
				}
			}
		}
		return builder.toString();
	}

}