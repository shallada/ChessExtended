package edu.neumont.learningChess.model;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.api.PieceType;

public class ProxyPlayer extends Player {

	private PromotionListener promotionListener;
	private MoveHistory history;
	private ICheckChecker checkChecker;

	public ProxyPlayer(Team team, ICheckChecker checkChecker) {
		super(team);
		this.checkChecker = checkChecker;
		promotionListener = new PromotionListener(new Queen());
	}
	
	@Override
	public Move getMove() {
		ExtendedMove move = history.getMoves().remove(0);
		setPromotionPiece(move.getPromotionPieceType());
		return move;
	}

	private ChessPiece getPromotionPiece(PieceType pieceType){
		if(pieceType == PieceType.KING || pieceType == PieceType.PAWN)
			throw new RuntimeException("Can't promote to king or pawn");
		ChessPiece piece = new Queen();
		if (pieceType != null) {
			piece = ChessPiece.getChessPieceFromPieceType(pieceType, null, checkChecker);
		}
		return piece;
	}
	
	public PromotionListener getPromotionListener() {
		return promotionListener;
	}

	public void setMoveHistory(MoveHistory history) {
		this.history = history;
	}

	public void setPromotionPiece(PieceType promotionPieceType) {
		getPromotionListener().setPromotionPiece(this.getPromotionPiece(promotionPieceType));
	}
}