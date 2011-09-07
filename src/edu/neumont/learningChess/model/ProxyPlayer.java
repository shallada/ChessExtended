package edu.neumont.learningChess.model;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class ProxyPlayer extends Player {

	private final Team team;
	private ChessPiece chessPiece;
	private PromotionListener promotionListener;
	private MoveHistory history;

	public ProxyPlayer(Team team) {
		super(team);
		this.team = team;
		promotionListener = new PromotionListener(null);
	}

	public void setPromotionPiece(ChessPiece chessPiece) {
		this.chessPiece = chessPiece;
		
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

	public IPromotionListener getPromotionListener() {
		return promotionListener;
	}

	public void setMoveHistory(MoveHistory history) {
		this.history = history;
	}
}