package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import data.DomXMLReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.game.GameGraphical;

public class GameController implements Initializable {

	private static String mode;
	private static String path;
	private GameGraphical game;

	@FXML
	AnchorPane anchorPane;

	@FXML
	Text redPoints;

	@FXML
	Text greenPoints;

	@FXML
	Text textWon;

	public static void setMode(String p_mode) {
		mode = p_mode;
	}

	public static void setPath(String p_path) {
		path = p_path;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textWon.setText("");

		game = new GameGraphical(1, 1,mode, anchorPane, redPoints, greenPoints, textWon);
		DomXMLReader<GameGraphical> domXMLReader = new DomXMLReader<>(game);
		domXMLReader.setGameFieldFromXML(path);

	}

	@FXML
	public void onClickBack(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/GameMenu.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
