/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Gui.Panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Constants.Settings;
import Gui.Events.GameKeyListener;
import Gui.Events.GameMouseListener;
import Sudoku.Main;

public class Cell extends JPanel {
	
	private static final long serialVersionUID = -3434189255940071675L; //Auto-generated by Eclipse

	JLabel digit = null;
	
	public boolean Locked = false;
	public boolean Edit = false;

	public int cellPosition;
	public int cellRow;
	public int cellCol;
	
	public boolean stateError = false;

	public static final int NO_VALUE = 0;

	/**
	 * Constructor for the cell panel
	 */
    public Cell() {
    	this.setBackground(Settings.COLOR_CELL_PLAIN);
    	this.setPreferredSize(new Dimension(10,10));
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		this.digit = new JLabel(" ");
		this.digit.setFont(Settings.GAME_FONT);
		
		add(this.digit, gbc);
		
		addKeyListener(new GameKeyListener());
		
        addMouseListener(new GameMouseListener(this));
        
    }

    /**
     * Method for locking the plate cell.
     */
    public void lock() {
    	this.Locked = true;
    }

    /**
     * Method for unlocking the plate cell.
     */
    public void unlock() {
    	this.Locked = false;
    }
    
    /**
     * Method for setting the edit state on the plate cell class.
     * 
     * @param edit Sets the editable boolean flag on the cell.
     * @return The editable flag
     */
    public boolean edit(boolean edit) {
    	if (this.Locked) return false; //This cell is locked and so beyond any edit capabilities.
    	
    	this.Edit = edit;
    	
    	if (this.Edit) { //Cell is marked editable
    		setBackground(Settings.COLOR_CELL_EDIT);
    	} else {
    		setBackground(Settings.COLOR_CELL_PLAIN);
    	}
    	
    	if (stateError) { //Override with error background if cell is in a state of error.
    		setBackground(Settings.COLOR_CELL_ERROR);
    	}
    	
    	return edit;
    }
    
    /**
     * Method for gettign the cells current number.
     * If the sudoku cell does not contain a number, return 0 here.
     * 
     * @return The integer value for the cell, 0-9
     */
    public int getValue() {
    	String stringValue = this.digit.getText();
    	if (stringValue == "" || stringValue == null) return NO_VALUE;
    	
    	return Integer.parseInt(stringValue);
    }
    
    /**
     * Method for setting the cell digit with an integer entry
     * 
     * @param alpha The integer number to be filled
     * @param locked The locked parameter defines if the cell should be locked after update
     */
    public void set(int alpha, boolean locked) {
    	set(String.valueOf(alpha), locked);
    }
    
    /**
     * Method for setting the cell digit as a string
     * 
     * @param alpha The number to be filled, string rep.
     * @param locked The locked parameter defines if the cell should be locked after update
     */
    public void set(String alpha, boolean locked) {
    	this.digit.setText(alpha);
    	this.Locked = locked;
    	
    	if (locked) {
    		this.setBackground(Settings.COLOR_CELL_STATIC);
    		return;
    	}

    	this.setBackground(Settings.COLOR_CELL_PLAIN);
    	
    	if (alpha == null) return;
    	
    	int digit = getValue();
    	
    	if (!Main.currentGame.isOk(this.cellRow - 1, this.cellCol - 1, digit, false)) {
        	this.setBackground(Settings.COLOR_CELL_ERROR);
        	this.stateError = true;
    	} else {
    		this.stateError = false;
    	}
    	
    	Main.currentGame.GridUser[this.cellRow - 1][this.cellCol - 1] = digit;
    	
    	//Is board complete?
    	if (Main.currentGame.isBoardComplete()) {
    		Main.currentGame.complete();
    	}
    }
    
    /**
     * Method used for clearing, or deleting a cell with backspace/delete
     */
    public void clear() {
    	Main.currentGame.GridUser[this.cellRow - 1][this.cellCol - 1] = NO_VALUE;
    	this.digit.setText("");
    	
    	this.stateError = false; //Empty value can not be in a state of error

    	this.setBackground(Settings.COLOR_CELL_PLAIN);
    	
    	this.Edit = false;
    }
}
