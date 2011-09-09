package edu.neumont.learningChess.model;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class ProxyPlayer extends Player {

	private PromotionListener promotionListener;
	private MoveHistory history;

	public ProxyPlayer(Team team) {
		super(team);
		promotionListener = new PromotionListener(null);
	}
	
	@Override
	public Move getMove() {
		ExtendedMove move = history.getMoves().remove(0);
		promotionListener.setPromotionPiece(convertToChessPiece(move.getPromotionPieceType()));
		return move;
	}

	private ChessPiece convertToChessPiece(PieceType promotionPieceType) {
		return (promotionPieceType == PieceType.QUEEN) ? new Queen() : new Knight();
	}

	public PromotionListener getPromotionListener() {
		return promotionListener;
	}

	public void setMoveHistory(MoveHistory history) {
		this.history = history;
	}

	public void setPromotionPiece(PieceType promotionPieceType) {
		getPromotionListener().setPromotionPiece(this.convertToChessPiece(promotionPieceType));
	}
}