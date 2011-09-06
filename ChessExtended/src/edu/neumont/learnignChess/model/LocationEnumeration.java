package edu.neumont.learnignChess.model;


import java.util.Enumeration;

import edu.neumont.learningChess.api.Location;

public class LocationEnumeration implements Enumeration<Location> {

	private int row = 0;
	private int column = 0;
	@Override
	public boolean hasMoreElements() {
		return row < ChessBoard.NUMBER_OF_ROWS;
	}

	@Override
	public Location nextElement() {
		Location returnLocation = new Location(row, column);
		column++;
		if (column >= ChessBoard.NUMBER_OF_COLUMNS) {
			column = 0;
			row++;
		}
		return returnLocation;
	}

}
