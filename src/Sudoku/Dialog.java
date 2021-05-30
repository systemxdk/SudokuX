/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Sudoku;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Constants.Settings;

public class Dialog {  
	
	JFrame f;
	boolean returnName = false;
	
	/**
	 * Constructor for the class
	 * 
	 * @param returnName Defines if name should be returned by the option dialog.
	 */
	public Dialog(boolean returnName) {
		this.returnName = returnName;
	}

	/**
	 * Method for showing an option dialog to the player
	 * If returnName is true the player is asked for their name
	 * 
	 * @param message The message for the player
	 * @return The players name if returnName is set
	 */
	public String show(String message) {
		f = new JFrame();
		
		if (this.returnName) {
			String inputName = JOptionPane.showInputDialog(f, message, "Indtast navn", JOptionPane.DEFAULT_OPTION);
			if (inputName.isEmpty()) inputName = Settings.GAME_DEFAULT_NAME;
			
			return inputName;
		} else {
			JOptionPane.showConfirmDialog(f, message, "Spillet er slut", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			return "";
		}
	}
}  
