/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Sudoku;

public class Player implements Comparable<Player> {
	
	public String name;
	public Integer seconds;
	
	/**
	 * ToString method used for writing player complex types into the highscore txt-file
	 * @return String The players csv value
	 */
	public String toString() {
		return String.format("%d;%s", this.seconds, this.name);
	}
	
	/**
	 * The comparable method which allows for sorting player complex types
	 * @param Player The player complex type
	 * @return The sorted integer
	 */
	public int compareTo(Player ps) {
		return this.seconds.compareTo(ps.seconds);
	}
}
