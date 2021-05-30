/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Sudoku;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import Constants.Language;
import Constants.Settings;
import Gui.Panels.Cell;

public class Game {
	
	//Statics regarding the sudoku plate
	
	public static final int SIZE = 9; 
	public static final int CELLS = 81;
	public static final int NO_VALUE = 0;
	
	//Class vars

	public int[][] GridUser;
	public int[][] GridSolved;

	//Variables related to timing
	TimerTask timerTask;
	Timer timer;
	
	public long start;
	public long end;
	public int duration = 0;
	public boolean active = false;
	
	/**
	 * THe constructor for the sudoku game.
	 * 
	 * @param grid The current grid 9x9 multi array.
	 * @param realGame Defines if the current instance is a real player game
	 */
	public Game(int[][] grid, boolean realGame) {
		this.GridUser = new int[SIZE][SIZE];
		this.GridSolved = new int[SIZE][SIZE];
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.GridUser[i][j] = grid[i][j];
			}
		}
		
		this.generate();
		
		if (!realGame) {
			this.fill();
			return;
		}
		
		this.setDifficulty(Settings.GAME_DIFFICULTY);
		this.setTimer();
		this.setActive();
		
		this.fill();
	}
	
	/**
	 * Method used to set a timer when a game starts.
	 * Timer makes sure to relay the gametime in the statusbar with format HH:mm:ss
	 */
	public void setTimer() {
		this.start = System.currentTimeMillis() / 1000L; //We use unix epoch.
		
		this.timer = new Timer();
		this.timerTask = new Gui.Timers.Timer();
		this.timer.schedule(this.timerTask, 1000, 1000);
	}
	
	/**
	 * Stop the game timer, ex. when game completes
	 * This includes calculating and registering the total duration of the game
	 */
	public void stopTimer() {
		this.timer.cancel();
		
		this.end = System.currentTimeMillis() / 1000L; //We use unix epoch.
		this.duration = (int)(this.end - this.start);
	}
	
	/**
	 * Method used to format seconds into HH:mm:ss format
	 * 
	 * @param seconds	The amount of seconds
	 * @return			The string representation of integer seconds (HH:mm:ss)
	 */
	public static String formatTime(int seconds) {
		Date d = new Date(seconds * 1000L);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(d);
	}
	
	/**
	 * Method for setting the active game state
	 */
	private void setActive() {
		this.active = true;
	}
	
	/**
	 * Method for setting the inactive game state
	 */
	private void setInactive() {
		this.active = false;
	}
	
	/**
	 * Determines if a number already exists in the entire 9-cell vertical column.
	 * 
	 * @credits 		https://gist.github.com/ssaurel/d9d025ee2d802b975ff8e314dcf2dd2a
	 * @param col 		The column integer
	 * @param number 	The number typed in from user
	 * @return 			A boolean true/false value.
	 */
	private boolean isInCol(int col, int number, boolean inclusive) {
		int occurences = 0;
		for (int i = 0; i < SIZE; i++) {
			if (GridUser[i][col] == number) {
				occurences++;
			}
		}
		if (inclusive) return occurences > 1;
		return occurences > 0;
	}
	
	/**
	 * Determines if a number exists in the appropriate 3x3 box.
	 * 
	 * @credits 		https://gist.github.com/ssaurel/d9d025ee2d802b975ff8e314dcf2dd2a
	 * @param row 		The row integer
	 * @param col		The column integer
	 * @param number	The number typed in from user
	 * @return			A boolean true/false value
	 */
	private boolean isInBox(int row, int col, int number, boolean inclusive) {
		int occurences = 0;
		int r = row - row % 3;
		int c = col - col % 3;
		
		for (int i = r; i < r + 3; i++) {
			for (int j = c; j < c + 3; j++) {
				if (GridUser[i][j] == number) {
					occurences++;
				}
			}
		}

		if (inclusive) return occurences > 1;
		return occurences > 0;
	}
	
	/**
	 * Determines if a number already exists in the 9-cell horizonal row
	 * 
	 * @credits 		https://gist.github.com/ssaurel/d9d025ee2d802b975ff8e314dcf2dd2a
	 * @param row		The row integer
	 * @param number	The number typed in from user
	 * @return			A boolean true/false value
	 */
	private boolean isInRow(int row, int number, boolean inclusive) {
		int occurences = 0;
		for (int i = 0; i < SIZE; i++) {
			if (GridUser[row][i] == number) {
				occurences++;
			}
		}
		if (inclusive) return occurences > 1;
		return occurences > 0;
	}
	
	/**
	 * Method to determine if a typed-in number is valid horizontally, vertically and in its 3x3 box.
	 * 
	 * @credits 		https://gist.github.com/ssaurel/d9d025ee2d802b975ff8e314dcf2dd2a
	 * @param row		The row integer
	 * @param col		The column integer
	 * @param number	The number typed in from user
	 * @return			A boolean true/false value
	 */
	public boolean isOk(int row, int col, int number, boolean inclusive) {
		return !isInRow(row, number, inclusive) && !isInCol(col, number, inclusive) && !isInBox(row, col, number, inclusive);
	}
	
	/**
	 * Method to determine if the sudoku board is complete and valid
	 * It does so by checking for empty cells, and horizontal, vertical and box placement of digits.
	 * 
	 * @return	A boolean true/false value
	 */
	public boolean isBoardComplete() {
		
		List<Integer> blankCells = getBlankCells();
		if (blankCells.size() > 0) return false;
		
		//All cells filled, so iterate and check validity
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				
				if (!isOk(row, col, this.GridUser[row][col], true)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Method to run after the sudoku is completed and has been validated
	 * 
	 */
	public void complete() {
		
		//Lock the plate
		Main.plate.lock();
		
		//Stop the timer
		this.stopTimer();
		
		//Flip completed game to inactive.
		//This prevents actions from buttons like "hint" and "give up"
		this.setInactive();
		
		//Set status message to the user
		message(String.format(Language.LANG_STATUS_GAME_COMPLETED, this.duration));
		
		//Dialogging..
		Score score = new Score();
		
		int recordPositionLocal = score.isNewRecord(this.duration);
		
		boolean isNewRecordLocal = recordPositionLocal > 0;
		
		boolean returnNameFromInput = isNewRecordLocal;
		
		String dialogMessage = String.format(Language.LANG_TEXT_HIGHSCORES_GZ, duration);
		if (isNewRecordLocal) {
			dialogMessage += String.format(Language.LANG_TEXT_HIGHSCORES_NEW_RECORD, recordPositionLocal);
		} else {
			dialogMessage += String.format(Language.LANG_TEXT_HIGHSCORES_NO_RECORD);
		}
		
		Dialog d = new Dialog(returnNameFromInput);
		String playerName = d.show(dialogMessage);

		if (isNewRecordLocal) {
			score.registerRecord(this.duration, playerName);
		}

	}
	
	/**
	 * Method called when user clicks the "give up" jbutton
	 */
	public void giveup() {
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int pos = (i * 9) + j + 1;
				
				Cell cell = Main.plate.getCell(pos);
				
				int result = this.GridSolved[i][j];
				this.GridUser[i][j] = result;
				
				cell.set(Integer.toString(result), true);
			}
		}
		
		//Lock the plate
		Main.plate.lock();

		//Stop the timer
		this.stopTimer();

		//Toggle game state
		this.setInactive();
		
		//Set status message to the user
		message(String.format(Language.LANG_STATUS_GAME_GIVE_UP, this.duration));
		
	}
	
	/**
	 * Relay a message to the player in the south jlabel
	 * 
	 * @param message The message for the player
	 */
	public void message(String message) {
		Gui.Panels.Window.labelMessage.setText(message);
	}
	
	/**
	 * Method for returning the solved digit on a given 1-81 position
	 * Wraps the getSolvedValue with row, col arguments
	 * 
	 * @param position The position 
	 * @return The integer digit 
	 */
	public Integer getSolvedValue(int position) {
		int row = (int)Math.floor(position / SIZE);
		int col = position % SIZE - 1;
		
		if (col == -1) {
			col = 8;
			row = row - 1;
		}
		
		return getSolvedValue(row, col);
	}
	
	/**
	 * Method for returning the solved digit on a Grid[row][col] position
	 * 
	 * @param row The row integer
	 * @param col The column integer
	 * @return THe digit
	 */
	public Integer getSolvedValue(int row, int col) {
		return GridSolved[row][col];
	}
	
	/**
	 * Method used for returning an integer list of blank cells
	 * 
	 * @return Integer list of blanks
	 */
	public List<Integer> getBlankCells() {
		List<Integer> blanks = new ArrayList<Integer>();
		
		for (int i = 1; i <= CELLS; i++) {
			Cell cell = Main.plate.getCell(i);
			
			int value = cell.getValue();
			if (value == NO_VALUE) {
				blanks.add(i);
			}
		}
		
		return blanks;
	}
	
	/**
	 * Method called when the players hits the "hint" jbutton
	 */
	public void hint() {
		
		//Get a list of all blank cells on the sudoku board
		List<Integer> blankCells = getBlankCells();
		if (blankCells.size() == 0) return; //No more hints to be given.
		
		//Shuffle list of blanks
		Collections.shuffle(blankCells);
		
		//Pick the first blank cell from the randomly ordered list.
		int cellId = blankCells.get(0);
		
		//Get value from specified cell id
		int value = getSolvedValue(cellId);

		//Add punish seconds to the timer
		Main.currentGame.start -= Settings.GAME_HINT_PUNISHMENT_SEC;
		message(String.format(Language.LANG_STATUS_HINT_PUNISHMENT, Settings.GAME_HINT_PUNISHMENT_SEC));
		
		//Set according value to players board 
		Cell cell = Main.plate.getCell(cellId);
		cell.set(value, false);
	}
	
	/**
	 * Method called for generating a new Grid[row][col] array 
	 * We randomize our plate by giving the first row random numbers.
	 * After this we solve the plate and registers the values for our grid arrays
	 * 
	 * @credits https://gist.github.com/ssaurel/d9d025ee2d802b975ff8e314dcf2dd2a (in part)
	 * @return Boolean representation if the plate generation went well
	 */
	public boolean generate() {
		
		//Fill out the grid.
		//Start by randomizing the first row, then fill all the remainder cells by solving the puzzle.
		
		ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9 ));
		Collections.shuffle(numbers);
		
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (GridUser[row][col] == NO_VALUE) {
					for (Integer number : numbers) {
						if (isOk(row, col, number, false)) {
							
							GridUser[row][col] = number;
							GridSolved[row][col] = number;
						
							if (generate()) { // we start backtracking recursively
								return true;
							} else {
								GridUser[row][col] = NO_VALUE;
								GridSolved[row][col] = NO_VALUE;
							}
						}
					}
					
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Method called for removing cell values from a fixed solved plate
	 * 
	 * The idea is to remove n numbers from a 3x3 region.
	 * Ex. this means that if the difficulty is 5 we remove 5 digits from the 3x3 region.
	 * 
	 * Therefore the lower the difficulty number, the easier the game is.
	 * 
	 * @param difficulty The difficulty integer
	 */
	public void setDifficulty(int difficulty) {
		HashMap<Integer, String> boxMap = new HashMap<Integer, String>();

		boxMap.put(1, "0,0/0,1/0,2/1,0/1,1/1,2/2,0/2,1/2,2"); //Box 1 positions, upperleft
		boxMap.put(2, "0,3/0,4/0,5/1,3/1,4/1,5/2,3/2,4/2,5"); //Box 2 positions, uppermid
		boxMap.put(3, "0,6/0,7/0,8/1,6/1,7/1,8/2,6/2,7/2,8"); //Box 3 positions, upperright

		boxMap.put(4, "3,0/3,1/3,2/4,0/4,1/4,2/5,0/5,1/5,2"); //Box 4 positions, midleft
		boxMap.put(5, "3,3/3,4/3,5/4,3/4,4/4,5/5,3/5,4/5,5"); //Box 5 positions, mid
		boxMap.put(6, "3,6/3,7/3,8/4,6/4,7/4,8/5,6/5,7/5,8"); //Box 6 positions, midright

		boxMap.put(7, "6,0/6,1/6,2/7,0/7,1/7,2/8,0/8,1/8,2"); //Box 7 positions, lowerleft
		boxMap.put(8, "6,3/6,4/6,5/7,3/7,4/7,5/8,3/8,4/8,5"); //Box 8 positions, lowermid
		boxMap.put(9, "6,6/6,7/6,8/7,6/7,7/7,8/8,6/8,7/8,8"); //Box 9 positions, lowerright
		
		Iterator<Map.Entry<Integer, String>> it = boxMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer, String> pair = (Map.Entry<Integer, String>)it.next();
	        
	        String combos = (String)pair.getValue();
	        
	        String[] coords = combos.split("/");
	        List<String> coordsList = Arrays.asList(coords);
	        
	        Collections.shuffle(coordsList);
	        
	        for (int c = 0; c < difficulty; c++) {
	        	String[] coord = coordsList.get(c).split(",");
	        	
	        	int row = Integer.parseInt(coord[0]);
	        	int col = Integer.parseInt(coord[1]);
	        	
	        	GridUser[row][col] = 0;
	        }
	    }
	}
	
	/**
	 * Method used to fill the plate with values from the GridUser multidim.
	 */
	public void fill() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int pos = (i * 9) + j + 1;
				
				Cell cell = Main.plate.getCell(pos);
				
				int digit = this.GridUser[i][j];
				if (digit == 0) continue;
				
				cell.set(Integer.toString(digit), true);
			}
		}
	}
	
	/**
	 * The toString() method of the Sudoku game.
	 * This will print a plate into the IDE console.
	 */
	public String toString() {
		String ret = "";
		
		//Iterate player grid
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				ret += GridUser[r][c] + " ";
			}
			ret += "\n";
		}
		
		ret += "\n";
		
		//Iterate solution grid
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				ret += GridSolved[r][c] + " ";
			}
			ret += "\n";
		}

		ret += "\n";
		
		return ret;
	}

}
