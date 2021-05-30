/**
 * @author: Steffen Beck
 * @snr: s211091
 */

package Constants;

public class Language {
	
	public final static String LANG_TITLE = "SudokuX";
	
	public final static String LANG_SCOREBOARD_TOP10_INTERNET = "Top 15 - Internet";
	public final static String LANG_SCOREBOARD_TOP10_LOCAL = "Top 15 - Denne PC";

	public final static String LANG_BUTTON_GAME_NEW = "Nyt spil";
	public final static String LANG_BUTTON_GAME_HINT = "Hint";
	public final static String LANG_BUTTON_GAME_REVEAL = "Giv op";
	public final static String LANG_BUTTON_GAME_SOLVER = "Sudoku Løser";
	
	public final static String LANG_STATUS_DEFAULT = "Klik på 'Nyt spil' for at starte";
	public final static String LANG_STATUS_DEFAULT_TIME = "Tid brugt: 00:00:00";
	public final static String LANG_STATUS_GAME_STARTED = "Dit spil er begyndt - held og lykke!";
	public final static String LANG_STATUS_HINT_PUNISHMENT = "Du har fået %d sekunders straf for det seneste hint";
	public final static String LANG_STATUS_GAME_COMPLETED = "Tillykke - du gennemførte spillet på %d sekunder!";
	public final static String LANG_STATUS_GAME_GIVE_UP = "Du gav op efter %d sekunder";
	
	public final static String LANG_TEXT_HIGHSCORES_NONE = "Endnu ingen highscores";
	public final static String LANG_TEXT_HIGHSCORES_GZ = "Tillykke!\nDu gennemførte pladen på %d sekunder.\n";
	public final static String LANG_TEXT_HIGHSCORES_NEW_RECORD = "Du kom ind på en ny %d. plads på denne PC.. Godt gået!\n\n";
	public final static String LANG_TEXT_HIGHSCORES_NO_RECORD = "Desværre var det ikke nok til at slå en rekord i denne omgang.. Prøv igen!\n\n";
	
	public final static String LANG_TEXT_RULE_HEADER = "Reglerne for Sudoku";
	public final static String LANG_TEXT_RULE_CONTAINER = "<HTML>Du skal fylde tal i de tomme<br />felter i et kvadrat, der består af 9 gange 9 felter.<br /><br />Nogle af felterne er udfyldt på forhånd.<br />Kunsten er at krible alle tallene på plads, så hvert tal kun bruges én gang i hver række, kolonne og mini-kvadrat på 3 gange 3 felter.<br /><br />Du kan til enhver tid trykke på Hint-knappen, og få udfyldt et tilfældigt nummer på pladen.<br />Dette koster %s sekunder.<br /><br />Ved tryk på 'Giv op' afsluttes pladen, og din tid.<br /><br />Held og lykke! :-)</HTML>";
	public final static String LANG_TEXT_TIME_SPENT = "Tid brugt: %s";
	
	public final static String LANG_TOPSCORE_FILE_CANT_READ = "Kan ikke læse \ntopscore filen";
	public final static String LANG_TOPSCORE_FILE_CANT_WRITE = "Kan ikke skrive til\ntopscore filen";

}
