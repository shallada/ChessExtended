package edu.neumont.learningChess.model;

import java.util.Enumeration;

import edu.neumont.learningChess.api.Location;


public class King extends ChessPiece {

	public static final String NAME = "King";
	private static final int WORTH = 10000;
	private static final int LEFT_ROOK_COLUMN = 0;
	private static final int RIGHT_ROOK_COLUMN = 7;
	
	private static Adjustment[] kingAdjustments = {
			new Adjustment(0,1),
			new Adjustment(1,1),
			new Adjustment(1,0),
			new Adjustment(1,-1),
			new Adjustment(0,-1),
			new Adjustment(-1,-1),
			new Adjustment(-1,0),
			new Adjustment(-1,1)
	};
	private ICheckChecker checkChecker;
	
	public King(ICheckChecker checkChecker) {
		super(WORTH);
		this.checkChecker = checkChecker;
	}
	
	public String getName() {
		return NAME;
	}

	public Enumeration<Location> getLegalMoves(ChessBoard board) {
		MoveEnumeration moves = new MoveEnumeration(board, location);
		moves.addAdjustments(kingAdjustments);
		if (!hasMoved()) {
			Move leftCastle = new Move(location, new Location(location.getRow(), location.getColumn() - 2));
			if (isLegalCastleMove(board, leftCastle)) {
				moves.addTarget(leftCastle.getTo());
			}
			Move rightCastle = new Move(location, new Location(location.getRow(), location.getColumn() + 2));
			if (isLegalCastleMove(board, rightCastle)) {
				moves.addTarget(rightCastle.getTo());
			}
		}
		return moves;
	}

	public Enumeration<Location> getLegalMovesSansCastling(ChessBoard board) {
		MoveEnumeration moves = new MoveEnumeration(board, location);
		moves.addAdjustments(kingAdjustments);
		return moves;
	}

	public boolean canAttack(ChessBoard board, Location target) {
		boolean attacks = false;
		for (Enumeration<Location> e = getLegalMovesSansCastling(board); !attacks && e.hasMoreElements(); ) {
			Location location = e.nextElement();
			attacks = target.equals(location);
		}
		return attacks;
	}
	
	private boolean isLegalCastleMove(ChessBoard board, Move move) {
		boolean moveIsLegal = false;
		if (!hasMoved() && !piecesInWay(board, move) && !rookHasMoved(board, move) && !checkChecker.isInCheck(team)) {
			int castlingDirection = getDirection(move);
			for (int step = 1; (!moveIsLegal) && (step < 2); step++) {
				Location next = new Location(move.getFrom().getRow(), move.getFrom().getColumn() + (step * castlingDirection));
				Move moveToPosition = new Move(move.getFrom(), next);
				board.tryMove(moveToPosition);
				moveIsLegal = !checkChecker.isInCheck(team);
				board.undoTriedMove();
			}
		}
		return moveIsLegal;
	}
	
	public boolean attemptingCastling(Move move) {
		return !hasMoved() && (Math.abs(move.getFrom().getColumn() - move.getTo().getColumn()) == 2);
	}
	
	private boolean piecesInWay(ChessBoard board, Move move) {
		boolean piecesFound = false;
		if (Math.abs(move.getFrom().getColumn() - move.getTo().getColumn()) > 0) {
			int direction = getDirection(move);
			Location rookLocation = getCastlingRookLocation(move);
			for (int col = move.getFrom().getColumn() + direction; !piecesFound && col != rookLocation.getColumn(); col += direction) {
				Location currentLocation = new Location(move.getFrom().getRow(), col);
				piecesFound = board.hasPiece(currentLocation);
			}
		}
		return piecesFound;
	}
	
	private boolean rookHasMoved(ChessBoard board, Move move) {
		Location rookLocation = getCastlingRookLocation(move);
		return !board.hasPiece(rookLocation) || board.getPiece(rookLocation).hasMoved();
	}
	
	public Location getCastlingRookLocation(Move move) {
		int rookColumn = (getDirection(move) < 0)? LEFT_ROOK_COLUMN: RIGHT_ROOK_COLUMN;
		Location rookLocation = new Location(move.getFrom().getRow(), rookColumn);
		return rookLocation;
	}
	
	private int getDirection(Move move) {
		return (move.getTo().getColumn() - move.getFrom().getColumn()) / Math.abs(move.getTo().getColumn() - move.getFrom().getColumn());
	}
}
