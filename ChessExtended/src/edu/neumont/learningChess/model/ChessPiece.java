package edu.neumont.learningChess.model;

import java.util.Enumeration;
import java.util.Iterator;

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
	
	public abstract Iterator<Location> getLegalMoves(ChessBoard board);
	
	public boolean canAttack(ChessBoard board, Location target) {
		boolean attacks = false;
		for (Iterator<Location> e = getLegalMoves(board); !attacks && e.hasNext(); ) {
			Location location = e.next();
			attacks = target.equals(location);
		}
		return attacks;
	}
	
	public boolean isLegalMove(ChessBoard board, Move move) {
		boolean isValid = false;
		
		for (Iterator<Location> e = getLegalMoves(board); !isValid && e.hasNext(); ) {
			Location there = e.next();
			isValid = there.equals(move.getTo());
		}
		return isValid;
	}
	
	public int getValue() {
		return worth;
	}
}
