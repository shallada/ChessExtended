package edu.neumont.learningChess.controller;

import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.Player;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;
import edu.neumont.learningChess.model.Team;

public class ServerPlayer extends Player {
	
	public ServerPlayer(Team team) {
		super(team);
	}

	@Override
	public Move getMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPromotionListener getPromotionListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
