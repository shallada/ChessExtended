package edu.neumont.learningChess.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.json.Jsonizer;
import edu.neumont.learningChess.model.Bishop;
import edu.neumont.learningChess.model.ChessPiece;
import edu.neumont.learningChess.model.Knight;
import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;
import edu.neumont.learningChess.model.Player;
import edu.neumont.learningChess.model.PromotionListener;
import edu.neumont.learningChess.model.Queen;
import edu.neumont.learningChess.model.Rook;
import edu.neumont.learningChess.model.Team;

public class ServerPlayer extends Player {
	

	private PromotionListener promotionListener = null;
	
	private final String getMoveEndpoint;
	
	public static final boolean IS_LOCAL = false;

	private GameController gameController = null;
	
	public ServerPlayer(Team team, GameController game) {
		super(team);
		this.gameController = game;
		if(IS_LOCAL) {
			getMoveEndpoint = "http://localhost:8080/LearningChessWebServer/getmove";
		} else {
			getMoveEndpoint = "http://chess.neumont.edu:8081/ChessGame/getmove";
		}
		promotionListener = new PromotionListener(null);
	}
	
	

	@Override
	public Move getMove() {
		ExtendedMove extendedMoveFromServer = null;
//		MoveDescription mostRecentMoveDescription = gameController.getMostRecentMoveDescription();
		
		try {
			URL url = new URL(getMoveEndpoint);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			String jsonOut = Jsonizer.jsonize(gameController.getCurrentGameState());
			writer.write(jsonOut);
			writer.flush();
			
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			StringBuilder jsonStringBuilder = new StringBuilder();
			int bytesRead;
			 while ((bytesRead = in.read()) > -1) {
				 jsonStringBuilder.append((char)bytesRead);
			 }
			 extendedMoveFromServer = Jsonizer.dejsonize(jsonStringBuilder.toString(), ExtendedMove.class);
			 setPromotionPiece(extendedMoveFromServer.getPromotionPieceType());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return extendedMoveFromServer;
	}
	
	private void setPromotionPiece(PieceType pieceType){
		ChessPiece piece = null;
		if (pieceType != null) {
			switch (pieceType) {
			case QUEEN:
				piece = new Queen();
				break;
			case ROOK:
				piece = new Rook();
				break;
			case KNIGHT:
				piece = new Knight();
				break;
			case BISHOP:
				piece = new Bishop();
				break;
			}
		}
		promotionListener.setPromotionPiece(piece);
	}

	@Override
	public IPromotionListener getPromotionListener() {
		return promotionListener;
	}
}
