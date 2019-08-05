package view_controller;

/**
 * Author: Jian Fang
 */
import model.Boggle;
import model.DiceTray;

/**
 * 
 * a console based program that allows one user to play one game of Boggle with
 * a fixed 2D array of char
 * 
 */
public class BoggleConsole {
	public static void main(String[] args) {
		char[][] fixed = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
				{ 'S', 'S', 'E', 'N' }, };
		DiceTray tray = new DiceTray(fixed);
		Boggle boggle = new Boggle(fixed);
		boggle.GameConsole();
	}

}
