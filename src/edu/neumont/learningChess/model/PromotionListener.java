package edu.neumont.learningChess.model;

import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class PromotionListener implements IPromotionListener {

	private ChessPiece promotionPieceType;

	public PromotionListener(ChessPiece promotionPieceType) {
		this.promotionPieceType = promotionPieceType;
	}

	@Override
	public ChessPiece getPromotionPiece(Location location) {
		return promotionPieceType;
	}
	
	public void setPromotionPiece(ChessPiece promotionPieceType) {
		this.promotionPieceType = promotionPieceType;
	}

}
