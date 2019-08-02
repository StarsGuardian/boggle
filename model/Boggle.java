package model;

import model.DiceTray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.*;

/**
 * 
 * @author Jian Fang
 *
 */
public class Boggle {

	// TODO: Complete a Boggle game that will be used with two views
	// 1. A console based game with standard IO (Boggle Two)
	// 2. An event-driven program with a graphical user interface (Boggle Three)
	private String Guessed;
	private List<String> inputList;
	private List<String> dictionary = new ArrayList();
	private List<String> check = new ArrayList();
	private List<String> correct = new ArrayList();
	private List<String> incorrect = new ArrayList();
	private String fileName;
	private Scanner infile;

	public Boggle() {

	}

	public void GamePreperation(char[][] Table) {
		System.out.println("Play one game of Boggle: ");
		System.out.println(PreGameOutput(Table));
		System.out.println("Enter words or ZZ to quit: " + "\n");
		String[] input = RecordUserinput(Guessed);
		inputList = Arrays.asList(input);
		inputList = new ArrayList<>(new HashSet<>(inputList));
		System.out.print("\n");
		fileName = "BoggleWords.txt";
		try {
			infile = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<List<String>> check_incorrect = isWord(dictionary, inputList, check, incorrect, infile);
		incorrect = check_incorrect.get(0);
		check = check_incorrect.get(1);
		List<List<String>> correct_incorrect = IfInDiceTray(check, correct, incorrect);
		correct = correct_incorrect.get(0);
		incorrect = correct_incorrect.get(1);
		int zz = incorrect.indexOf("zz");
		incorrect.remove(zz);
		String Update_correct = new_correct(correct);
		String Update_incorrect = new_incorrect(incorrect);
		List<String> potential = potential_words(dictionary, correct);
		int amount = potential_words(dictionary, correct).size();
		int score = count_score(correct);
		System.out.println("Score: " + score + "\n");
		System.out.println("Words you found: " + "\n" + Update_correct + "\n");
		System.out.println("Incorrect words: " + "\n" + Update_incorrect + "\n");
		String Update_potential = new_potential(potential);
		System.out.println("You could have found these " + amount + " more words: " + "\n" + Update_potential);
	}

	private String PreGameOutput(char[][] Table) {
		String content = new String();
		for (int i = 0; i <= 3; i++) {
			content += "\n";
			for (int j = 0; j <= 3; j++) {
				content += " " + Table[i][j];
			}
		}
		content += "\n";
		return content;
	}

	private String[] RecordUserinput(String Guessed) {
		Scanner keyboard = new Scanner(System.in);
		boolean loop = true;
		while (loop) {
			String Guess = keyboard.nextLine().toLowerCase();
			Guessed += Guess + " ";
			if (Guess.substring(Guess.length() - 2, Guess.length()).compareTo("zz") == 0)
				break;
		}
		Guessed = Guessed.replaceAll("\\s+", " ");
		Guessed = Guessed.substring(4, Guessed.length());
		String[] input = Guessed.split(" ");
		return input;
	}

	private List<List<String>> isWord(List<String> dictionary, List<String> inputList, List<String> check,
			List<String> incorrect, Scanner infile) {
		while (infile.hasNextLine()) {
			String word = infile.nextLine();
			word = word.replaceAll("\\r|\\n", "");
			dictionary.add(word);
		}
		for (int i = 0; i < dictionary.size(); i++) {
			for (int j = 0; j < inputList.size(); j++) {
				if (inputList.get(j).compareTo(dictionary.get(i)) == 0) {
					check.add(inputList.get(j));
					inputList.remove(inputList.get(j));
					break;
				}
			}
		}
		incorrect = inputList;
		List<List<String>> incorrect_check = new ArrayList();
		incorrect_check.add(incorrect);
		incorrect_check.add(check);
		return incorrect_check;
	}

	private List<List<String>> IfInDiceTray(List<String> check, List<String> correct, List<String> incorrect) {
		List<List<String>> cor_incor = new ArrayList();
		for (int i = 0; i < check.size(); i++) {
			if (DiceTray.found(check.get(i)))
				correct.add(check.get(i));
			else
				incorrect.add(check.get(i));
		}
		cor_incor.add(correct);
		cor_incor.add(incorrect);
		return cor_incor;
	}

	private int count_score(List<String> correct) {
		int score = 0;
		for (int i = 0; i < correct.size(); i++) {
			if (correct.get(i).length() == 3 || correct.get(i).length() == 4)
				score += 1;
			else if (correct.get(i).length() == 5)
				score += 2;
			else if (correct.get(i).length() == 6)
				score += 3;
			else if (correct.get(i).length() == 7)
				score += 4;
			else if (correct.get(i).length() > 7)
				score += 11;
		}
		return score;
	}

	private List<String> potential_words(List<String> dictionary, List<String> correct) {
		List<String> potential_words = new ArrayList();
		for (int i = 0; i < dictionary.size(); i++) {
			if (DiceTray.found(dictionary.get(i)))
				if (correct.indexOf(dictionary.get(i)) == -1)
					potential_words.add(dictionary.get(i));
		}
		return potential_words;
	}

	private String new_correct(List<String> correct) {
		String new_correct = "";
		for (int i = 0; i < correct.size(); i++) {
			if (i != 0 && i % 20 == 0)
				new_correct += correct.get(i) + "\n";
			else
				new_correct += correct.get(i) + " ";
		}
		return new_correct;
	}

	private String new_incorrect(List<String> incorrect) {
		String new_incorrect = "";
		for (int i = 0; i < incorrect.size(); i++) {
			if (i != 0 && i % 20 == 0)
				new_incorrect += incorrect.get(i) + "\n";
			else
				new_incorrect += incorrect.get(i) + " ";
		}
		return new_incorrect;
	}

	private String new_potential(List<String> potential) {
		String new_potential = "";
		for (int i = 0; i < potential.size(); i++) {
			if (i != 0 && i % 20 == 0)
				new_potential += potential.get(i) + "\n";
			else
				new_potential += potential.get(i) + " ";
		}
		return new_potential;
	}
}