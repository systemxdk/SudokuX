/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Gui.Timers;

import java.util.TimerTask;

import Constants.Language;
import Sudoku.Game;
import Sudoku.Main;

public class Timer extends TimerTask {
	
	/**
	 * Method set to run by the game timer.
	 * Updates the south jlabel with a time spent text.
	 */
	public void run() {
		long unixEpochCurrent = System.currentTimeMillis() / 1000L;
		int gameDuration = (int)(unixEpochCurrent - Main.currentGame.start);
		
		Gui.Panels.Window.labelTime.setText(String.format(Language.LANG_TEXT_TIME_SPENT, Game.formatTime(gameDuration)));
	}	
	
}
