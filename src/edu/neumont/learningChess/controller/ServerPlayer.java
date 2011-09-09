package edu.neumont.learningChess.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.api.PieceType;
import edu.neumont.learningChess.json.Jsonizer;
import edu.neumont.learningChess.model.Bishop;
import edu.neumont.learningChess.model.ChessPiece;
import edu.neumont.learningChess.model.Knight;
import edu.neumont.learningChess.model.Move;
import edu.neumont.learningChess.model.MoveDescription;
import edu.neumont.learningChess.model.Pawn.IPromotionListener;
import edu.neumont.learningChess.model.Player;
import edu.neumont.learningChess.model.PromotionListener;
import edu.neumont.learningChess.model.Queen;
import edu.neumont.learningChess.model.Rook;
import edu.neumont.learningChess.model.Team;

public class ServerPlayer extends Player {
	

	private PromotionListener promotionListener = null;
	
	private final String getMoveEndpoint;
	
	private static final boolean IS_LOCAL = false;

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
	
	List<ExtendedMove> moveHistory = new ArrayList<ExtendedMove>();

	@Override
	public Move getMove() {
		ExtendedMove extendedMoveFromServer = null;
		MoveDescription mostRecentMoveDescription = gameController.getMostRecentMoveDescription();
		if(mostRecentMoveDescription != null)
			moveHistory.add(new ExtendedMove(mostRecentMoveDescription.getMove(), getPieceType(mostRecentMoveDescription.getPromotionPiece())));
		
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
		moveHistory.add(extendedMoveFromServer);
		return extendedMoveFromServer;
	}
	
	private void tellTheServer() {
		MoveHistory moveHistory = new MoveHistory(this.moveHistory);
		String endpoint;
		if(IS_LOCAL) {
			endpoint = "http://localhost:8080/LearningChessWebServer/analyzehistory";
		}
		else {
			endpoint = "http://chess.neumont.edu:8081/ChessGame/analyzehistory";
		}

		try {
			URL url = new URL(endpoint);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			String jsonOut = Jsonizer.jsonize(moveHistory);
			writer.write(jsonOut);
			writer.flush();
			int lengthFromClient = moveHistory.getMoves().size();
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			StringBuilder jsonStringBuilder = new StringBuilder();
			int bytesRead;
			while ((bytesRead = in.read()) > -1) {
				jsonStringBuilder.append((char)bytesRead);
			}
			int lengthFromServer = 0;
			try {
				lengthFromServer = Integer.parseInt(jsonStringBuilder.toString());
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
			if(lengthFromClient != lengthFromServer)
				throw new RuntimeException("Lengths are different!");
			else
				System.out.println("Lengths are the same");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void gameIsOver() {
		tellTheServer();
	}
	
	private PieceType getPieceType(ChessPiece promotionPiece) {
		return GameController.getPieceTypeFromChessPiece(promotionPiece);
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
