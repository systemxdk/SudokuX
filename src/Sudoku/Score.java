/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import Constants.Language;
import Constants.Settings;
import Gui.Panels.Window;
import Gui.Tables.Models.DataModelLocal;

public class Score {

	public static boolean topscoreActive = true;
	
	private final static int NO_PLACEMENT = 0;
	
	/**
	 * Method called for returning the list of complex player highscores
	 * 
	 * @return List of players in the highscore file
	 * @throws Exception Generic exception if an error is met
	 */
	public ArrayList<Player> getHighscores() throws Exception {
		
		ArrayList<Player> scores = new ArrayList<Player>();
		
		try {
			
			File file = new File(Settings.GAME_TOPSCORE_FILE);
			if (!file.exists()) file.createNewFile();

			if (!file.canRead()) throw new Exception(Language.LANG_TOPSCORE_FILE_CANT_READ);
			if (!file.canWrite()) throw new Exception(Language.LANG_TOPSCORE_FILE_CANT_WRITE);
			
		    FileReader fr = new FileReader(Settings.GAME_TOPSCORE_FILE);
		    BufferedReader bfr = new BufferedReader(fr);
		    
		    String line = bfr.readLine();
		    while (line != null)
		    {
		    	String[] chunks = line.split(";");
		    	
		    	Player p = new Player();
		    	p.seconds = Integer.parseInt(chunks[0]);
		    	p.name = chunks.length == 1 ? Settings.GAME_DEFAULT_NAME : chunks[1]; //Empty name from topscore csv should not be possible
		    	
		    	scores.add(p);
		    	
		    	line = bfr.readLine();
		    }

		    bfr.close();
		    
			Score.topscoreActive = true;
		    
		} catch (Exception e) {
			Score.topscoreActive = false;
			
			throw e; //Relay the error message
		}
		
		return scores;
	}
	
	/**
	 * Method used for determining if we have a new record on our hands
	 * 
	 * @param duration The duration to compare with
	 * @return The placement on the highscore list
	 */
	public int isNewRecord(int duration) {
		if (!topscoreActive) return NO_PLACEMENT;
		
		try {
			ArrayList<Player> scores = getHighscores();
			
			int placement = 1;
			
			for (Player p:scores) {
				if (placement >= Settings.GAME_TOPSCORE_MAX_LIMIT) return NO_PLACEMENT; 
				
				if (p.seconds > duration) {
					return placement;
				}

				placement++;
		    }
			
			return placement; //No record beaten but there is still room in the n topscore max limit for this player
		} catch (Exception e) {
			System.out.println("Error occured fetching the local topscore list.");
		}
		
		return 0;
	}
	
	/**
	 * Method used for registering a new highscore record
	 * 
	 * @param duration The duration in seconds
	 * @param playerName The players name
	 */
	public void registerRecord(int duration, String playerName) {
		try {
			
			//Get alle local high scores
			ArrayList<Player> scores = getHighscores();
			
			//Add current player score to the arraylist of highscores
			Player p = new Player();
			p.name = playerName;
			p.seconds = duration;
			
			scores.add(p);
			
			//Sort the playerscore arraylist
			scores.sort(null);
			
			//Write the new local topscore file
		    FileWriter file = new FileWriter(Settings.GAME_TOPSCORE_FILE);
		    PrintWriter writer = new PrintWriter(file);
		    
		    int savedScores = 0;
		    for (Player score:scores) {
		    	writer.println(score);
		    	
		    	if (++savedScores >= Settings.GAME_TOPSCORE_MAX_LIMIT) break; //Cap topscore file to n limit.
		    }
		    
		    writer.close();
		    file.close();
			
		    //Feed new local topscore list to the Tablemodel
		    DataModelLocal model = (DataModelLocal)Window.tableTop10Local.getModel();
		    model.load();
		    
		    model.fireTableStructureChanged(); //Notify for structure changes
		    
		    Window.tableTop10Local.repaint(); //Repaint topscore table
		    Window.tableTop10Local.getColumnModel().getColumn(0).setPreferredWidth(27);
		    Window.tableTop10Local.getColumnModel().getColumn(2).setPreferredWidth(54);
		    
		} catch (Exception e) {
			System.out.println("Could not save the local topscore file. Write-protected?");
			
			System.out.println("Disabling local highscores.");
			Score.topscoreActive = false;
		}
	}
	
}
