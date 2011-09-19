package edu.neumont.learningChess.api;

import java.util.List;

import edu.neumont.learningChess.controller.GameController.PlayerType;

public class MoveHistory {
	private final List<ExtendedMove> moves;
	private String whitePlayerName;
	private String blackPlayerName;
	private PlayerType winnerType;

	public MoveHistory(List<ExtendedMove> moves) {
		this.moves = moves;

	}

	public List<ExtendedMove> getMoves() {
		return moves;
	}

	public int getMoveCount() {
		return moves.size();
	}

	/**
	 * @return the winnerType
	 */
	public PlayerType getWinnerType() {
		return winnerType;
	}

	/**
	 * @param winnerType the winnerType to set
	 */
	public void setWinnerType(PlayerType winnerType) {
		this.winnerType = winnerType;
	}

	/**
	 * @return the whitePlayerName
	 */
	public String getWhitePlayerName() {
		return whitePlayerName;
	}

	/**
	 * @param whitePlayerName the whitePlayerName to set
	 */
	public void setWhitePlayerName(String whitePlayerName) {
		this.whitePlayerName = whitePlayerName;
	}

	/**
	 * @return the blackPlayerName
	 */
	public String getBlackPlayerName() {
		return blackPlayerName;
	}

	/**
	 * @param blackPlayerName the blackPlayerName to set
	 */
	public void setBlackPlayerName(String blackPlayerName) {
		this.blackPlayerName = blackPlayerName;
	}
}
