package edu.neumont.learningChess.dev;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import edu.neumont.learningChess.api.ChessGameState;
import edu.neumont.learningChess.controller.GameController;
import edu.neumont.learningChess.json.Jsonizer;

public class DevTools {

	private static GameController gameController;
	public DevTools(GameController gameController) {
		DevTools.gameController = gameController;
	}
	
	public static void saveCurrentGameState() {
		if(gameController != null){
			Calendar cal = Calendar.getInstance();
			StringBuilder sb = new StringBuilder();
			sb.append(cal.get(Calendar.MONTH +1));
			sb.append("-");
			sb.append(cal.get(Calendar.DATE));
			sb.append("-");
			sb.append(cal.getTime());
			File dir = new File("gamestates");
			if(!dir.exists()){
				dir = new File("gamestates");
			}
			File file = new File(dir, sb.toString());
	
			ChessGameState currentState = DevTools.gameController.getCurrentGameState();
			String jsonizedState = Jsonizer.jsonize(currentState);
			try {
				FileWriter wr = new FileWriter(file);
				wr.write(jsonizedState);
				wr.flush();
				wr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
