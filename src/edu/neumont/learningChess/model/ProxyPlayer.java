package edu.neumont.learningChess.model;

import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class ProxyPlayer extends Player {

	private final Team team;
	private ChessPiece chessPiece;
	private IPromotionListener promotionListener;

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
		return null;
	}

	public IPromotionListener getPromotionListener() {
		return promotionListener;
	}
}