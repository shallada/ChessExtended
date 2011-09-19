package edu.neumont.learningChess.model;

public enum WinnerType {
	Human(1), AI(2), LearningServer(3);
	private final int value;

	private WinnerType(int value) {
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	public int getValue() {
		return value;
	}
}
