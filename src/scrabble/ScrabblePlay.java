package scrabble;

import java.util.ArrayList;

/**
 * Stores a possible play
 * 
 * @author Team 23 consisting of Ethan Veatch, Daniel Schepers, and Christopher
 *         A Good Created May 19, 2011.
 */
public class ScrabblePlay {
	private int score;
	private boolean isVertical;
	private String word;
	private ArrayList<String> adjacentWords = new ArrayList<String>();
	private BoardSpace space;
	private BoardSpace[][] charBoard;
	private char[][] multiplierBoard;
	private ArrayList<Character> tilesInHand;
	private String horizWord;
	private ArrayList<BoardSpace> newSpaces = new ArrayList<BoardSpace>();
	private String vertWord;
	private int numBlankTiles;

	/**
	 * Constructs a ScrabblePlay object
	 * 
	 * @param space
	 * @param play
	 * @param word
	 * @param isVertical
	 * @param charBoard
	 * @param multiplierBoard
	 * @param tilesInHand
	 * @param blankTiles
	 */
	public ScrabblePlay(BoardSpace space, String play, boolean isVertical,
			BoardSpace[][] charBoard, char[][] multiplierBoard,
			ArrayList<Character> tilesInHand, int blankTiles) {
		this.space = space;
		this.numBlankTiles = blankTiles;
		this.word = play;
		this.isVertical = isVertical;
		this.multiplierBoard = new char[15][15];
		this.charBoard = new BoardSpace[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				this.multiplierBoard[i][j] = multiplierBoard[i][j];
				this.charBoard[i][j] = new BoardSpace(charBoard[i][j]);
			}
		}
		this.multiplierBoard = multiplierBoard;
		this.score = 0;
		this.tilesInHand = new ArrayList<Character>(tilesInHand);
		calculateScore();

	}

	/**
	 * Calculates the score using the position (still to implement) and letter
	 * values.
	 * 
	 * @return Score
	 */
	protected int getScore() {
		return this.score;
	}

	private void calculateScore() {
		if (!this.isVertical) {
			this.score = getHorizontalScore();
		} else {
			this.score = getVerticalScore();
		}
		if (this.newSpaces.size() == Scrabble.tilesInHand.size())
			this.score += 50;
	}

	/**
	 * Places a horizontal word on the board
	 * 
	 * @param before
	 */
	private boolean fillCharBoardHoriz(int start) {
		int x = this.space.getxPos();
		int score = 0;
		int multiplier = 1;
		for (int i = start; i < start + this.word.length(); i++) {
			if (!this.charBoard[x][i].isOccupied()) {
				if (this.tilesInHand.contains(this.word.charAt(i - start))) {
					this.tilesInHand.remove(this.tilesInHand.indexOf(this.word
							.charAt(i - start)));
				} else if (this.numBlankTiles != 0) {
					this.tilesInHand.remove(this.tilesInHand.indexOf('b'));
					this.numBlankTiles--;
				} else
					return false;
				this.charBoard[x][i].setOccupant(this.word.charAt(i - start));
				this.newSpaces.add(this.charBoard[x][i]);
				score += this.charBoard[x][i].getScore();
				if (this.charBoard[x][i].findMultiplier() == 'D') {
					multiplier *= 2;
				} else if (this.charBoard[x][i].findMultiplier() == 'T') {
					multiplier *= 3;
				}
			} else if (this.charBoard[x][i].getOccupant() != this.word.charAt(i
					- start))
				return false;
			else {
				score += this.charBoard[x][i].getScore();
			}
		}
		this.score += (score * multiplier);
		return true;
	}

	private boolean fillCharBoardVert(int start) {
		int y = this.space.getyPos();
		int score = 0;
		int multiplier = 1;
		for (int i = start; i < start + this.word.length(); i++) {
			if (!this.charBoard[i][y].isOccupied()) {
				if (this.tilesInHand.contains(this.word.charAt(i - start))) {
					this.tilesInHand.remove(this.tilesInHand.indexOf(this.word
							.charAt(i - start)));
				} else if (this.numBlankTiles != 0) {
					this.tilesInHand.remove(this.tilesInHand.indexOf('b'));
					this.numBlankTiles--;
				} else
					return false;
				this.charBoard[i][y].setOccupant(this.word.charAt(i - start));
				this.newSpaces.add(this.charBoard[i][y]);
				score += this.charBoard[i][y].getScore();
				if (this.charBoard[i][y].findMultiplier() == 'D') {
					multiplier *= 2;
				} else if (this.charBoard[i][y].findMultiplier() == 'T') {
					multiplier *= 3;
				}
			} else if (this.charBoard[i][y].getOccupant() != this.word.charAt(i
					- start))
				return false;
			else {
				score += this.charBoard[i][y].getScore();
			}
		}
		this.score += (score * multiplier);
		return true;
	}

	/**
	 * Gets the score of any characters after the new characters played
	 * horizontally
	 * 
	 * @param i
	 * @param getyPos
	 * @return
	 */
	private int getHorizWordsAround(int after, int x) {
		ArrayList<Character> word = new ArrayList<Character>();
		// this.afterWord+=this.charBoard[after][y].getOccupant();
		while (after < 14 && this.charBoard[x][after + 1].isOccupied()) {
			after++;
		}
		word.add(this.charBoard[x][after].getOccupant());
		while (after > 0 && this.charBoard[x][after - 1].isOccupied()) {
			word.add(0, this.charBoard[x][after - 1].getOccupant());
			after--;
		}
		this.horizWord = "";

		for (Character character : word) {
			this.horizWord += character;
		}

		return Scrabble.dictionary.isAWord(this.horizWord) ? 0 : -1;
	}

	private int getVertWordsAround(int after, int y) {
		ArrayList<Character> word = new ArrayList<Character>();
		while (after < 14 && this.charBoard[after + 1][y].isOccupied()) {
			after++;
		}
		word.add(this.charBoard[after][y].getOccupant());
		while (after > 0 && this.charBoard[after - 1][y].isOccupied()) {
			word.add(0, this.charBoard[after - 1][y].getOccupant());
			after--;
		}
		this.vertWord = "";

		for (Character character : word) {
			this.vertWord += character;
		}
		return (Scrabble.dictionary.isAWord(this.vertWord)) ? 0 : -1;

	}

	/**
	 * Returns the horizontal Score
	 * 
	 * @return
	 */
	private int getHorizontalScore() {
		int before = this.word.indexOf(this.space.getOccupant());
		int start = this.space.getyPos() - before;
		int end = start + this.word.length() - 1;
		int after = this.word.length() - 1 - before;
		if (this.space.getyPos() + after > 14)
			return -1;

		if (before == -1)
			return -1;

		if (start < 0)
			return -1;

		if (!fillCharBoardHoriz(start)) // Fill in the board and get the score
			// of that play
			return -1;
		if (!checkAdjacent()) // Check the validity of adjacent words and get
			// their score
			return -1;
		return (getHorizWordsAround(end, this.space.getxPos()) == -1) ? -1
				: this.score;

	}

	private int getVerticalScore() {
		int before = this.word.indexOf(this.space.getOccupant());
		int start = this.space.getxPos() - before;
		int end = start + this.word.length() - 1;
		int after = this.word.length() - 1 - before;
		if (this.space.getxPos() + after > 14) {
			return -1;
		}
		if (before == -1)
			return -1;

		if (start < 0)
			return -1;

		if (!fillCharBoardVert(start)) // Fill in the board and get the score
			// of that play
			return -1;
		if (!checkAdjacent()) // Check the validity of adjacent words and get
			// their score
			return -1;

		return (getVertWordsAround(end, this.space.getyPos()) == -1) ? -1
				: this.score;

	}

	/**
	 * Finds words adjacent to the word just played
	 * 
	 * @param start
	 * @return
	 */

	private Boolean checkAdjacent() {
		int score = 0;
		for (BoardSpace space : this.newSpaces) {
			int x = space.getxPos();
			int y = space.getyPos();
			String word = "";
			int multiplier = 1;
			if (space.findMultiplier() == 'D') {
				multiplier *= 2;
			}
			if (space.findMultiplier() == 'T') {
				multiplier *= 3;
			}
			this.multiplierBoard[space.getyPos()][space.getxPos()] = ' ';
			if (!this.isVertical) {
				while (y > 0 && this.charBoard[y - 1][x].isOccupied())
					y--;
				while (y < 14 && this.charBoard[y + 1][x].isOccupied()) {
					word += this.charBoard[y + 1][x].getOccupant();
					score += Scrabble.DEFAULT_POINT_VALUES[this.charBoard[y + 1][x]
							.getOccupant() - 65];
					y++;
				}
				if (word.length() > 1 && Scrabble.dictionary.isAWord(word)) {
					this.adjacentWords.add(word);
				} else if (word != "" && !Scrabble.dictionary.isAWord(word))
					return false;
			} else {
				while (x > 0 && this.charBoard[x - 1][y].isOccupied())
					x--;
				while (x < 14 && this.charBoard[x + 1][y].isOccupied()) {
					word += this.charBoard[x + 1][y].getOccupant();
					score += Scrabble.DEFAULT_POINT_VALUES[this.charBoard[x + 1][y]
							.getOccupant() - 65];
					x++;
				}
				if (word.length() > 1 && Scrabble.dictionary.isAWord(word))
					this.adjacentWords.add(word);
				else if (word != "" && !Scrabble.dictionary.isAWord(word))
					return false;
			}
			this.score += (score * multiplier);
		}
		return true;
	}

	/**
	 * Plays what the best play is.
	 * 
	 */
	protected void playIt() {
		System.out.println("The word hopefully being played is" + this.word
				+ " and it is being played " + this.isVertical
				+ " for a score of " + this.score);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Scrabble.boardPieces[i][j] = new BoardSpace(
						this.charBoard[i][j]);
				BoardSpace.boardLayout[i][j] = this.multiplierBoard[i][j];
			}
		}
		Scrabble.scoreThisTurn = this.score;
		Scrabble.totalScore += this.score;
		Scrabble.tilesInHand = new ArrayList<Character>(this.tilesInHand);
		System.out.println("Found adjacent words " + this.adjacentWords);
		System.out.println("the new letters played are " + this.newSpaces);
		Scrabble.newLettersPlayed = this.newSpaces;
	}

	/**
	 * Prints a board representing the play.
	 * 
	 */
	public void printPlay() {
		// System.out.println("=========================");
		// System.out.println("x: " + this.xPosition + "y: " + this.yPosition
		// + " with word " + this.word + " score of " + this.score);
		// for (int i = 0; i < 15; i++) {
		// for (int j = 0; j < 15; j++) {
		// if (!(this.charBoard[i][j].getOccupant() == (char) 0)) {
		// // System.out.print(this.charBoard[i][j].getOccupant());
		// System.out.print(this.charBoard[i][j].getOccupant());
		// } else {
		// System.out.print(" ");
		// }
		// // System.out.print(this.multipierBoard[i][j]);
		// }
		// System.out.println("");
		// }
		// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

	}

	@Override
	public String toString() {
		return this.word + " Score of: " + this.score;
	}
}
