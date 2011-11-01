import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.json.Jsonizer;
import edu.neumont.learningChess.model.AIPlayer;
import edu.neumont.learningChess.model.ServerPlayer;
import edu.neumont.learningChess.view.BoardDisplay;

public class AIClient {

	static GameController.PlayerType white = null;
	static GameController.PlayerType black = null;

	public static void main(String[] args) {
		BoardDisplay.setSHOW_ALERT(false);
		final int argumentsLength = args.length;
		boolean giveInfo = true;

		if (argumentsLength > 1 && argumentsLength <= 6) {
			giveInfo = false;
			try {
				if (argumentsLength >= 5) {
					white = GameController.PlayerType.valueOf(args[2]);
					black = GameController.PlayerType.valueOf(args[3]);
				} else {
					white = GameController.PlayerType.AI;
					black = GameController.PlayerType.LearningServer;
				}
				if (argumentsLength == 4 || argumentsLength == 6) {
					AIPlayer.setDifficulty(Integer.parseInt(args[argumentsLength - 2]));
				}
				if (argumentsLength >= 3) {
					GameController.setShowBoard(Boolean.parseBoolean(args[1]));
				} else {
					GameController.setShowBoard(true);
				}
				final long iterations = Long.parseLong(args[0]);
				for (int j = 0; j < Integer.parseInt(args[args.length - 1]); j++) {
					new Thread() {
						@Override
						public void run() {
							for (int i = 0; i < iterations; i++) {
								setupRunClose();
							}
						}
					}.start();
				}
			} catch (IllegalArgumentException e) {
				giveInfo = true;
			}
		}
		if (giveInfo) {
			System.out.println();
			System.out.println("param set 1 [2 param]");
			System.out
					.println("AIClient (# of iterations) (number of threads)");
			System.out.println();
			System.out.println("param set 2 [3 params]");
			System.out
					.println("AIClient (# of iterations) (visable) (number of threads)");
			System.out.println();
			System.out.println("param set 3 [4 params]");
			System.out
					.println("AIClient (# of iterations) (visable) (AI Difficulty) (number of threads)");
			System.out.println();
			System.out.println("param set 4 [5 params]");
			System.out
					.println("AIClient (# of iterations) (visable) (white Player Type) (black Player Type) (number of threads)");
			System.out.println();
			System.out.println("param set 5 [6 params]");
			System.out.println("AIClient (# of iterations) (visable) (white Player Type) (black Player Type) (AI Difficulty) (number of threads)");
			System.out.println();
			System.out.println("Player Types:");
			System.out.println("\t " + GameController.PlayerType.AI);
			System.out
					.println("\t " + GameController.PlayerType.LearningServer);

		}

	}

	private static void setupRunClose() {
		GameController game = new GameController(white, black);
		game.play();
		if (game.isCheckmate() || game.isStalemate()) {
			game.disableClosing();
			tellTheServer(game.getGameHistory());
		}
		game.close();
	}

	private static void tellTheServer(Iterator<ExtendedMove> moveHistoryIterator) {
		List<ExtendedMove> moves = new ArrayList<ExtendedMove>();
		while (moveHistoryIterator.hasNext())
			moves.add(moveHistoryIterator.next());

		MoveHistory moveHistory = new MoveHistory(moves);
		String endpoint;
		if (ServerPlayer.IS_LOCAL) {
			endpoint = "http://localhost:8080/LearningChessWebServer/analyzehistory";
		} else {
			endpoint = "http://chess.neumont.edu:80/ChessGame/analyzehistory";
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
				if ((char) bytesRead != '\n' && (char) bytesRead != '\r')
					jsonStringBuilder.append((char) bytesRead);
			}
			int lengthFromServer = 0;
			try {
				String jsonString = jsonStringBuilder.toString();
				lengthFromServer = Integer.parseInt(jsonString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if (lengthFromClient != lengthFromServer)
				throw new RuntimeException("Lengths are different!");
			else
				System.out.println("Lengths are the same");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
