package edu.neumont.learningChess.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import edu.neumont.learningChess.api.ChessGameState;
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
	private final String endpoint = "chess.neumont.edu:8081/ChessGame/getmove";
	public ServerPlayer(Team team) {
		super(team);
	}

	@Override
	public Move getMove() {
		try {
			URL url = new URL(endpoint);
			URLConnection connection = url.openConnection();
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			ChessGameState gameState = null;
			String jsonOut = Jsonizer.jsonize(gameState);
			writer.write(jsonOut);
			writer.flush();
			
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			StringBuilder jsonStringBuilder = new StringBuilder();
			int bytesRead;
			 while ((bytesRead = in.read()) > -1) {
				 jsonStringBuilder.append((char)bytesRead);
			 }
			 ExtendedMove extendedMoveFromServer = Jsonizer.dejsonize(jsonStringBuilder.toString(), ExtendedMove.class);
			 setPromotionPiece(extendedMoveFromServer.getPromotionPieceType());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setPromotionPiece(PieceType type){
		ChessPiece piece = null;
		switch (type) {
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
		promotionListener = new PromotionListener(piece);
	}

	@Override
	public IPromotionListener getPromotionListener() {
		return promotionListener;
	}

}
