/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Gui.Panels;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import Constants.Language;
import Constants.Settings;
import Gui.Buttons.*;
import Gui.Tables.Models.DataModelLocal;
import Gui.Threads.Animation;
import Sudoku.Main;

public class Window extends JPanel {
	
	private static final long serialVersionUID = 6210033488459065174L; //Auto-generated by Eclipse
	
	public static JLabel labelTime;
	public static JLabel labelMessage;

	public static JTable tableTop10Local;
	
	/**
	 * The constructor for the window jpanel
	 */
	public Window() {
		setLayout(new BorderLayout());
		
		//////////////////////////////
		// BUTTON CONTAINER - NORTH //
		//////////////////////////////
		
		JPanel topButtonContainer = new JPanel();
		topButtonContainer.add(new New(Constants.Language.LANG_BUTTON_GAME_NEW));
		topButtonContainer.add(new Hint(Constants.Language.LANG_BUTTON_GAME_HINT));
		topButtonContainer.add(new GiveUp(Constants.Language.LANG_BUTTON_GAME_REVEAL));
	    add(topButtonContainer, BorderLayout.NORTH);

		//////////////////////////////
	    // PLATE CONTAINER - CENTER //
		//////////////////////////////
	    
	    Plate plate = new Plate();
	    add(plate, BorderLayout.CENTER);
	    
		Main.plate = plate;
		
		MatteBorder border = new MatteBorder(1, 1, 1, 1, Color.BLACK);

		////////////////////////////////
		// HIGHSCORE CONTAINER - WEST //
		////////////////////////////////
		
		JLabel labelTop10Local = new JLabel();
		labelTop10Local.setHorizontalAlignment(JLabel.CENTER);
		labelTop10Local.setOpaque(true);
		labelTop10Local.setText(Constants.Language.LANG_SCOREBOARD_TOP10_LOCAL);
		labelTop10Local.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		tableTop10Local = new JTable();
		tableTop10Local.setIntercellSpacing(new Dimension(10, 20));
		tableTop10Local.setBorder(border);
		tableTop10Local.setRowHeight(25);
		tableTop10Local.setRowMargin(1);
		tableTop10Local.setModel(new DataModelLocal());
		tableTop10Local.createDefaultColumnsFromModel();
		if (tableTop10Local.getColumnModel().getColumnCount() == 3) { //Data feeded
			tableTop10Local.getColumnModel().getColumn(0).setPreferredWidth(27);
			tableTop10Local.getColumnModel().getColumn(2).setPreferredWidth(54);
		}
		tableTop10Local.setFocusable(false);
		tableTop10Local.setRowSelectionAllowed(false);
		tableTop10Local.setBorder(border);
		
		JPanel scoreContainerLocal = new JPanel();
		scoreContainerLocal.setLayout(new BorderLayout());
		scoreContainerLocal.add(labelTop10Local, BorderLayout.NORTH);
		scoreContainerLocal.add(tableTop10Local, BorderLayout.CENTER);
		scoreContainerLocal.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		scoreContainerLocal.setPreferredSize(new Dimension(200, 20));
		add(scoreContainerLocal, BorderLayout.WEST);

		///////////////////////////
		// RULE CONTAINER - EAST //
		///////////////////////////
		
		JLabel labelRulesHeader = new JLabel();
		labelRulesHeader.setHorizontalAlignment(JLabel.CENTER);
		labelRulesHeader.setOpaque(true);
		labelRulesHeader.setText(Language.LANG_TEXT_RULE_HEADER);
		labelRulesHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		StringBuilder rules = new StringBuilder();
		rules.append(String.format(Language.LANG_TEXT_RULE_CONTAINER, Settings.GAME_HINT_PUNISHMENT_SEC));
		
		JLabel labelRules = new JLabel();
		labelRules.setText(rules.toString());
		
		JPanel rulesContainer = new JPanel();
		rulesContainer.setLayout(new BorderLayout());
		rulesContainer.add(labelRulesHeader, BorderLayout.NORTH);
		rulesContainer.add(labelRules, BorderLayout.CENTER);
		rulesContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		rulesContainer.setPreferredSize(new Dimension(200, 20));
		
		add(rulesContainer, BorderLayout.EAST);
		
		/////////////////////////////
		// LABEL CONTAINER - SOUTH //
		/////////////////////////////
		
		labelTime = new JLabel(Language.LANG_STATUS_DEFAULT_TIME);
		labelMessage = new JLabel(Language.LANG_STATUS_DEFAULT);
		
		labelTime.setBackground(Color.BLUE);
		labelMessage.setBackground(Color.RED);
		
		JPanel labelContainer = new JPanel();
		labelContainer.setLayout(new BorderLayout());
		labelContainer.add(labelTime, BorderLayout.CENTER);
		labelContainer.add(labelMessage, BorderLayout.EAST);
		labelContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(labelContainer, BorderLayout.SOUTH);
	    
		try {
			
			if (Settings.STARTUP_PLATE_ANIMATION) { //Make a simple animation on the plate when opening the game
				Animation animation = new Animation(); //Start a separate thread so we dont block the main thread
				animation.start();
			}

		} catch (Exception e) {
			System.out.println("Error occured on animation: " + e.getMessage());
		}
		
	}
	
	/**
	 * Method for calling the super paintComponenent 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}