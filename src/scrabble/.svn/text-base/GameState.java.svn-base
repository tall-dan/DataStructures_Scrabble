package scrabble;

import java.util.ArrayList;

/**
 * Contains the results of the last play (board, hand, tile bag, score info).
 * You may add fields (and corresponding parameters to the constructor) and add
 * new methods. You should not remove any fields or modify the existing public
 * methods.
 * 
 * @author Team 23: Chris Good, Ethan Veatch, and Dan Schepers
 * 
 */
public class GameState {

	private ArrayList<Character> tilesInHand; // The contents of the player's
	// hand after the play.
	private int totalScore;
	private int scoreThisPlay;
	private char[][] boardChars;
	private ArrayList<Character> tileBag;
	private String lastWordPlayed;

	/**
	 * Constructor.
	 * 
	 * @param tilesInHand
	 *            - the current tiles in the player's had
	 * @param scoreThisPlay
	 *            - the score earned on the this move
	 * @param totalScore
	 *            - the score earned overall
	 * @param boardChars
	 *            - current tiles on the board
	 * @param tileBag
	 *            - tiles to be drawn from
	 * @param lastWordPlayed
	 *            contains entire word, which may be a "superstring" of the the
	 *            characters actually played on this Turn. If one of the
	 *            characters in the word is played by a blank, it should be in
	 *            lowercase. e.g. If character 2 in XENIA is a blank that
	 *            represents N, the value of this parameter should be XEnIA
	 */
	public GameState(ArrayList<Character> tilesInHand, int totalScore,
			int scoreThisPlay, char[][] boardChars,
			ArrayList<Character> tileBag, String lastWordPlayed) {
		super();
		this.tilesInHand = tilesInHand;
		this.totalScore = totalScore;
		this.scoreThisPlay = scoreThisPlay;
		this.boardChars = boardChars;
		this.tileBag = tileBag;
		this.lastWordPlayed = lastWordPlayed;
	}

	/**
	 * Clones a GameState
	 * 
	 * @param gs
	 */
	public GameState(GameState gs) {
		this.tilesInHand = new ArrayList<Character>(gs.getTilesInHand());
		this.totalScore = gs.getTotalScore();
		this.scoreThisPlay = gs.getScoreThisPlay();
		this.boardChars = new char[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				this.boardChars[i][j] = gs.boardChars[i][j];
			}
		}
		this.tileBag = new ArrayList<Character>(gs.getTileBag());
		this.lastWordPlayed = gs.getLastWordPlayed();
	}

	/**
	 * @return current tiles on the board
	 */
	public char[][] getBoardChars() {
		return this.boardChars;
	}

	/**
	 * @return the score earned on the this move
	 */
	public int getScoreThisPlay() {
		return this.scoreThisPlay;
	}

	/**
	 * @return the current tiles in hand
	 */
	public ArrayList<Character> getTilesInHand() {
		return this.tilesInHand;
	}

	/**
	 * @return the score earned overall
	 */
	public int getTotalScore() {
		return this.totalScore;
	}

	/**
	 * @return tiles to be drawn from
	 */
	public ArrayList<Character> getTileBag() {
		return this.tileBag;
	}

	/**
	 * @return the lastWordPlayed
	 */
	public String getLastWordPlayed() {
		return this.lastWordPlayed;
	}
}
