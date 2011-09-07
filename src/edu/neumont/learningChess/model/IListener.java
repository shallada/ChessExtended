package edu.neumont.learningChess.model; 

import edu.neumont.learningChess.api.Location;

public interface IListener {
		public void movePiece(Move move, boolean capturePiece);
		public void placePiece(ChessPiece piece, Location location);
		public void removePiece(Location location);
	}