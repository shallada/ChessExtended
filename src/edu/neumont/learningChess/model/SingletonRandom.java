package edu.neumont.learningChess.model;
import java.util.Random;


public class SingletonRandom {

	public static Random random = new Random(31);
	
	public static int nextInt(int maxValue) {
		return random.nextInt(maxValue);
	}
}
