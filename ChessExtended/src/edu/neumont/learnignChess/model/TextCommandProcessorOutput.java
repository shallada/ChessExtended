package edu.neumont.learnignChess.model;
import java.io.PrintStream;
import java.util.Hashtable;

import edu.neumont.learningChess.api.Location;


public class TextCommandProcessorOutput implements ChessCommandHandler {

	private static Hashtable<String, String> nameMap = new Hashtable<String, String>();
	static {
		nameMap.put("k", "King");
		nameMap.put("q", "Queen");
		nameMap.put("b", "Bishop");
		nameMap.put("n", "Knight");
		nameMap.put("r", "Rook");
		nameMap.put("p", "Pawn");
	}
	
	private PrintStream output;

	public TextCommandProcessorOutput(PrintStream output) {
		this.output = output;
	}
	
	@Override
	public void handleMovement(Location from, Location to, boolean takesPiece) {
		@SuppressWarnings("unused")
		int fromRow = from.getRow();
		@SuppressWarnings("unused")
		int fromColumn = from.getColumn();
		@SuppressWarnings("unused")
		int toRow = to.getRow();
		@SuppressWarnings("unused")
		int toColumn = to.getColumn();
		String ending = takesPiece? " and takes piece at location "+to: "";
		output.println("Move from location "+from+" to location "+to+ ending);
	}

	@Override
	public void handlePlacement(String pieceId, boolean isWhite, Location location) {
		String color = isWhite? "White": "Black";
		String name = nameMap.get(pieceId);
		@SuppressWarnings("unused")
		int row = location.getRow();
		@SuppressWarnings("unused")
		int column = location.getColumn();
		output.println("Place " + color + " " + name + " at location "+location);
	}

}
