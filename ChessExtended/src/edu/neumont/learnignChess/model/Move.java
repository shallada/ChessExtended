package edu.neumont.learnignChess.model;
import edu.neumont.learningChess.api.Location;


public class Move {

	private Location from;
	private Location to;
	
	public Move(Location from, Location to) {
		this.from = from;
		this.to = to;
	}
	
	public Location getFrom() {
		return from;
	}
	
	public Location getTo() {
		return to;
	}
	
	public String toString() {
		return from.toString() + " " + to.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
	
}
