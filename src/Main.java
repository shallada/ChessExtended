import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

public class Main {

	public static void main(String[] args) {
		JPanel LoginOptionMenu = new JPanel();
		String title = "";
		switch (JOptionPane.showConfirmDialog(null, "Do you have an account?", "Login", JOptionPane.YES_NO_OPTION)) {
		case 0:
			LoginOptionMenu.add(new JTextArea("User Name"));
			LoginOptionMenu.add(new JTextArea("Password"));
			LoginOptionMenu.setLayout(new GridLayout(2, 1, 0, 15));
			title = "Login";
			break;
		case 1:
			LoginOptionMenu.add(new JLabel("User Name"));
			LoginOptionMenu.add(new JTextField());
			LoginOptionMenu.add(new JLabel("Password"));
			LoginOptionMenu.add(new JPasswordField());
			LoginOptionMenu.add(new JLabel("Password confirm"));
			LoginOptionMenu.add(new JPasswordField());
			LoginOptionMenu.setLayout(new GridLayout(4, 2, 0, 15));
			title = "Register";
			break;
		}
		JOptionPane.showMessageDialog(null, LoginOptionMenu, title, JOptionPane.INFORMATION_MESSAGE);
		do {
			ThemeNames[] values = ThemeNames.values();
			String[] themeNames = new String[values.length];
			for (int i = 0; i < values.length; i++) {
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
			GameController game = new GameController(white, black, theme);
			GameOverType gameOverType = game.play();
			if (game.isCheckmate() || game.isStalemate()) {
				game.disableClosing();
				PlayerType winnerType = null;
				if (gameOverType == GameOverType.checkmate)
					winnerType = game.getCurrentTeam().isWhite() ? black : white;
				tellTheServer(game.getGameHistory(), white.toString(), black.toString(), winnerType);
				game.close();
			}
		} while (JOptionPane.showConfirmDialog(null, "do you want to play again?", "play again?", JOptionPane.YES_NO_OPTION) == 0);
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
	
	public static String MD5(String str) {
		String s = null;
		try {
			byte[] bytesOfMessage = str.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			s = new String(thedigest, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
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
