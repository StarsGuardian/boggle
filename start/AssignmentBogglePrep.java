package start;

/**
 * Complete an event driven GUI that could be the start of
 * a GUI for Boggle Three
 * 
 * author Rick Mercer and Jian Fang
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AssignmentBogglePrep extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Canvas canvas;
	private Image boggleImage;
	private GraphicsContext g2;
	private TextArea input = new TextArea();
	private TextArea output = new TextArea();
	private Label prompt = new Label("Enter sent get ten notHere");
	private Button button = new Button("Search for found words");
	ArrayList<String> boggleWords = new ArrayList<String>();

	/**
	 * Layout the GUI and initialize everything (this is too much in start)
	 */
	@Override
	public void start(Stage stage) {
		makeListOfBoggleWords();
		input.setWrapText(true); // Cause a newLine instead of going off the TextArea
		// Add elements in column 1, not 0 with gaps set
		GridPane pane = new GridPane();
		try {
			boggleImage = new Image(new FileInputStream("boggle.jpeg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.setPrefWidth(350);
		output.setPrefWidth(350);
		pane.setPadding(new Insets(5, 0, 0, 10));
		pane.setVgap(5);
		pane.setHgap(5);
		pane.add(new ImageView(boggleImage), 0, 0);
		pane.add(prompt, 0, 1);
		pane.add(input, 0, 2);
		pane.add(button, 0, 3);
		pane.add(output, 0, 4);
		this.ButtonEvent();
		stage.setScene(new Scene(pane));
		stage.show();
	}

	private void ButtonEvent() {
		button.setOnAction(e -> {
			String outWord = "You found: ";
			ArrayList<String> possible = new ArrayList<String>();
			Scanner in = new Scanner(input.getText());
			while (in.hasNext()) {
				String word = in.next();
				if (boggleWords.contains(word)) {
					possible.add(word);
				}
			}
			in.close();
			for (Object word : possible.toArray()) {
				outWord += word.toString() + " ";
			}
			output.setText(outWord);
		});
	}

	private void makeListOfBoggleWords() {
		boggleWords.add("sent");
		boggleWords.add("ten");
		boggleWords.add("get");
	}
}