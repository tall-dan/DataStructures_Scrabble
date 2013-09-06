package scrabble;

/**
 * Stores the index and letter of a letter space that can be used with the
 * dictionary to find possible words.
 * 
 * @author team 23: Ethan Veatch, Chris Good, and Dan Schepers Created May 19,
 *         2011.
 */
public class LetterSpacingForDictionary {
	private int index;
	private char letter;

	/**
	 * Constructs a LetterSpacingForDictionary object with the given parameters
	 * 
	 * @param index
	 * @param letter
	 */
	public LetterSpacingForDictionary(int index, char letter) {
		this.index = index;
		this.letter = letter;
	}

	/**
	 * Constructs a LetterSpacingForDictionary object from two BoardSpace
	 * objects
	 * 
	 * @param required
	 * @param desired
	 */
	public LetterSpacingForDictionary(BoardSpace required, BoardSpace desired) {
		if (required.getxPos() == desired.getxPos()) {
			this.index = desired.getyPos() - required.getyPos();
			this.letter = desired.getOccupant();
		} else if (required.getyPos() == desired.getyPos()) {
			this.index = desired.getxPos() - required.getxPos();
			this.letter = desired.getOccupant();
		} else {
			System.out
					.println("There's a problem in letterSpacingForDic. required and desired aren't in the same row or column");
		}
	}

	/**
	 * Returns the index
	 * 
	 * @return index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Sets the index to the given int
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Returns the letter stored in the object
	 * 
	 * @return the letter
	 */
	public char getLetter() {
		return this.letter;
	}

	/**
	 * sets the letter to the given char
	 * 
	 * @param letter
	 */
	public void setLetter(char letter) {
		this.letter = letter;
	}

	@Override
	public String toString() {
		return this.letter + " at " + this.index;
	}

}
