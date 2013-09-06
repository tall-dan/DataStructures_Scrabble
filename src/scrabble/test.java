package scrabble;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class of random tests
 *
 * @author goodca
 *         Created May 19, 2011.
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String word = "wordslol";
//		int index = 0;
//		ArrayList<Character> lettersPlayed = new ArrayList<Character>();
//		lettersPlayed.add(0, 'o');
//		lettersPlayed.add(1, 'r');
//		lettersPlayed.add(2, 'd');
//		lettersPlayed.add(0, 'l');
//		for (Character c : lettersPlayed) {
//			index = word.indexOf(c);
//			word = word.substring(0, index) + word.substring(index + 1, word.length());
//		}
//		System.out.println(word);
		ScrabbleDictionary dic = new ScrabbleDictionary("scrabbleDictionaries/dictionary01.sd");
//		System.out.println(dic.isAWord("CQ"));
//		System.out.println(dic.isAWord("LULZ"));
//		System.out.println(dic.isAWord("BN"));
//		System.out.println(dic.isAWord("AC"));
//		System.out.println(dic.isAWord("ELEPHANT"));
//		System.out.println(dic.isAWord("FDSAFDSAFDS"));
		
//		HashSet<String> set = new HashSet<String>();
//		set.add('A');
//		set.add('B');
//		set.add('T');
		/**
		 * CHecks random words
		TreeSet<Character> totalBoardHandCharsTree = new TreeSet<Character>();
		HashSet<Character> totalBoardHandChars = new HashSet<Character>();
		int numberOfRandomBoardHandChars = 30;
		for (int i = 0; i < numberOfRandomBoardHandChars; i++) {
			totalBoardHandCharsTree.add((char) ((int)(Math.random() * 26) + 65));
		}
		for (Character character : totalBoardHandCharsTree) {
			totalBoardHandChars.add(character);
		}
		
		
		dic.containsOnly(totalBoardHandChars);
		
		ArrayList<Character> definiteBoardHandChars = new ArrayList<Character>();
		
		for (Character character : totalBoardHandChars) {
			definiteBoardHandChars.add(character);
		}
		
		int totalNumberOfDefiniteBoardHandChars = 9;
		for (int i = 0; i < totalBoardHandChars.size() - totalNumberOfDefiniteBoardHandChars; i++) {
			definiteBoardHandChars.remove((int)(definiteBoardHandChars.size()*Math.random()));
		}
		char definiteLetter = definiteBoardHandChars.get((int)(definiteBoardHandChars.size() * Math.random()));
		HashSet<String> smallerDic = dic.makeDicSmaller(definiteBoardHandChars, dic.getPossibleWords(), definiteLetter);
		System.out.println("Starting with " + totalBoardHandChars.size() + " random of what could be all of our board and hand characters " + totalBoardHandChars);
		System.out.println("and using " + totalNumberOfDefiniteBoardHandChars + " random of what could be our hand and definite board chars " + definiteBoardHandChars + " definitely containing " + definiteLetter);
		System.out.println(smallerDic.size() + " possible words result consisting of " + smallerDic);
		 */
		System.out.println(dic.isAWord("CCCTE"));
		HashSet<Character> possibleLetters = new HashSet<Character>();
		
		possibleLetters.add('R');
		possibleLetters.add('O');
		possibleLetters.add('L');
		possibleLetters.add('L');
		possibleLetters.add('E');
		possibleLetters.add('T');
		possibleLetters.add('I');
		possibleLetters.add('E');
		possibleLetters.add('S');
		dic.containsOnly(possibleLetters, 0);
		
		ArrayList<Character> chars = new ArrayList<Character>();
		chars.add('E');
		chars.add('I');
		chars.add('T');
		chars.add('E');
		chars.add('O');
		chars.add('S');
		chars.add('R');
		chars.add('O');
		chars.add('L');
		chars.add('L');
		ArrayList<LetterSpacingForDictionary> letterSpaces = new ArrayList<LetterSpacingForDictionary>();
		letterSpaces.add(new LetterSpacingForDictionary(1, 'O'));
		letterSpaces.add(new LetterSpacingForDictionary(2, 'L'));
		letterSpaces.add(new LetterSpacingForDictionary(3, 'L'));
//		letterSpaces.add(new LetterSpacingForDictionary(3, 'Z'));
		
		System.out.println(dic.makeDicSmaller(chars, dic.getPossibleWords(), 'R', letterSpaces, 0));
		
//		StringBuilder builder;
//		for (int i = 0; i < 50000; i++) {
//			builder = new StringBuilder();
//			for (int j = 0; j < 15*Math.random(); j++) {
//				
//				builder.append(Math.random() * 26 + 65);
//			}
//			set.add(builder.toString());
//			
//		}
//		System.out.println(set.size());
//		StopWatch watch = new StopWatch();
//		watch.start();
//		for (int i = 0; i < 1000; i++) {
//			for (String string : set) {
//				//Do absolutely nothing
//			}
//		}
//		watch.stop();
//		System.out.println(watch.getElapsedTime());
		
//		System.out.println("length: " + set.size() + set);
//		dic.containsOnly(set);
//		
//		System.out.println("length " + dic.getPossibleWords().size());

	}

}
