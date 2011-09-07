package edu.neumont.learningChess.model;

import java.util.Iterator;

import edu.neumont.learningChess.api.Location;

public class LocationIterator implements Iterator<Location> {

	private int row = 0;
	private int column = 0;

	@Override
	public boolean hasNext() {
		return row < ChessBoard.NUMBER_OF_ROWS;
	}

	@Override
	public Location next() {
		Location returnLocation = new Location(row, column);
		column++;
		if (column >= ChessBoard.NUMBER_OF_COLUMNS) {
			column = 0;
			row++;
		}
		return returnLocation;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
	}
}
