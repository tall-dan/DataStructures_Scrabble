package scrabble;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author schepedw. Created Jul 31, 2011.
 */
public class IsPalindrome {

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		StopWatch watch = new StopWatch();
		watch.start();
		File inFile = new File("scrabbleDictionaries/BigDictionary.txt");
		Scanner in;
		String reverseString;
		//createHugeDictionary();
		ArrayList<String> palindromes = new ArrayList<String>();
		try {
			in = new Scanner(inFile);
			String string;
			while (in.hasNext()) {
				string = in.nextLine().toLowerCase();
				if (string.length() > 2) {
					reverseString = "";
					for (int i = string.length() - 1; i >= 0; i--) {
						reverseString += string.charAt(i);
					}
					if (reverseString.equals(string)
							&& !palindromes.contains(reverseString)) {
						palindromes.add(string);
					}
				}
			}
		} catch (FileNotFoundException e) {

			e.getMessage();

		}
		System.out.println(palindromes.toString());
		watch.stop();
		System.out.println("this search took " + watch.getElapsedTime()
				/ 1000.00 + " seconds");
	}

	private static void createHugeDictionary() {// Useless
		HashSet<String> dictionary = new HashSet<String>();
		File inFile = new File("scrabbleDictionaries/dictionary01.sd");
		Scanner in;
		Scanner secondIn;
		File secondFile = new File(
				"C:/Users/schepedw/Documents/Courses/CSSE 220/JavaProjects/Doublets/english.cleaned.all.35.txt");
		try {
			in = new Scanner(inFile);
			secondIn = new Scanner(secondFile);
			while (in.hasNext()) {
				String word = in.nextLine().toLowerCase();
				if (word.length() > 1)
					dictionary.add(word);
			}
			System.out.println(dictionary.size());// Interesting behavior-there
			// are less words in this
			// than the .sd file- does
			// the sd file have
			// duplicates?
			while (secondIn.hasNext()) {
				String word = secondIn.nextLine().toLowerCase();
				
				if (word.length() > 1)
					dictionary.add(word);// doesn't change
				// the size of
				// the hashSet
			}
			System.out.println(dictionary.size());
		}

		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		Iterator<String> iterator = dictionary.iterator();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"scrabbleDictionaries/BigDictionary.txt"));
			while (iterator.hasNext()) {
				out.write(iterator.next() + "\n");
			}
			out.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}
}