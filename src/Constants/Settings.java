/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Constants;

import java.awt.Color;
import java.awt.Font;

public class Settings {

	//Display animation on startup
	public final static boolean STARTUP_PLATE_ANIMATION = true;
	
	//Cell font size and family
	public final static Font GAME_FONT = new Font("Verdana", 1, 18);
	
	/*
	 * Defines how many random cells are cleared per 3x3 block.
	 * The higher this number is the harder the sudoku game is.
	 * 
	 * IMPORTANT: If set to 7 or higher the grid is practically unsolvable.
	 */
	public final static int GAME_DIFFICULTY = 5; 	
	
	//The integer below will be added to the players game time when a hint is given.
	public final static int GAME_HINT_PUNISHMENT_SEC = 30;
	
	//Defines the filename containing local topscores
	public final static String GAME_TOPSCORE_FILE = "topscore.txt";
	
	//Defines the max limit of players on the topscore limit.
	//This value is not easily editable, as it requires changes to the frame.
	public final static int GAME_TOPSCORE_MAX_LIMIT = 15;
	
	//Default name for the player if a name is not supplied
	public final static String GAME_DEFAULT_NAME = "Anonym"; 

	// Plate cell backgrounds
	public final static Color COLOR_CELL_STATIC = Color.WHITE; //Numbers prefilled by the game
	public final static Color COLOR_CELL_PLAIN = Color.WHITE; //Cells to be filled by the player
	public final static Color COLOR_CELL_HOVER = Color.LIGHT_GRAY; //Cell background color on hover
	public final static Color COLOR_CELL_EDIT = Color.GRAY; //Cell background when editing the cell
	public final static Color COLOR_CELL_ERROR = Color.RED; //Cell background when inserted number collides on x,y
	
	//Multidim. int array that defines an empty board
	public static final int[][] EMPTY_GRID = {
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
	};
}
