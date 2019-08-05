package view_controller;

/**
 * Author: Jian Fang
 */
import model.Boggle;
import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;

/**
 * 
 * This class generates a GUI version of game Boggle. Users input their guesses
 * in text area, by clicking end game button, user will see the game result. By
 * clicking new game, user can start a new game.
 */
public class BoggleApp extends Application {

	private TextArea dices = new TextArea();
	private TextArea input = new TextArea();
	private Button newGame = new Button("New Game");
	private Button endGame = new Button("End Game");
	private Label label = new Label("Enter your attempts below");
	private Boggle boggle;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		GridPane mainPane = new GridPane();
		this.setGUI(mainPane);
		this.newGameEvent();
		this.endGameEvent();
		stage.setScene(new Scene(mainPane, 240, 640));
		stage.show();
	}

	/**
	 * Set all GUI in the pane
	 * 
	 * @param pane, the pane to show
	 */
	private void setGUI(GridPane pane) {
		// dices tray textarea
		dices.setWrapText(true);
		dices.setEditable(false);
		dices.setPrefHeight(235);
		dices.setPrefWidth(220);
		dices.setFont(new Font("Courier", 20));
		this.CreateTray();
		boggle = new Boggle(tray);
		dices.setStyle("-fx-font-alignment: center");
		dices.setText(this.trayStr());

		// user input textarea
		input.setPrefHeight(270);
		input.setPrefWidth(220);
		input.setWrapText(true);
		pane.setVgap(10);
		pane.setHgap(10);

		// the button pane
		GridPane buttonPane = new GridPane();
		buttonPane.setVgap(10);
		buttonPane.setHgap(10);
		buttonPane.add(newGame, 2, 1);
		buttonPane.add(endGame, 3, 1);

		// label pane
		GridPane LabelPane = new GridPane();
		LabelPane.setVgap(10);
		LabelPane.setHgap(10);
		LabelPane.add(label, 3, 1);

		pane.add(dices, 1, 1);
		pane.add(buttonPane, 1, 3);
		pane.add(LabelPane, 1, 5);
		pane.add(input, 1, 6);
	}

	/**
	 * The newGame button event, when button sets on action, the new dices tray is
	 * created
	 */
	private void newGameEvent() {
		newGame.setOnAction(e -> {
			this.CreateTray();
			dices.setText(this.trayStr());
			input.setText("");
		});
	}

	/**
	 * The endGame event, when the button sets on action, the new alert window shows
	 * and print information of this game
	 */
	private void endGameEvent() {
		endGame.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Results");
			alert.setHeaderText("Here is your score");
			alert.setContentText(this.AlertText());
			alert.showAndWait();
		});
	}

	/**
	 * The hard code 16 dices
	 */
	private char[][] Alldices = { { 'L', 'R', 'Y', 'T', 'T', 'E' }, { 'V', 'T', 'H', 'R', 'W', 'E' },
			{ 'E', 'G', 'H', 'W', 'N', 'E' }, { 'S', 'E', 'O', 'T', 'I', 'S' }, { 'A', 'N', 'A', 'E', 'E', 'G' },
			{ 'I', 'D', 'S', 'Y', 'T', 'T' }, { 'O', 'A', 'T', 'T', 'O', 'W' }, { 'M', 'T', 'O', 'I', 'C', 'U' },
			{ 'A', 'F', 'P', 'K', 'F', 'S' }, { 'X', 'L', 'D', 'E', 'R', 'I' }, { 'H', 'C', 'P', 'O', 'A', 'S' },
			{ 'E', 'N', 'S', 'I', 'E', 'U' }, { 'Y', 'L', 'D', 'E', 'V', 'R' }, { 'Z', 'N', 'R', 'N', 'H', 'L' },
			{ 'N', 'M', 'I', 'H', 'U', 'Q' }, { 'O', 'B', 'B', 'A', 'O', 'J' } };
	private boolean[][] used = new boolean[4][4];
	private char[][] tray = new char[4][4];

	/**
	 * To randomly create dice tray
	 */
	private void CreateTray() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				used[i][j] = false;
			}
		}
		for (char[] dice : Alldices) {
			Random random = new Random();
			int x = random.nextInt(4);
			int y = random.nextInt(4);
			while (used[x][y]) {
				x = random.nextInt(4);
				y = random.nextInt(4);
			}
			tray[x][y] = dice[random.nextInt(6)];
			used[x][y] = true;
		}
	}

	/**
	 * convert the dice tray array to string
	 * 
	 * @return string, the string of dice tray
	 */
	private String trayStr() {
		String output = "";
		for (int i = 0; i < 4; i++) {
			output += "\n\n";
			for (int j = 0; j < 4; j++) {
				if (tray[i][j] == 'Q') {
					output += " " + tray[i][j] + 'u';
				} else {
					output += "  " + tray[i][j];

				}
			}
		}
		output += "\n";
		return output;
	}

	/**
	 * The method sets the alert content text about score, correct word, incorrect
	 * word, what word you can find more.
	 * 
	 * @return String, the whole information of alert
	 */
	private String AlertText() {
		boggle = new Boggle(tray);
		boggle.ReadStringInput(input.getText());
		boggle.ReadFileInput();
		boggle.checkWord();
		boggle.potentialWords();

		String output = "";
		output += "\nYou score: " + boggle.Score() + "\n\n";
		output += "Words you found: \n" + boggle.autoWrap(boggle.correct) + "\n\n";
		output += "Incorrect words: \n" + boggle.autoWrap(boggle.incorrect) + "\n\n";
		output += "You could have found " + boggle.potential.size() + " more words: \n"
				+ boggle.autoWrap(boggle.potential);
		return output;
	}

}
