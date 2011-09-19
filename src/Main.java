import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.api.ThemeNames;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.controller.GameController.PlayerType;
import edu.neumont.learningChess.controller.GameOverType;
import edu.neumont.learningChess.json.Jsonizer;
import edu.neumont.learningChess.model.ServerPlayer;
import edu.neumont.learningChess.model.TextCommandProcessor;
import edu.neumont.learningChess.model.TextCommandProcessorOutput;
import edu.neumont.learningChess.model.WinnerType;

public class Main {

	private static final int N_THREADS = 1;

	public static void main(String[] args) {
		ThemeNames[] values = ThemeNames.values();
		String[] themeNames = new String[values.length];
		for (int i = 0; i <  values.length; i++) {
			themeNames[i] = values[i].toString();
		}
		JComboBox themeBox = new JComboBox(themeNames);
		JComboBox whiteComboBox = new JComboBox(new Object[] { GameController.PlayerType.Human, GameController.PlayerType.LearningServer, GameController.PlayerType.AI });
		JComboBox blackComboBox = new JComboBox(new Object[] { GameController.PlayerType.Human, GameController.PlayerType.LearningServer, GameController.PlayerType.AI });
		blackComboBox.setSelectedIndex(1);
		JPanel comboBoxes = new JPanel();
		comboBoxes.setLayout(new GridLayout(3, 3, 0, 15));
		comboBoxes.add(new JLabel("White:"));
		comboBoxes.add(whiteComboBox);
		comboBoxes.add(new JLabel("Black:"));
		comboBoxes.add(blackComboBox);
		comboBoxes.add(new JLabel("Piece theme:"));
		comboBoxes.add(themeBox);

		JOptionPane.showMessageDialog(null, comboBoxes, "Select Players", JOptionPane.INFORMATION_MESSAGE);
		final String theme = themeBox.getSelectedItem().toString();
		final GameController.PlayerType white = GameController.PlayerType.valueOf(whiteComboBox.getSelectedItem().toString());
		final GameController.PlayerType black = GameController.PlayerType.valueOf(blackComboBox.getSelectedItem().toString());

		GameController.setShowBoard(true);
		for (int j = 0; j < N_THREADS; j++) {
			new Thread() {
				@Override
				public void run() {
					GameController game = new GameController(white, black, theme);
					GameOverType gameOverType = game.play();
					if (game.isCheckmate() || game.isStalemate()) {
						game.disableClosing();
						PlayerType winnerType = null;
						if(gameOverType == GameOverType.checkmate)
							winnerType = game.getCurrentTeam().isWhite() ? black : white;
						tellTheServer(game.getGameHistory(), white.toString(), black.toString(), winnerType);
					}
					game.close();
				}
			}.start();
		}
	}

	private static void tellTheServer(Iterator<ExtendedMove> moveHistoryIterator, String whiteName, String blackName, PlayerType winnerType) {
		List<ExtendedMove> moves = new ArrayList<ExtendedMove>();
		while (moveHistoryIterator.hasNext())
			moves.add(moveHistoryIterator.next());

		MoveHistory moveHistory = new MoveHistory(moves);
		moveHistory.setWhitePlayerName(whiteName);
		moveHistory.setBlackPlayerName(blackName);
		moveHistory.setWinnerType(winnerType);
		String endpoint;
		if (ServerPlayer.IS_LOCAL) {
			endpoint = "http://localhost:8080/LearningChessWebServer/analyzehistory";
		} else {
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

	public static void old_main(String[] args) {

		TextCommandProcessorOutput output = new TextCommandProcessorOutput(System.out);
		TextCommandProcessor processor = new TextCommandProcessor();
		try {
			processor.processCommands(System.in, output);
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}
}
