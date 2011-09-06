package edu.neumont.learnignChess.model;

import java.util.Enumeration;

import edu.neumont.learningChess.api.Location;



public abstract class ChessPiece {

	protected Location location = null;
	protected Team team;
	protected int moveCount = 0;
	protected boolean hasMoved = false;
	protected int worth;
	
	public ChessPiece(int worth) {
		this.worth = worth;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean hasMoved() {
		return moveCount > 0;
	}

	public void incrementMoveCount() {
		moveCount++;
	}
	
	public void decrementMoveCount() {
		moveCount--;
	}
	
	public String getName() {
		return null;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public abstract Enumeration<Location> getLegalMoves(ChessBoard board);
	
	public boolean canAttack(ChessBoard board, Location target) {
		boolean attacks = false;
		for (Enumeration<Location> e = getLegalMoves(board); !attacks && e.hasMoreElements(); ) {
			Location location = e.nextElement();
			attacks = target.equals(location);
		}
		return attacks;
	}
	
	public boolean isLegalMove(ChessBoard board, Move move) {
		boolean isValid = false;
		
		for (Enumeration<Location> e = getLegalMoves(board); !isValid && e.hasMoreElements(); ) {
			Location there = e.nextElement();
			isValid = there.equals(move.getTo());
		}
		return isValid;
	}
	
	public int getValue() {
		return worth;
	}
}
