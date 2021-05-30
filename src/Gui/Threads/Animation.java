/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Gui.Threads;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Constants.Settings;
import Gui.Panels.Cell;
import Sudoku.Game;
import Sudoku.Main;

public class Animation extends Thread {

	private static Object LOCK = new Object();
	
	/**
	 * Thread method to run and create a plate animation on startup
	 */
	public void run() {
		for (int anim = 0; anim <= 10; anim++) {
			
    		@SuppressWarnings("unused") //Constructor of sudoku fills the plate, so there is a purpose of it unused
			Game sudoku = new Game(Settings.EMPTY_GRID, false);
    		
			synchronized(LOCK) {   
			    try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Main.plate.clear();
		
		HashMap<Integer, String> boardMessage = new HashMap<Integer, String>();

		boardMessage.put(11, "-");
		boardMessage.put(12, "-");
		boardMessage.put(13, "-");
		boardMessage.put(14, "-");
		boardMessage.put(15, "-");
		boardMessage.put(16, "-");
		boardMessage.put(17, "-");
		
		boardMessage.put(28, "V");
		boardMessage.put(29, "E");
		boardMessage.put(30, "L");
		boardMessage.put(31, "K");
		boardMessage.put(32, "O");
		boardMessage.put(33, "M");
		boardMessage.put(34, "M");
		boardMessage.put(35, "E");
		boardMessage.put(36, "N");
		
		boardMessage.put(40, "T");
		boardMessage.put(41, "I");
		boardMessage.put(42, "L");
		
		boardMessage.put(47, "S");
		boardMessage.put(48, "U");
		boardMessage.put(49, "D");
		boardMessage.put(50, "O");
		boardMessage.put(51, "K");
		boardMessage.put(52, "U");
		boardMessage.put(53, "X");

		boardMessage.put(65, "-");
		boardMessage.put(66, "-");
		boardMessage.put(67, "-");
		boardMessage.put(68, "-");
		boardMessage.put(69, "-");
		boardMessage.put(70, "-");
		boardMessage.put(71, "-");
		
		Iterator<Map.Entry<Integer, String>> it = boardMessage.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry<Integer, String> pair = (Map.Entry<Integer, String>)it.next();
	        
	        int position = (int)pair.getKey();
	        String letter = (String)pair.getValue();
	        
	        Cell cell = Main.plate.getCell(position);
	        cell.set(letter, true);
	        
	        it.remove();
	    }
		
	}
}
