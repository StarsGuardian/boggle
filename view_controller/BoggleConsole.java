package view_controller;

import model.Boggle;
import model.DiceTray;

public class BoggleConsole {
	public static void main(String[] args) {

		// Use this for testing
		char[][] a = { { 'A', 'B', 'S', 'E' }, { 'I', 'M', 'T', 'N' }, { 'N', 'D', 'E', 'D' },
				{ 'S', 'S', 'E', 'N' }, };

		DiceTray tray = new DiceTray(a);
		Boggle game = new Boggle();
		game.GamePreperation(a);
		// TODO: Complete a console game
	}
}