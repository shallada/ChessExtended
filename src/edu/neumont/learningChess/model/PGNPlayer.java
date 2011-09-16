package edu.neumont.learningChess.model;

import java.util.ArrayList;
import java.util.Iterator;

import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.model.AIPlayer.SearchResult;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;

public class PGNPlayer extends Player {

	private PromotionListener promotionListener;
	private ChessBoard board;
	
	public PGNPlayer(Team team, ChessBoard board) {
		super(team);
		this.board = board;
		promotionListener = new PromotionListener(new Queen());
	}

	@Override
	public Move getMove() {
		for (Iterator<Move> i = team.getMoves(board); i.hasNext();) {
			Move move = i.next();
			}
		return null;
	}

	
	
	public PromotionListener getPromotionListener() {
		return promotionListener;
	}
	
	public void setPromotionPiece(PieceType promotionPieceType) {
		getPromotionListener().setPromotionPiece(this.convertToChessPiece(promotionPieceType));
	}
	
	private ChessPiece convertToChessPiece(PieceType promotionPieceType) {
		return (promotionPieceType == PieceType.QUEEN) ? new Queen() : new Knight();
	}
	
	

}
