package edu.neumont.learningChess.dev;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
			String pattern = "MM_dd_yyyy_HHmmssSSS";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String directoryName = "GameStates";			
			String fileName = simpleDateFormat.format(Calendar.getInstance().getTime());
			
			File directory = new File(directoryName);
			if(!directory.exists()){
				directory.mkdir();
			}
			
			File file = new File(directoryName, fileName);		
	
			ChessGameState currentState = DevTools.gameController.getCurrentGameState();
			String jsonizedState = Jsonizer.jsonize(currentState);
			try {
				FileWriter wr = new FileWriter(file);
				wr.write(jsonizedState);
				wr.flush();
				wr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
