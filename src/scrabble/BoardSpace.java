package scrabble;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents and stores all information about squares on the board
 * 
 * @author Ethan Veatch, Chris Good, and Daniel Schepers Created May 16, 2011.
 */
public class BoardSpace {

	private int xPos;
	private int yPos;
	private char multiplier;
	private char occupant = (char) 0;
	/**
	 * Tells if the Space was originally a blank tile.
	 */
	protected boolean isBlankTile = false;
	private HashSet<String> verticalWordsPlayable = new HashSet<String>();
	private HashSet<String> horizontalWordsPlayable = new HashSet<String>();
	/**
	 * A modifiable board layout
	 */
	public static char[][] boardLayout = new char[][] {
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
	/**
	 * The default point values of the letters
	 */
	public static final int[] DEFAULT_POINT_VALUES = { 1, 3, 3, 2, 1, 4, // A-Z,
			// then
			// the
			// blank
			2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0 };

	/**
	 * Creates a boardSpace
	 * 
	 * @param xPos
	 * @param yPos
	 * @param occupant
	 */
	public BoardSpace(int xPos, int yPos, char occupant) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.multiplier = findMultiplier();
		this.occupant = occupant;
		if (occupant == 'b') {
			this.isBlankTile = true;
		}
	}

	/**
	 * Constructs a new BoardSpace object using a BoardSpace object;
	 * 
	 * @param space
	 */
	public BoardSpace(BoardSpace space) {
		// this.xPos = space.getxPos();
		// this.yPos = space.getyPos();
		// this.multiplier = space.findMultiplier();
		// this.occupant = space.getOccupant();
		// if (occupant == 'b') {
		// this.isBlankTile = true;
		this(space.getxPos(), space.getyPos(), space.getOccupant());

		// this.horizontalWordsPlayable = new
		// HashSet<String>(space.getHorizontalWordsPlayable());
		// this.verticalWordsPlayable = new
		// HashSet<String>(space.getVerticalWordsPlayable());
	}

	/**
	 * Score the boardTile
	 * 
	 * @return score
	 */
	public int getScore() {
		if (this.multiplier == 'd') {
			// Sets the multiplier to not multiplying
			BoardSpace.boardLayout[this.xPos][this.yPos] = ' ';
			return this.getPoints() * 2;
		} else if (this.multiplier == 't') {
			// Sets the multiplier to no longer multiply
			BoardSpace.boardLayout[this.xPos][this.yPos] = ' ';
			return this.getPoints() * 3;
		} else {
			// Don't do any multiplying
			return this.getPoints();
		}
	}

	/**
	 * Finds the tile in this boardSpace
	 * 
	 * @return the occupant
	 */
	public char getOccupant() {
		return this.occupant;
	}

	/**
	 * put a tile in this boardSpace
	 * 
	 * @param occupant
	 */
	public void setOccupant(char occupant) {
		this.occupant = occupant;

	}

	/**
	 * find the default point value of the tile contained in this boardSpace
	 * 
	 * @return the default point value of the tile contained in this boardSpace
	 */
	public int getPoints() {
		if (this.isBlankTile)
			return 0;
		// Finds the position in the default point values table -65 to correct
		// for ascii
		return DEFAULT_POINT_VALUES[this.occupant - 65];
	}

	/**
	 * Finds the multiplier for the boardspace's position
	 * 
	 * @return the multiplier
	 */
	public char findMultiplier() {
		return BoardSpace.boardLayout[this.xPos][this.yPos];
	}

	/**
	 * What is this boardSpace's yPosition
	 * 
	 * @return the yPosition
	 */
	public int getyPos() {
		return this.yPos;
	}

	/**
	 * What is this boardSpace's xPosition
	 * 
	 * @return the xPosition
	 */
	public int getxPos() {
		return this.xPos;
	}

	/**
	 * Does this boardSpace have a tile in it?
	 * 
	 * @return empty or filled
	 */
	public boolean isOccupied() {
		// It is occupied if the occupant is not ascii 0 or 32
		return !(this.occupant == 0 || this.occupant == 32);
	}

	@Override
	public String toString() {
		return this.occupant + " at (" + this.xPos + "," + this.yPos + ")";
	}

	/**
	 * Compares two boardSpaces based on xPosition, yPosition, and occupant
	 * 
	 * @param space
	 * @return equality
	 */

	public boolean isEqual(BoardSpace space) {
		return (this.xPos == space.getxPos() && this.yPos == space.getyPos() && this.occupant == space
				.getOccupant());
	}

	

	/**
	 * Sets the vertical words playable
	 * 
	 * @param playable
	 */
	public void setVerticalWordsPlayable(HashSet<String> playable) {
		this.verticalWordsPlayable = playable;

	}

	/**
	 * Gets the vertical words playable
	 * 
	 * @param playable
	 * @return the vertical words playable
	 */
	public HashSet<String> getVerticalWordsPlayable() {
		return this.verticalWordsPlayable;
	}

	/**
	 * Sets the field called 'horizontalWordsPlayable' to the given value.
	 * 
	 * @param playable
	 *            The horizontalWordsPlayable to set.
	 */
	public void setHorizontalWordsPlayable(HashSet<String> playable) {
		this.horizontalWordsPlayable = playable;

	}

	/**
	 * Returns the value of the field called 'horizontalWordsPlayable'.
	 * 
	 * @return Returns the horizontalWordsPlayable.
	 */
	public HashSet<String> getHorizontalWordsPlayable() {
		return this.horizontalWordsPlayable;
	}

	/**
	 * Sets the field called 'isBlankTile' to the given value.
	 * 
	 * @param isBlankTile
	 *            The isBlankTile to set.
	 */
	public void setBlankTile(boolean isBlankTile) {
		this.isBlankTile = isBlankTile;
	}

	/**
	 * Converts the arraylist of boardspaces to an arary of characters
	 * 
	 * @param spaces
	 * @return an ArrayList
	 */
	public ArrayList<Character> toArrayList(ArrayList<BoardSpace> spaces) {
		ArrayList<Character> toReturn = new ArrayList<Character>();
		for (BoardSpace space : spaces) {
			toReturn.add(space.getOccupant());
		}
		return toReturn;
	}

	/**
	 * Gets the best possible play
	 * 
	 * @return the best play
	 */
	public ScrabblePlay getBestPlay() {
		int bestScore = 0;
		int blankTiles=0;
		for (Character tile : Scrabble.tilesInHand) {
			if (tile=='b')
				blankTiles++;
		}
		ScrabblePlay bestPlay = null;
		for (String string : this.horizontalWordsPlayable) {
			ScrabblePlay play = new ScrabblePlay(this, string, false,
					Scrabble.boardPieces, BoardSpace.boardLayout, Scrabble.tilesInHand,blankTiles
					);

			int playScore = play.getScore();
			if (playScore > bestScore) {
				bestPlay = play;
				bestScore = playScore;
			}
		}
		
		for (String string : this.verticalWordsPlayable) {
			ScrabblePlay play = new ScrabblePlay(this, string, true, Scrabble.boardPieces,
					BoardSpace.boardLayout, Scrabble.tilesInHand,blankTiles);
			int playScore = play.getScore();
			if (playScore > bestScore) {
				bestPlay = play;
				bestScore = playScore;
			}
			play.printPlay();
		}
		return bestPlay;
	}
}
