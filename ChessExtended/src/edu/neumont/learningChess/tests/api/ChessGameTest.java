package edu.neumont.learningChess.tests.api;

import org.junit.Test;

import edu.neumont.learningChess.api.ChessGame;
import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.api.Location;
import edu.neumont.learningChess.api.PieceDescription;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.api.TeamColor;
import edu.neumont.learningChess.model.ChessBoard.MoveDescription;
import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;


import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChessGameTest {

	@Test
	public void testChessGame() {
		ChessGame game = new ChessGame();
		ChessGameState gameState = game.getGameState();
		assertEquals(gameState.getMovingTeamColor(), TeamColor.LIGHT);
		PieceDescription descrip = null;

		descrip = gameState.getPieceDescription(new Location(0, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 1));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 2));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 3));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 4));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 5));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 6));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 7));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		for (int i = 0; i < 8; i++) {
			descrip = gameState.getPieceDescription(new Location(1, i));
			assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		// SWITCH TO DARK

		descrip = gameState.getPieceDescription(new Location(7, 0));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 1));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 2));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 3));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 4));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 5));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 6));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 7));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		for (int i = 0; i < 8; i++) {
			descrip = gameState.getPieceDescription(new Location(6, i));
			assertTrue(descrip.getColor().equals(TeamColor.DARK));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		} // checks pawn move to

		assertTrue(gameState.getPawnMovedTwoLocation() == null);
	}

	@Test
	public void testChessGameChessGameState() {

		ChessGame game = new ChessGame(new ChessGame().getGameState());
		ChessGameState gameState = game.getGameState();

		PieceDescription descrip = null;

		descrip = gameState.getPieceDescription(new Location(0, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 1));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 2));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 3));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 4));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 5));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 6));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 7));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		for (int i = 0; i < 8; i++) {
			descrip = gameState.getPieceDescription(new Location(1, i));
			assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		// SWITCH TO DARK

		descrip = gameState.getPieceDescription(new Location(7, 0));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 1));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 2));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 3));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 4));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 5));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 6));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 7));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		for (int i = 0; i < 8; i++) {
			descrip = gameState.getPieceDescription(new Location(6, i));
			assertTrue(descrip.getColor().equals(TeamColor.DARK));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		// make pawn 2 move
		Move m = new Move(new Location(1, 0), new Location(3, 0));
		MoveDescription description = game.getMoveDescription(m,
				new Promoter(), true);

		game.makeMove(description);

		gameState = game.getGameState();

		// gameState.setPawnMovedTwoLocation(new Location(3, 0));

		//

		// recheck
		//
		descrip = gameState.getPieceDescription(new Location(0, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 1));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 2));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 3));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 4));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 5));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 6));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 7));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		// Check the pawn that moved 2
		descrip = gameState.getPieceDescription(new Location(3, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
		assertTrue(descrip.hasMoved());
		assertEquals(gameState.getPawnMovedTwoLocation(), new Location(3, 0));

		for (int j = 1; j < 8; j++) {
			descrip = gameState.getPieceDescription(new Location(1, j));
			assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		// SWITCH TO DARK

		descrip = gameState.getPieceDescription(new Location(7, 0));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 1));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 2));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 3));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 4));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 5));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 6));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 7));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		//

		for (int j = 0; j < 8; j++) {
			descrip = gameState.getPieceDescription(new Location(6, j));
			assertTrue(descrip.getColor().equals(TeamColor.DARK));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		assertTrue(!(gameState.getPawnMovedTwoLocation() == null));

		// }
	}

	@Test
	public void testGetGameState() {

		ChessGame game = new ChessGame();
		ChessGameState gameState = game.getGameState();

		assertEquals(gameState.getMovingTeamColor(), TeamColor.LIGHT);
		PieceDescription descrip = null;

		descrip = gameState.getPieceDescription(new Location(0, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 1));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 2));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 3));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 4));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 5));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 6));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 7));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		for (int i = 0; i < 8; i++) {
			descrip = gameState.getPieceDescription(new Location(1, i));
			assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		// SWITCH TO DARK

		descrip = gameState.getPieceDescription(new Location(7, 0));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 1));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 2));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 3));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 4));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 5));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 6));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 7));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		for (int i = 0; i < 8; i++) {
			descrip = gameState.getPieceDescription(new Location(6, i));
			assertTrue(descrip.getColor().equals(TeamColor.DARK));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		} // checks pawn move to

		assertTrue(gameState.getPawnMovedTwoLocation() == null);

	}

	@Test
	public void testGetMoveDescription() {

		ChessGame game = new ChessGame();
		Move m = new Move(new Location(1, 0), new Location(3, 0));
		MoveDescription description = game.getMoveDescription(m,
				new Promoter(), true);
		ChessGameState gameState = game.getGameState();
		game.makeMove(description);
		gameState = game.getGameState();
		PieceDescription descrip = null;

		descrip = gameState.getPieceDescription(new Location(0, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 1));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 2));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 3));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 4));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 5));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 6));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(0, 7));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		// Check the pawn that moved 2
		descrip = gameState.getPieceDescription(new Location(3, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
		assertTrue(descrip.hasMoved());
		assertEquals(gameState.getPawnMovedTwoLocation(), new Location(3, 0));

		for (int j = 1; j < 8; j++) {
			descrip = gameState.getPieceDescription(new Location(1, j));
			assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		// SWITCH TO DARK

		descrip = gameState.getPieceDescription(new Location(7, 0));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 1));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 2));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 3));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.QUEEN));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 4));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KING));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 5));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.BISHOP));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 6));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.KNIGHT));
		assertTrue(!descrip.hasMoved());

		descrip = gameState.getPieceDescription(new Location(7, 7));
		assertTrue(descrip.getColor().equals(TeamColor.DARK));
		assertTrue(descrip.getPieceType().equals(PieceType.ROOK));
		assertTrue(!descrip.hasMoved());

		//

		for (int j = 0; j < 8; j++) {
			descrip = gameState.getPieceDescription(new Location(6, j));
			assertTrue(descrip.getColor().equals(TeamColor.DARK));
			assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
			assertTrue(!descrip.hasMoved());
		}

		assertTrue(!(gameState.getPawnMovedTwoLocation() == null));
	}

	@Test
	public void testMakeMoveAndUnMakeMove() {
		ChessGame game = new ChessGame();
		game.makeMove(new MoveDescription(new Move(new Location(1, 0),
				new Location(3, 0)), PieceType.PAWN));
		PieceDescription descrip = game.getGameState().getPieceDescription(
				new Location(3, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
		assertTrue(descrip.hasMoved());
		game.unMakeMove();
		descrip = game.getGameState().getPieceDescription(new Location(3, 0));
		assertEquals(descrip, null);
		descrip = game.getGameState().getPieceDescription(new Location(1, 0));
		assertTrue(descrip.getColor().equals(TeamColor.LIGHT));
		assertTrue(descrip.getPieceType().equals(PieceType.PAWN));
		assertTrue(!descrip.hasMoved());
	}

	@Test
	public void testIsCheck() {
		ChessGame game = new ChessGame();
		game.makeMove(new MoveDescription(new Move(new Location('1', 'g'), new Location('3', 'f')), PieceType.PAWN));
		game.makeMove(new MoveDescription(new Move(new Location('8', 'b'), new Location('6', 'c')), PieceType.KNIGHT));
		game.makeMove(new MoveDescription(new Move(new Location('2', 'd'), new Location('4', 'd')), PieceType.PAWN));
		game.makeMove(new MoveDescription(new Move(new Location('7', 'd'), new Location('5', 'd')), PieceType.PAWN));
		game.makeMove(new MoveDescription(new Move(new Location('1', 'b'), new Location('3', 'c')), PieceType.KNIGHT));
		game.makeMove(new MoveDescription(new Move(new Location('6', 'c'), new Location('4', 'b')), PieceType.KNIGHT));
		game.makeMove(new MoveDescription(new Move(new Location('2', 'e'), new Location('4', 'e')), PieceType.PAWN));
		game.makeMove(new MoveDescription(new Move(new Location('4', 'b'), new Location('2', 'c')), PieceType.PAWN));
		
		assertTrue(game.isCheck());
		

	}

	@Test
	public void testIsCheckMate() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsStaleMate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPossibleMoves() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPossibleMovesLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGameHistory() {
		fail("Not yet implemented");
	}

}

class Promoter implements IPromotionListener {

	@Override
	public PieceType getPromotionPieceType() {
		// TODO Auto-generated method stub
		return PieceType.QUEEN;
	}

}
