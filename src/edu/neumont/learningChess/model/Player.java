package edu.neumont.learningChess.model;


public abstract class Player {
	
	protected Team team;
	
	protected static final boolean IS_LOCAL = false;
	
	public Player(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public abstract Move getMove();
	
	public abstract Pawn.IPromotionListener getPromotionListener();
	
	protected boolean isLegalMove(Move move, ChessBoard board, ICheckChecker checkChecker) {
		ChessPiece movingPiece = board.getPiece(move.getFrom());
		Team movingTeam = movingPiece.getTeam();
		return (movingTeam == team) && movingPiece.isLegalMove(board, move) && !causesCheckmate(move, board, checkChecker);
	}
	
	private boolean causesCheckmate(Move move, ChessBoard board, ICheckChecker checkChecker) {
		board.tryMove(move);
		boolean result = checkChecker.isInCheck(team);
		board.undoTriedMove();
		
		return result;
	}

	
}
