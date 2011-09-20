import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.neumont.learningChess.api.ExtendedMove;
import edu.neumont.learningChess.api.MoveHistory;
import edu.neumont.learningChess.json.Jsonizer;
import edu.neumont.learningChess.model.ServerPlayer;


public class DataMining {

	private static ArrayList<String> whitePGN;
	private static ArrayList<String> blackPGN;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File(args[0]);
		
		parseGameInfoFromFile(file);

	}
	
	private static void parseGameInfoFromFile(File file)
	{
		String ChessNotationText = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String temp;
			while((temp = in.readLine()) != null)
			{
				ChessNotationText = ChessNotationText.concat(" " + temp);
				System.out.println(temp);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile("(?:\\d{1,3}?\\.)\\s(O-O|O-O-O|[PKBQRNa-h]?[a-h1-8]{0,2}?x?[a-h][1-8][+#]?)\\s(O-O|O-O-O|1/2-1/2|1-0|0-1|[PKBQRNa-h]?[a-h1-8]{0,2}?x?[a-h][1-8][+#]?)\\s?(1/2-1/2|1-0|0-1)?");
		Matcher matcher = pattern.matcher(ChessNotationText);
		while (matcher.matches());
		{
			whitePGN.add(matcher.group(1));
			blackPGN.add(matcher.group(2));
		}
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
