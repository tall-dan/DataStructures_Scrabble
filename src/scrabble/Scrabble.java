package scrabble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.Collections;

import javax.swing.JOptionPane;

/**
 * Maintains the state of the game, checks for legal plays, calculates scores,
 * finds best plays.
 * 
 * 
 * @author Dan Schepers, Ethan Veatch, Chris Good
 * 
 */

// Because we make an 2D array of HashSet<String>'s. This keeps us from
// needing to cast items each time.
public class Scrabble {

	/**
	 * Default board size
	 */
	public static final int DEFAULT_BOARD_SIZE = 15;
	/**
	 * Default maximum number of tiles in hand
	 */
	public static final int DEFAULT_MAX_HAND_SIZE = 7;
	/**
	 * Default board configuration
	 */
	public static final char[][] DEFAULT_BOARD_LAYOUT = new char[][] {
			{ 'T', ' ', ' ', 'd', ' ', ' ', ' ', 'T', ' ', ' ', ' ', 'd', ' ',
					' ', 'T' },
			{ ' ', 'D', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					'D', ' ' },
			{ ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'D',
					' ', ' ' },
			{ 'd', ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', ' ', ' ', 'D', ' ',
					' ', 'd' },
			{ ' ', ' ', ' ', ' ', 'D', ' ', ' ', ' ', ' ', ' ', 'D', ' ', ' ',
					' ', ' ' },
			{ ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					't', ' ' },
			{ ' ', ' ', 'd', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'd',
					' ', ' ' },
			{ 'T', ' ', ' ', 'd', ' ', ' ', ' ', '*', ' ', ' ', ' ', 'd', ' ',
					' ', 'T' },
			{ ' ', ' ', 'd', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'd',
					' ', ' ' },
			{ ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					't', ' ' },
			{ ' ', ' ', ' ', ' ', 'D', ' ', ' ', ' ', ' ', ' ', 'D', ' ', ' ',
					' ', ' ' },
			{ 'd', ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', ' ', ' ', 'D', ' ',
					' ', 'd' },
			{ ' ', ' ', 'D', ' ', ' ', ' ', 'd', ' ', 'd', ' ', ' ', ' ', 'D',
					' ', ' ' },
			{ ' ', 'D', ' ', ' ', ' ', 't', ' ', ' ', ' ', 't', ' ', ' ', ' ',
					'D', ' ' },
			{ 'T', ' ', ' ', 'd', ' ', ' ', ' ', 'T', ' ', ' ', ' ', 'd', ' ',
					' ', 'T' } };
	/*
	 * T = tripleWordScore D = doubleWordScore t = tripleLetterScore d =
	 * doubleLetterScore
	 */

	/**
	 * Array storing the standard letter point values A-Z (blank is at the end).
	 */
	public static final int[] DEFAULT_POINT_VALUES = { 1, 3, 3, 2, 1, 4, // A-Z,
			// then
			// the
			// blank

			2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0 };

	/**
	 * Array storing the standard number of tiles for each letter A-Z (blank is
	 * at the end)
	 */
	public static final int[] DEFAULT_LETTER_COUNTS = { 9, 2, 2, 4, 12, 2, // A-Z,
			// then
			// the
			// blank
			3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2 };

	private static final char blankTile = 'b';
	private boolean isFirstWord = true;
	private static char clickedTile;
	/**
	 * Keeps track of the letters the user has added to the board
	 */
	protected static ArrayList<BoardSpace> lettersPlayed = new ArrayList<BoardSpace>();

	// The fields below are not necessarily required fields. As long as your Get
	// methods below return something
	// equivalent to these fields, you may change these fields around. If you
	// do, you may need to adjust the GUI code.

	/**
	 *The full dictionary to be used
	 */
	protected static ScrabbleDictionary dictionary;
	/**
	 * A 15X15 board
	 */
	static int boardSize; // Number of rows (equals number of columns
	private char[][] boardConfiguration; // Where are the double and triple
	// letters and words?
	// private char[][] boardChars; // Current letters played on the board
	private ArrayList<Character> tileBag;
	/**
	 * A list of boardSpace objects
	 */
	static BoardSpace[][] boardPieces;
	private int maxHandSize; // Maximum # of tiles player can have in hand at
	// once.
	/**
	 * A list of the tiles in the user's hand
	 */
	static ArrayList<Character> tilesInHand; // Current tiles in the player's
	private static String previousLettersPlayedInWord;
	/**
	 * The last letters played
	 */
	protected static ArrayList<BoardSpace> lastLettersPlayed = new ArrayList<BoardSpace>();
	/**
	 * the letters played on this turn
	 */
	protected static ArrayList<BoardSpace> newLettersPlayed = new ArrayList<BoardSpace>();
	/**
	 * The letters the user selected to exchange
	 */
	protected static ArrayList<Character> toExchange = new ArrayList<Character>();
	// hand.
	/**
	 * The score for the last word played
	 */
	static int scoreThisTurn;
	/**
	 * Total score
	 */
	static int totalScore;
	private String lastWordPlayed;
	private boolean isVertical;
	private ArrayList<String> adjacentWords;

	private boolean computerPlay;
	private ArrayList<BoardSpace> occupiedBoardSpaces = new ArrayList<BoardSpace>();
	private int numBlankTiles;

	/**
	 * Perform initializations common to all Scrabble games.
	 */
	public Scrabble() {
		Scrabble.scoreThisTurn = 0;
		Scrabble.totalScore = 0;
		this.lastWordPlayed = "";
	}

	/**
	 * Creates a new standard scrabble game with random tile bag
	 * 
	 * @param dictionary
	 * @throws Exception
	 */
	public Scrabble(ScrabbleDictionary dictionary) throws Exception {
		this(dictionary, (ArrayList<Character>) null);
	}

	/**
	 * Create a new scrabble game in which no plays have been made.
	 * 
	 * @param dictionary
	 *            - dictionary to be used in this instance of Scrabble
	 * @param tileBag
	 *            - tiles to be drawn from
	 */
	public Scrabble(ScrabbleDictionary dictionary, ArrayList<Character> tileBag) {
		this();
		Scrabble.dictionary = dictionary;
		Scrabble.boardSize = Scrabble.DEFAULT_BOARD_SIZE;
		this.boardConfiguration = Scrabble.DEFAULT_BOARD_LAYOUT;
		this.maxHandSize = Scrabble.DEFAULT_MAX_HAND_SIZE;
		// this.boardChars = new char[Scrabble.boardSize][Scrabble.boardSize];

		Scrabble.boardPieces = new BoardSpace[Scrabble.boardSize][Scrabble.boardSize];
		for (int i = 0; i < Scrabble.boardSize; i++) {
			for (int j = 0; j < Scrabble.boardSize; j++) {
				Scrabble.boardPieces[i][j] = new BoardSpace(i, j, (char) 0);
			}
		}

		if (tileBag == null) {
			
			this.tileBag=new ArrayList<Character>();
			
			for (int i = 0; i < Scrabble.DEFAULT_LETTER_COUNTS.length - 1; i++) { // don't
				// include
				// blanks
				// here.
				for (int j = 0; j < Scrabble.DEFAULT_LETTER_COUNTS[i]; j++) {
					this.tileBag.add((char) ('A' + i));
				}
			}
			for (int i = 0; i < Scrabble.DEFAULT_LETTER_COUNTS[Scrabble.DEFAULT_LETTER_COUNTS.length - 1]; i++) {
				this.tileBag.add(Scrabble.blankTile);
				// DONE: Shuffle this tileBag.
			}
			shuffle();
		} else {
			this.tileBag = tileBag;
		}
		Scrabble.tilesInHand = new ArrayList<Character>();
		for (int i = 0; i < this.maxHandSize; i++) {
			this.drawTile();
		} //replaced by the shit below
//		Character[] array={'c','r','t','s','u','t','a'};
//		for (int i =0;i<array.length;i++){
//			Scrabble.tilesInHand.add(array[i]);
//			this.tileBag.remove(this.tileBag.size()-1);
		//how set letters that you want in your hand
	}

	/**
	 * Move last tile in tileBag to the player's hand
	 */
	public void drawTile() {
		Scrabble.tilesInHand.add(this.tileBag.remove(this.tileBag.size() - 1));
	}

	/**
	 * Creates a Scrabble object using the data in the given .scrabble file (the
	 * state of the game at any given moment).
	 * 
	 * The format of the .scrabble file must follow the example shown in the
	 * specification document.
	 * 
	 * @param dictionary
	 *            A Scrabble dictionary
	 * @param fileName
	 *            name (must end in '.scrabble')
	 * @throws IOException
	 *             if the given file name is not found or does not end in
	 *             scrabble
	 */
	public Scrabble(ScrabbleDictionary dictionary, String fileName)
			throws IOException {
		this();
		Scrabble.dictionary = dictionary;

		// reader for the input test file
		BufferedReader myReader;

		// verifies that it is a .scrabble file and the file exists
		// String[] check = input.split(".");
		// if(check[1].equals("scrabble")){
		myReader = new BufferedReader(new FileReader(new File(fileName)));
		// }
		// else {
		// throw new IOException("Not a .scrabble file");
		// }

		// Gets the handsize, boardsize, tilesInHand, & sDictionary from the
		// first line of a test file
		String line = myReader.readLine();
		String[] line1 = line.split(" ");
		this.maxHandSize = Integer.parseInt(line1[0]);
		Scrabble.boardSize = Integer.parseInt(line1[1]);
		Scrabble.tilesInHand = new ArrayList<Character>();
		for (int i = 0; i < line1[2].length(); i++) {
			Scrabble.tilesInHand.add(line1[2].charAt(i));
		}
		Scrabble.totalScore = Integer.parseInt(line1[3]);

		// Sets the boards point square layout from the test file
		this.boardConfiguration = new char[Scrabble.boardSize][Scrabble.boardSize];
		for (int i = 0; i < Scrabble.boardSize; i++) {
			line = myReader.readLine();
			for (int j = 0; j < Scrabble.boardSize; j++) {
				this.boardConfiguration[i][j] = line.charAt(j);
			}
		}

		// gets the current setup on the board (which letters have been played
		// where)
		// this.boardChars = new char[Scrabble.boardSize][Scrabble.boardSize];
		// //not
		// curently used
		Scrabble.boardPieces = new BoardSpace[Scrabble.boardSize][Scrabble.boardSize];
		for (int i = 0; i < Scrabble.boardSize; i++) {
			line = myReader.readLine();
			for (int j = 0; j < Scrabble.boardSize; j++) {
				// this.boardChars[i][j] = line.charAt(j);
				Scrabble.boardPieces[i][j] = new BoardSpace(i, j, line
						.charAt(j));
			}
		}

		// gets the provided tile bag (the last line in the scrabble file)
		line = myReader.readLine();
		this.tileBag = new ArrayList<Character>();
		for (int i = 0; i < line.length(); i++) {
			this.tileBag.add(line.charAt(i));
		}
	}

	/**
	 * Accessor method for Scrabble's currentGameState field.
	 * 
	 * @return the current GameState in the Scrabble class.
	 */
	public GameState getCurrentGameState() {
		return new GameState(Scrabble.tilesInHand, Scrabble.totalScore,
				Scrabble.scoreThisTurn, getBoardChars(), this.tileBag,
				this.lastWordPlayed);
	}

	/**
	 * Make the computers move if there are no tiles on the board.
	 * 
	 * @param requiredChar
	 */
	protected void playFirstTimeComputer() {
		Scrabble.lettersPlayed.clear();
		String toUse = getBestFirstComputer();
		for (int i = 0; i < toUse.length(); i++) {
			boardPieces[7][7 + i].setOccupant(toUse.charAt(i));
			Scrabble.lettersPlayed.add(boardPieces[7][7 + i]);
			Scrabble.tilesInHand.remove(Scrabble.tilesInHand.indexOf(toUse
					.charAt(i)));

		}
		ScrabbleBoard.changeCharactersOnBoard(Scrabble.getBoardChars());
		Scrabble.previousLettersPlayedInWord = "";
		Scrabble.newLettersPlayed = new ArrayList<BoardSpace>(
				Scrabble.lettersPlayed);
		this.adjacentWords = new ArrayList<String>();
	}

	private String getBestFirstComputer() {
		char requiredChar = 0;
		for (int i = 0; i < Scrabble.tilesInHand.size(); i++) {
			if (Scrabble.tilesInHand.get(i) != 'b'
					&& Scrabble.DEFAULT_POINT_VALUES[Scrabble.tilesInHand
							.get(i) - 65] > requiredChar) {
				requiredChar = Scrabble.tilesInHand.get(i);
			}
		}
		HashSet<Character> characters = new HashSet<Character>();
		int numBlankTiles = 0;
		for (Character letter : Scrabble.tilesInHand) {
			if (letter == 'b') {
				numBlankTiles++;
			} else {
				characters.add(letter);
			}
		}
		Scrabble.dictionary.containsOnly(characters, this.numBlankTiles);
		String toUse = "";
		HashSet<String> smallDic = Scrabble.dictionary.makeDicSmaller(
				Scrabble.tilesInHand, Scrabble.dictionary.getPossibleWords(),
				Scrabble.tilesInHand.get(0),
				new ArrayList<LetterSpacingForDictionary>(), numBlankTiles);
		for (String word : smallDic) {
			if (word.length() > toUse.length()) {
				toUse = word;
			}
		}
		return toUse;
	}

	/**
	 * Will play one turn in the Scrabble game and return a new GameState that
	 * contains the values that have changed during play. See the assignment
	 * document for more specifics.
	 * 
	 * @return a GameState containing the changed values after a single turn.
	 * @throws InvalidKeyException
	 */
	public GameState playOnce() {
		this.numBlankTiles = 0;
		// int max = 0;
		// char requiredChar = 0;
		for (char letter : Scrabble.tilesInHand) {
			if (letter == 'b') {
				this.numBlankTiles++;
			}
		}
		this.computerPlay = true;
		Scrabble.dictionary.containsOnly(getListOfChars(), this.numBlankTiles);
		// HashSet<String> playableWordsAtLocation;
		int j;
		ArrayList<BoardSpace> verticalSpaces = new ArrayList<BoardSpace>();
		ArrayList<BoardSpace> horizontalSpaces = new ArrayList<BoardSpace>();
		for (int i = 0; i < 15; i++) {
			verticalSpaces.clear();
			horizontalSpaces.clear();
			for (j = 0; j < 15; j++) {
				if (Scrabble.boardPieces[i][j].isOccupied()) {
					this.occupiedBoardSpaces.add(Scrabble.boardPieces[i][j]);
					horizontalSpaces.add(Scrabble.boardPieces[i][j]);
				}
				if (Scrabble.boardPieces[j][i].isOccupied())
					verticalSpaces.add(Scrabble.boardPieces[j][i]);
			}
			if (!horizontalSpaces.isEmpty())
				populateHorizontalPossibilities(horizontalSpaces);
			if (!verticalSpaces.isEmpty())
				populateVerticalPossibilities(verticalSpaces);
		}
		int before = Scrabble.tilesInHand.size();
		if (!playBestPlay()) {
			return null;
		}
		int after = Scrabble.tilesInHand.size();
		for (int i = 0; i < before - after; i++) {
			drawTile();
		}
		this.computerPlay = false;
		return getCurrentGameState();
	}

	/**
	 * Populates the vertical possibilities
	 * 
	 * @param verticalSpaces
	 * 
	 * 
	 */
	private void populateVerticalPossibilities(
			ArrayList<BoardSpace> verticalSpaces) {
		ArrayList<LetterSpacingForDictionary> verticalLetters = new ArrayList<LetterSpacingForDictionary>();
		for (BoardSpace space : verticalSpaces) {
			verticalLetters.clear();
			for (int k = 0; k < verticalSpaces.size(); k++) {
				if (verticalSpaces.get(k) != space)
					verticalLetters.add(new LetterSpacingForDictionary(space,
							verticalSpaces.get(k)));
			}
			ArrayList<Character> handAndTiles = space
					.toArrayList(verticalSpaces);
			if (!handAndTiles.addAll(Scrabble.tilesInHand)) {
				System.out.println("Problem in populate vertical letters");
			}
			HashSet<String> playable = Scrabble.dictionary
					.makeDicSmaller(handAndTiles, Scrabble.dictionary
							.getPossibleWords(), space.getOccupant(),
							verticalLetters, this.numBlankTiles);
			space.setVerticalWordsPlayable(playable);
		}

	}

	/**
	 * Populates the horizontal Possibilities
	 * 
	 * @param horizontalSpaces
	 */
	private void populateHorizontalPossibilities(
			ArrayList<BoardSpace> horizontalSpaces) {
		ArrayList<LetterSpacingForDictionary> horizontalLetters = new ArrayList<LetterSpacingForDictionary>();
		for (BoardSpace space : horizontalSpaces) {
			horizontalLetters.clear();
			for (int k = 0; k < horizontalSpaces.size(); k++) {
				if (horizontalSpaces.get(k) != space)
					horizontalLetters.add(new LetterSpacingForDictionary(space,
							horizontalSpaces.get(k)));
			}
			ArrayList<Character> handAndTiles = space
					.toArrayList(horizontalSpaces);
			if (!handAndTiles.addAll(Scrabble.tilesInHand)) {
				System.out.println("Problem in populate horizontal letters");
			}
			space.setHorizontalWordsPlayable(Scrabble.dictionary
					.makeDicSmaller(handAndTiles, Scrabble.dictionary
							.getPossibleWords(), space.getOccupant(),
							horizontalLetters, this.numBlankTiles));
		}

	}

	private boolean playBestPlay() {
		ScrabblePlay bestPlay = null;
		int highScore = -1;
		for (BoardSpace space : this.occupiedBoardSpaces) {
			ScrabblePlay play = space.getBestPlay();
			if (play != null) {
				if (play.getScore() > highScore) {
					highScore = play.getScore();
					bestPlay = play;
				}
			}
		}
		if (bestPlay == null) {
			return false;
		}
		System.out.println(bestPlay);
		bestPlay.playIt();
		return true;
	}

	private HashSet<Character> getListOfChars() {
		char[][] boardCharacters = getBoardChars();
		HashSet<Character> chars = new HashSet<Character>();
		for (int i = 0; i < boardCharacters.length; i++) {
			for (int j = 0; j < boardCharacters.length; j++) {
				chars.add(boardCharacters[i][j]);
			}
		}
		for (Character character : Scrabble.tilesInHand) {
			chars.add(character);
		}
		return chars;
	}

	/**
	 * Will play the entire game and return an ArrayList of GameStates that
	 * represent each step of play from the end of the next play until the end
	 * of the game.
	 * 
	 * @return ArrayList of GameState objects representing each step in the
	 *         game.
	 */
	public ArrayList<GameState> playGame() {
		/*
		 * While there are tiles left in the computer player's hand and there
		 * are valid moves left on the board, playOnce(). Then, return the
		 * arraylist of gamestates that were returned by playOnce.
		 */
		ArrayList<GameState> states = new ArrayList<GameState>();

		GameState state;
		while ((state = this.playOnce()) != null) {
			states.add(state);
		}

		return states;
	}

	/**
	 * Plays toPlay (in the order given), starting at zero-based position
	 * (rowStart, colStart). Plays vertically if vertical==true, otherwise plays
	 * horizontally. If there is already a letter in a square in which we try to
	 * place a tile, skip over it and place that tile in the next available
	 * space.
	 * 
	 * @param toPlay
	 * @param rowStart
	 * @param colStart
	 * @param vertical
	 * @return the new state of the game after this play (including replacing
	 *         player's tiles from the tileBag.
	 * @throws IllegalArgumentException
	 *             if the play tries to make an illegal word.
	 * @throws IndexOutOfBoundsException
	 *             if the placement is illegal (some tiles would be outside the
	 *             board, first play does not include the center square,
	 *             subsequent play does not touch any previously-played letters.
	 * 
	 */
	public GameState playOnceHuman(String toPlay, int rowStart, int colStart,
			boolean vertical) throws IllegalArgumentException,
			IndexOutOfBoundsException {
		// See done button

		return null;
	}

	/**
	 * Takes in a certain number of tiles, x, that should be removed from the
	 * users hand and placed back in the tileBag. The tileBag must then be
	 * shuffled and the last x tiles placed into the hand.
	 * 
	 * @param toExchange
	 * 
	 */
	public void returnTiles(ArrayList<Character> toExchange) {
		System.out.println(toExchange.toString());
		System.out.println(Scrabble.tilesInHand.toString());
		for (int i = 0; i < toExchange.size(); i++) {
			this.tileBag.add(toExchange.get(i));

			Scrabble.tilesInHand.remove(Scrabble.tilesInHand.indexOf(toExchange
					.get(i)));

		}
		System.out.println(Scrabble.tilesInHand.toString());
		shuffle();
		for (@SuppressWarnings("unused")
		char c : toExchange)
			drawTile();
	}

	/**
	 * Should shuffle this game's tileBag into a new random order.
	 */
	public void shuffle() {
		Collections.shuffle(this.tileBag);
	}

	/**
	 * Returns a new ScrabbleDictionary created by reading the file specified in
	 * the fileName parameter.
	 * 
	 * @param fileName
	 *            - the name of the file in which the dictionary is located.
	 * @return a new ScrabbleDictionary
	 */
	public static ScrabbleDictionary createDictionary(String fileName) {
		return new ScrabbleDictionary(fileName);
	}

	/**
	 * @return the characters currently on the board
	 */
	public static char[][] getBoardChars() {
		char[][] boardChars = new char[Scrabble.boardSize][Scrabble.boardSize];
		for (int i = 0; i < Scrabble.boardSize; i++) {
			for (int j = 0; j < Scrabble.boardSize; j++) {
				boardChars[i][j] = Scrabble.boardPieces[i][j].getOccupant();
			}
		}

		return boardChars;
	}

	/**
	 * @return the board's current configuration of point squares
	 */
	public char[][] getBoardConfiguration() {
		return this.boardConfiguration;
	}

	/**
	 * @return the size of the board (this value must be odd)
	 */
	public int getBoardSize() {
		return Scrabble.boardSize;
	}

	/**
	 * @return the maximum number of tiles a user can have in hand at any one
	 *         time.
	 */
	public int getMaxHandSize() {
		return this.maxHandSize;
	}

	/**
	 * @return score received on a single turn
	 */
	public int getScoreThisTurn() {
		return Scrabble.scoreThisTurn;
	}

	/**
	 * @return the tileBag containing all letters not yet played
	 */
	public ArrayList<Character> getTileBag() {
		return this.tileBag;
	}

	/**
	 * @return an array of characters representing the tiles currently in the
	 *         user's hand.
	 */
	public ArrayList<Character> getTilesInHand() {
		return Scrabble.tilesInHand;
	}

	/**
	 * @return the overall game score
	 */
	public int getTotalScore() {
		return Scrabble.totalScore;
	}

	/**
	 * @return the current ScrabbleDictionary
	 */
	public ScrabbleDictionary getDictionary() {
		return Scrabble.dictionary;
	}

	/**
	 * @return the lastWordPlayed
	 */
	public String getLastWordPlayed() {
		return this.lastWordPlayed;
	}

	/**
	 * Put a character on the board in the given spot
	 * 
	 * @param xIndex
	 * @param yIndex
	 */
	public static void setBoardSpot(int xIndex, int yIndex) {
		if (!Scrabble.boardPieces[yIndex][xIndex].isOccupied()) {
			BoardSpace space = Scrabble.boardPieces[yIndex][xIndex];
			String bCharacter = "";
			if (Scrabble.clickedTile == 'b') {
				while (true) {

					bCharacter = JOptionPane
							.showInputDialog("Enter a char to replace the blank character:");
					bCharacter = bCharacter.toUpperCase();
					if (bCharacter.length() == 1) {
						break;
					}
				}
				Scrabble.boardPieces[yIndex][xIndex].setBlankTile(true);
				Scrabble.clickedTile = bCharacter.charAt(0);
			}
			space.setOccupant(Scrabble.clickedTile);
			Scrabble.lettersPlayed.add(space);

			if (bCharacter == "") {
				Scrabble.tilesInHand.remove(Scrabble.tilesInHand
						.indexOf(Scrabble.clickedTile));
			} else {
				Scrabble.tilesInHand.remove(Scrabble.tilesInHand.indexOf('b'));
			}
			ScrabbleBoard.changeCharactersOnBoard(Scrabble.getBoardChars());
			UserHandPanel.changeTilesInHand(Scrabble.tilesInHand);

		} else {
			System.out.println("Only add pieces to a empty location");
		}

	}

	/**
	 * Sets the clicked tile
	 * 
	 * @param letter
	 */
	public static void setClickTile(char letter) {
		if (ScrabbleFrame.exchanging) {
			Scrabble.toExchange.add(letter);
		}
		Scrabble.clickedTile = letter;
		System.out.println("Selected Tile: " + Scrabble.clickedTile);
	}

	/**
	 * Evaluates score.
	 * 
	 */
	public void evaluateScore() {
		Scrabble.scoreThisTurn = 0;

		int letterVal;
		int wordMultiplier = 1;
		for (int i = 0; i < Scrabble.lettersPlayed.size(); i++) {
			BoardSpace letter = Scrabble.lettersPlayed.get(i);
			Scrabble.scoreThisTurn += letter.getScore();
			if (letter.findMultiplier() == 'D'
					|| letter.findMultiplier() == '*') {
				wordMultiplier *= 2;
				BoardSpace.boardLayout[letter.getxPos()][letter.getyPos()] = ' ';
			} else if (Scrabble.lettersPlayed.get(i).findMultiplier() == 'T') {
				wordMultiplier *= 3;
				BoardSpace.boardLayout[letter.getxPos()][letter.getyPos()] = ' ';
			}
		}
		int pointVal = 0;
		for (int i = 0; i < Scrabble.previousLettersPlayedInWord.length(); i++) {
			letterVal = Scrabble.previousLettersPlayedInWord.charAt(i) - 65;
			pointVal = Scrabble.DEFAULT_POINT_VALUES[letterVal];
			Scrabble.scoreThisTurn += pointVal;
		}
		Scrabble.scoreThisTurn *= wordMultiplier;
		Scrabble.scoreThisTurn += adjacentWordScore();
		Scrabble.totalScore += Scrabble.scoreThisTurn;
	}

	/**
	 * Returns the score of the adjacent words. Currently does not take into
	 * account the premium squares.
	 * 
	 * @return scores of adjacent words
	 */
	public int adjacentWordScore() {
		int adjacentScore = 0;
		int letterVal;
		int pointVal;
		for (String word : this.adjacentWords) {
			for (char c : word.toCharArray()) {
				letterVal = c - 65;
				pointVal = Scrabble.DEFAULT_POINT_VALUES[letterVal];
				adjacentScore += pointVal;
			}
		}
		return adjacentScore;
	}

	/**
	 * Brings back the old game state
	 * 
	 * @param oldGS
	 */
	public void reverseGameState(GameState oldGS) {
		Scrabble.tilesInHand = oldGS.getTilesInHand();

		for (BoardSpace space : Scrabble.lettersPlayed) {
			space.setOccupant((char) 0);
		}
		if (!Scrabble.boardPieces[7][7].isOccupied()) {
			this.isFirstWord = true;
		}
	}

	/**
	 * Checks if the play made is legal
	 * 
	 * @return if legal
	 */
	public boolean checkLegality() {
		if (checkRowOrColumnIllegal()) {
			return false;
		}
		if (Scrabble.lettersPlayed.size() == 0) {
			return false;
		}
		String word;
		if (this.isVertical) {
			word = getVerticalWord(Scrabble.lettersPlayed.get(0),
					Scrabble.lettersPlayed, Scrabble.boardPieces);
			if (word.length() == 1) {
				word = getHorizontalWord(Scrabble.lettersPlayed.get(0),
						Scrabble.lettersPlayed, Scrabble.boardPieces);
			}

		} else {
			word = getHorizontalWord(Scrabble.lettersPlayed.get(0),
					Scrabble.lettersPlayed, Scrabble.boardPieces);
			if (word.length() == 1) {
				word = getVerticalWord(Scrabble.lettersPlayed.get(0),
						Scrabble.lettersPlayed, Scrabble.boardPieces);
			}
		}
		if (word.length() == Scrabble.lettersPlayed.size() && !this.isFirstWord) {
			System.out.println("You must start on an existant word");
			return false;
		}
		if (word.equals("")) {
			return false;
		}
		this.isFirstWord = false;

		setUpAlternateLettersPlayed(word);
		return Scrabble.dictionary.isAWord(word);
	}

	private void setUpAlternateLettersPlayed(String word) {
		Scrabble.previousLettersPlayedInWord = "";
		int index = 0;
		for (BoardSpace space : Scrabble.lettersPlayed) {
			index = word.indexOf(space.getOccupant());
			word = word.substring(0, index)
					+ word.substring(index + 1, word.length());
		}
		Scrabble.previousLettersPlayedInWord = word;
	}

	private String getHorizontalWord(BoardSpace space,
			ArrayList<BoardSpace> lettersPlayed, BoardSpace[][] boardPieces) {
		Scrabble.newLettersPlayed.clear();
		// space = Scrabble.lettersPlayed.get(0);
		StringBuilder builder = new StringBuilder();
		int numberContained = 0;
		int xPosition = space.getxPos();
		int yPosition = space.getyPos();
		while (xPosition != 0
				&& boardPieces[xPosition - 1][yPosition].isOccupied()) {

			xPosition--;
		}

		while (boardPieces[xPosition][yPosition].isOccupied()
				&& xPosition != 14) {

			for (BoardSpace spaceLoop : lettersPlayed) {
				if (spaceLoop
						.isEqual(Scrabble.boardPieces[xPosition][yPosition])
						&& !this.computerPlay) {
					numberContained++;
				}
			}

			// adds to be changed color
			Scrabble.newLettersPlayed.add(boardPieces[xPosition][yPosition]);

			builder.append(boardPieces[xPosition][yPosition].getOccupant());
			xPosition++;
		}
		if (numberContained != lettersPlayed.size() && !this.computerPlay) {
			return "";
		}
		return builder.toString();
	}

	private String getVerticalWord(BoardSpace space,
			ArrayList<BoardSpace> lettersPlayed, BoardSpace[][] boardPieces) {
		Scrabble.newLettersPlayed.clear();
		StringBuilder builder = new StringBuilder();
		int numberContained = 0;
		int xPosition = space.getxPos();
		int yPosition = space.getyPos();
		while (yPosition > 0
				&& boardPieces[xPosition][yPosition - 1].isOccupied()) {
			yPosition--;
		}
		while (yPosition < 15 && boardPieces[xPosition][yPosition].isOccupied()) {
			// Increments a number if the element is contained. Lets us check to
			// make sure there are no separations.
			for (BoardSpace spaceLoop : lettersPlayed) {
				if (spaceLoop.isEqual(boardPieces[xPosition][yPosition])
						&& !this.computerPlay) {
					numberContained++;
				}
			}
			// adds to be changed color

			Scrabble.newLettersPlayed.add(boardPieces[xPosition][yPosition]);

			builder.append(boardPieces[xPosition][yPosition].getOccupant());
			yPosition++;
		}

		if (numberContained != lettersPlayed.size() && !this.computerPlay) {
			return "";
		}
		return builder.toString();
	}

	private boolean checkRowOrColumnIllegal() {
		if (!Scrabble.boardPieces[7][7].isOccupied()) {// Make we start on the
			// center square
			System.out.println("You must start with the center square");
			return true;
		}

		if (Scrabble.lettersPlayed.size() <= 1) {
			return false;
		} else if (Scrabble.lettersPlayed.get(0).getxPos() == Scrabble.lettersPlayed
				.get(1).getxPos()) {
			for (BoardSpace space : Scrabble.lettersPlayed) {
				if (space.getxPos() != Scrabble.lettersPlayed.get(0).getxPos()) {
					return true;
				}
			}
			this.isVertical = true;
			this.adjacentWords = checkAdjacent(Scrabble.lettersPlayed);
			return false;
		} else if (Scrabble.lettersPlayed.get(0).getyPos() == Scrabble.lettersPlayed
				.get(1).getyPos()) {
			for (BoardSpace space : Scrabble.lettersPlayed) {
				if (space.getyPos() != Scrabble.lettersPlayed.get(0).getyPos()) {
					return true;
				}
			}
			this.isVertical = false;
			this.adjacentWords = checkAdjacent(Scrabble.lettersPlayed);
			return false;
		} else {
			return true;
		}
	}

	// Change to take an arrayList<BoardSpace>
	private ArrayList<String> checkAdjacent(ArrayList<BoardSpace> lettersPlayed) {
		ArrayList<String> adjacentWords = new ArrayList<String>();
		if (this.isVertical) {
			for (BoardSpace space : lettersPlayed) {
				String word = "";
				int xPos = space.getxPos();
				int yPos = space.getyPos();
				while (xPos > 0
						&& Scrabble.boardPieces[xPos - 1][yPos].isOccupied()) {
					xPos--;
				}
				while (xPos < 15
						&& Scrabble.boardPieces[xPos][yPos].isOccupied()) {
					word += Scrabble.boardPieces[xPos][yPos].getOccupant();
					xPos++;
				}
				if (word.length() > 1 && Scrabble.dictionary.isAWord(word)) {
					adjacentWords.add(word);
				}
			}
		} else {
			for (BoardSpace space : lettersPlayed) {
				String word = "";
				int xPos = space.getxPos();
				int yPos = space.getyPos();
				while (yPos > 1
						&& Scrabble.boardPieces[xPos][yPos - 1].isOccupied()) {
					yPos--;
				}
				while (yPos < 15
						&& Scrabble.boardPieces[xPos][yPos].isOccupied()) {
					word += Scrabble.boardPieces[xPos][yPos].getOccupant();
					yPos++;
				}
				if (word.length() > 1 && Scrabble.dictionary.isAWord(word)) {
					adjacentWords.add(word);
				}
			}
		}
		return adjacentWords;
	}
}
