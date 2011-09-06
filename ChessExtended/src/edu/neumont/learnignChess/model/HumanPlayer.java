package edu.neumont.learnignChess.model;
import edu.neumont.learningChess.view.IDisplay;





public class HumanPlayer extends Player implements IDisplay.IMoveHandler {

	private ChessBoard board;
	private ICheckChecker checkChecker;
	private boolean awaitingMove = false;
	private Move awaitedMove = null;
	private Pawn.IPromotionListener promotionListener;
	
	public HumanPlayer(Team team, ChessBoard board, ICheckChecker checkChecker, Pawn.IPromotionListener promotionListener) {
		super(team);
		this.board = board;
		this.checkChecker = checkChecker;
		this.promotionListener = promotionListener;
	}
	
	@Override
	public synchronized Move getMove() {
		awaitingMove = true;
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		Move tempMove = awaitedMove;
		awaitedMove = null;
		awaitingMove = false;
		return tempMove;
	}

	@Override
	public synchronized boolean handleMove(Move move) {
		boolean canHandleMove = true;
		if (awaitingMove) {
			if (isLegalMove(move)) {
				canHandleMove = true;
				awaitedMove = move;
				notify();
			} else {
				canHandleMove = false;
			}
		}
		return canHandleMove;
	}

	public boolean isLegalMove(Move move) {
		ChessPiece movingPiece = board.getPiece(move.getFrom());
		Team movingTeam = movingPiece.getTeam();
		return (movingTeam == team) && movingPiece.isLegalMove(board, move) && !causesCheckmate(move);
	}
	
	private boolean causesCheckmate(Move move) {
		board.tryMove(move);
		boolean result = checkChecker.isInCheck(team);
		board.undoTriedMove();
		
		return result;
	}

	@Override
	public Pawn.IPromotionListener getPromotionListener() {
		return promotionListener;
	}
}
