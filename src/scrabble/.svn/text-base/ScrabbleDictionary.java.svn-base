package scrabble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Constructs a dictionary and gives methods for making the size smaller to fit
 * specific needs.
 * 
 * @author Daniel Schepers, Ethan Veatch, Christopher A Good
 * 
 */
public class ScrabbleDictionary {
	private HashSet<String> wholeDictionary = new HashSet<String>();
	private HashSet<String> possibleWords = new HashSet<String>();

	/**
	 * Creates a Scrabble dictionary by reading the dictionary with the given
	 * filename.
	 * 
	 * @param filePathName
	 */

	public ScrabbleDictionary(String filePathName) {
		File inFile = new File(filePathName);
		Scanner in;
		try {
			in = new Scanner(inFile);
			String string;
			boolean canBeAdded;
			while (in.hasNext()) {
				canBeAdded = true;
				string = in.nextLine();
				// Don't need to add any words that contain something besides a
				// lowercase letter or words that are over 15 characters since
				// they won't fit on the board.
				if ((string.charAt(0) > 65 && string.charAt(0) < 90)
						|| string.length() > 15) {
					canBeAdded = false;
				}
				string = string.toUpperCase();

				for (int i = 0; i < string.length(); i++) {
					if (string.charAt(i) < 65 || string.charAt(i) > 90) {
						canBeAdded = false;
					}
				}
				if (canBeAdded) {
					this.wholeDictionary.add(string);
				}
			}
			System.out.println("The size of the dictionary is "
					+ this.wholeDictionary.size());
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
	}

	/**
	 * Checks if the given word is in the dictionary
	 * 
	 * @param word
	 * @return if the word is in the dictionary
	 */
	public boolean isAWord(String word) {
		word = word.toUpperCase();
		return this.wholeDictionary.contains(word);
	}

	/**
	 * saves a list of all the words that contain only characters given in the
	 * HashSet possible letters
	 * 
	 * @param possibleLetters
	 * @param numberOfBlankTiles
	 */
	public void containsOnly(HashSet<Character> possibleLetters,
			int numberOfBlankTiles) {
		int tileDiscrepancy;

		for (Character character : possibleLetters) {
			if (character == 'b') {
				numberOfBlankTiles++;
			}
		}
		this.possibleWords.clear();
		for (String word : this.wholeDictionary) {
			tileDiscrepancy = 0;
			for (Character c : word.toCharArray()) {
				if (!possibleLetters.contains(c)) {
					tileDiscrepancy++;
				}
			}
			if (tileDiscrepancy <= numberOfBlankTiles) {
				this.possibleWords.add(word);
			}
		}
	}

	/**
	 * Returns the possible words created from containsOnly
	 * 
	 * @return words
	 */
	public HashSet<String> getPossibleWords() {
		return this.possibleWords;
	}

	/**
	 * Creates an array of words that can possibly definitely be made depending
	 * on the board
	 * 
	 * @param chars
	 * @param dic
	 * @param requiredChar
	 * @param letterSpaces
	 * @param numberOfBlankTiles
	 * @return HashSet
	 */
	public HashSet<String> makeDicSmaller(ArrayList<Character> chars,
			HashSet<String> dic, char requiredChar,
			ArrayList<LetterSpacingForDictionary> letterSpaces,
			int numberOfBlankTiles) {
		HashSet<String> toReturn = new HashSet<String>();
		boolean isPossible;
		int tileDiscrepancy;
		ArrayList<Character> modifiedchars;
		for (String word : dic) {
			if (!word.contains(requiredChar + "")) {
				continue; // if it does not contain the required character there
				// is no need to continue this loop iteration
			}
			isPossible = true;
			tileDiscrepancy = 0;
			modifiedchars = new ArrayList<Character>(chars);

			for (Character c : word.toCharArray()) {
				if (!chars.contains(c)) {
					tileDiscrepancy++;
					// isPossible = false;

				} else {
					if (!modifiedchars.remove(c)) {
						isPossible = false;
					}
				}
			}
			boolean ifSpacingPossible = true;
			ifSpacingPossible = letterSpacesCheck(requiredChar, letterSpaces,
					numberOfBlankTiles, toReturn, isPossible, tileDiscrepancy,
					word, ifSpacingPossible);
		}
		return toReturn;
	}

	/**
	 * Private method that will verify that any spaces that would be in line
	 * with the word are not violated.
	 * 
	 * @param requiredChar
	 * @param letterSpaces
	 * @param numberOfBlankTiles
	 * @param toReturn
	 * @param isPossible
	 * @param tileDiscrepancy
	 * @param word
	 * @param ifSpacingPossible
	 * @return
	 */
	private boolean letterSpacesCheck(char requiredChar,
			ArrayList<LetterSpacingForDictionary> letterSpaces,
			int numberOfBlankTiles, HashSet<String> toReturn,
			boolean isPossible, int tileDiscrepancy, String word,
			boolean ifSpacingPossible) {
		int spaceIndex;
		// Just an efficiency check to save precious clock cycles. If
		// nothing in the letter spaces there is no need to do any further
		// checking and things can just be added
		if (!letterSpaces.isEmpty() && tileDiscrepancy <= numberOfBlankTiles) {
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == requiredChar) {
					// Loops over each letterSpace to see if a word will fit
					for (LetterSpacingForDictionary space : letterSpaces) {
						spaceIndex = space.getIndex();
						// If the LetterSpace is before the required character
						if (((spaceIndex + i) >= 0) && (spaceIndex < 0)) {
							ifSpacingPossible = word.charAt(i + spaceIndex) == space
									.getLetter();
							// If the letterspace is after the required
							// character
						} else if (((word.length() - i) - spaceIndex > 0)
								&& (spaceIndex > 0)) {
							ifSpacingPossible = word.charAt(i + spaceIndex) == space
									.getLetter();
						}
						if (!ifSpacingPossible) {
							break;
						}
					}
					if (isPossible && ifSpacingPossible) {
						toReturn.add(word);
					}
				}
			}
		} else if (isPossible && tileDiscrepancy <= numberOfBlankTiles) {
			toReturn.add(word);
		}
		return ifSpacingPossible;
	}
}
