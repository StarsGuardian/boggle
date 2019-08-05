package model;

/**
 * Author: Jian Fang
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * 
 * a console based program that allows one user to play one game of Boggle with
 * a fixed 2D array of char
 * 
 */
public class Boggle {

	private char[][] Table;
	private ArrayList<String> inputWord = new ArrayList<String>();
	private ArrayList<String> dictionary = new ArrayList<String>();
	public ArrayList<String> correct = new ArrayList<String>();
	public ArrayList<String> incorrect = new ArrayList<String>();
	public ArrayList<String> potential = new ArrayList<String>();
	private DiceTray tray;

	public Boggle(char[][] table) {
		this.Table = table;
		tray = new DiceTray(Table);
	}

	/**
	 * execute the boggleConsole game
	 */
	public void GameConsole() {
		System.out.println("Play one game of Boggle: ");
		System.out.println(this.PreOutPut());
		System.out.println("Enter words or ZZ to quit: \n");
		this.ReadFileInput();
		this.ReadUserInput();
		this.checkWord();
		this.potentialWords();
		System.out.println("Score: " + this.Score() + "\n");
		System.out.println("Word you found: \n" + this.autoWrap(correct) + "\n");
		System.out.println("Incorrect words: \n" + this.autoWrap(incorrect) + "\n");
		System.out.println(
				"You could have found these " + this.potential.size() + " more words: \n" + this.autoWrap(potential));

	}

	/**
	 * Convert dice tray into a string
	 * 
	 * @return string, the dice tray
	 */
	private String PreOutPut() {
		String output = "";
		for (int i = 0; i < 4; i++) {
			output += "\n";
			for (int j = 0; j < 4; j++) {
				output += " " + Table[i][j];
			}
		}
		output += "\n";
		return output;
	}

	/**
	 * Read the user input and store all word into a array list.
	 */
	private void ReadUserInput() {
		Scanner keyboard = new Scanner(System.in);
		while (keyboard.hasNext()) {
			String word = keyboard.next().toLowerCase();
			if (word.equals("zz")) {
				break;
			}
			if (!this.inputWord.contains(word)) {
				this.inputWord.add(word);
			}
		}
		keyboard.close();
	}

	/**
	 * Read the string input and store all word into a array list.
	 */
	public void ReadStringInput(String in) {
		Scanner keyboard = new Scanner(in);
		while (keyboard.hasNext()) {
			String word = keyboard.next().toLowerCase();
			if (!this.inputWord.contains(word)) {
				this.inputWord.add(word);
			}
		}
		keyboard.close();
	}

	/**
	 * Read the file input and store all words into a array list
	 */
	public void ReadFileInput() {
		String filename = "BoggleWords.txt";
		Scanner infile;
		try {
			infile = new Scanner(new File(filename));
			while (infile.hasNext()) {
				this.dictionary.add(infile.next());
			}
			infile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check the user input is the word is correct word in BoggleWords and dice tray
	 * or incorrect words that are not in the BoggleWords.
	 */
	public void checkWord() {
		for (String word : this.inputWord) {
			if (this.dictionary.contains(word)) {
				if (this.tray.found(word)) {
					this.correct.add(word);
				}
			} else {
				this.incorrect.add(word);
			}
		}
		Collections.sort(this.correct);
		Collections.sort(this.incorrect);
	}

	/**
	 * Check the words that are in the dice tray and BoggleWords but not in the user
	 * input
	 */
	public void potentialWords() {
		for (String word : this.dictionary) {
			if (!this.correct.contains(word) && this.tray.found(word)) {
				this.potential.add(word);
			}
		}
	}

	/**
	 * Calculate the score of the user
	 * 
	 * @return int , the score that user gets
	 */
	public int Score() {
		int score = 0;
		for (String word : this.correct) {
			switch (word.length()) {
			case 3:
				score++;
				break;
			case 4:
				score++;
				break;
			case 5:
				score += 2;
				break;
			case 6:
				score += 3;
				break;
			case 7:
				score += 4;
				break;
			default:
				score += 11;
			}
		}
		return score;
	}

	/**
	 * if the list is too long, the method will wrap
	 * 
	 * @param list, the list to wrap
	 * @return string, the string of a list
	 */
	public String autoWrap(ArrayList<String> list) {
		String output = "";
		for (int i = 0; i < list.size(); i++) {
			if (i != 0 && i % 20 == 0) {
				output += list.get(i) + "\n";
			} else {
				output += list.get(i) + " ";
			}
		}
		return output;
	}
}
