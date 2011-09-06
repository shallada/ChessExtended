package edu.neumont.learningChess.api;

import edu.neumont.learningChess.model.Move;

public class ExtendedMove extends Move {

	public ExtendedMove(Location from, Location to) {
		super(from, to);
	}
	
	public ExtendedMove(Move moveCoordinates, PieceType promotionPieceType) {
		this(moveCoordinates.getFrom(), moveCoordinates.getTo());
		this.promotionPieceType = promotionPieceType;
	}

	public ExtendedMove(Move moveCoordinates) {
		this(moveCoordinates.getFrom(), moveCoordinates.getTo());
	}

	private PieceType promotionPieceType;

	/**
	 * @return the promotionPieceType
	 */
	public PieceType getPromotionPieceType() {
		return promotionPieceType;
	}

	/**
	 * @param promotionPieceType the promotionPieceType to set
	 */
	public void setPromotionPieceType(PieceType promotionPieceType) {
		this.promotionPieceType = promotionPieceType;
	}
}
