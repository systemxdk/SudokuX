/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Sudoku;

import javax.swing.*;

import Gui.Panels.Plate;
import Gui.Panels.Window;

public class Main {

	public static Game currentGame = null;
	public static Plate plate = null;
	
	public static void main(String[] args) { //Entry main

		Window panel = new Window();
		
		JFrame window = new JFrame(Constants.Language.LANG_TITLE);
		
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setSize(880,507);
		window.add(panel);
		
		window.setResizable(false);
		window.setVisible(true);
		
	}

}
