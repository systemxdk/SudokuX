/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Gui.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Gui.Panels.Cell;

public class GameKeyListener implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
    	Cell cell = (Cell)e.getSource();
    	
		boolean validKey = 
				(e.getKeyChar() >= '1' && e.getKeyChar() <= '9') 
				|| e.getKeyChar() == KeyEvent.VK_BACK_SPACE 
				|| e.getKeyChar() == KeyEvent.VK_DELETE;
		
		if (!validKey) return;
		
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == KeyEvent.VK_DELETE) {
			cell.clear();
			return;
		}
		
		int digit = Character.getNumericValue(e.getKeyChar());

		cell.set(digit, false);
		cell.Edit = false;
	}

}
