/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Gui.Events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Constants.Settings;
import Gui.Panels.Cell;
import Sudoku.Game;
import Sudoku.Main;

public class GameMouseListener implements MouseListener {
	
	private Cell cell;
	
	/**
	 * Constructor for the mouse listener.
	 * 
	 * @param cell The cell argument instance which is registered privately
	 */
	public GameMouseListener(Cell cell) {
		this.cell = cell;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
    	Cell caller = (Cell)e.getSource();
    	if (caller.Edit) {
    		caller.edit(false);
    	} else {
    		
    		for (int i = 0; i < Game.SIZE; i++) {
    			for (int j = 0; j < Game.SIZE; j++) {
    				int pos = (i * 9) + j + 1;
    				
    				Cell cell = Main.plate.getCell(pos);
    				cell.edit(false);
    			}
    		}
    		
    		caller.edit(true);
    		
    		caller.setFocusable(true);
    		caller.requestFocusInWindow();
    	}
	}
	
	@Override
    public void mouseEntered(MouseEvent e) {
    	Cell caller = (Cell)e.getSource();
    	
    	if (caller.Locked) return;
    	if (caller.Edit) return;
    	if (caller.stateError) return; 
    	
        this.cell.setBackground(Settings.COLOR_CELL_HOVER);
    }
	
	@Override
    public void mouseExited(MouseEvent e) {
    	Cell caller = (Cell)e.getSource();
    	
    	if (caller.Locked) return;
    	if (caller.Edit) return;
    	if (caller.stateError) return; 
    	
    	this.cell.setBackground(Settings.COLOR_CELL_PLAIN);
    }
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
}
