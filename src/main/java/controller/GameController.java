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
	private static String folder;
	private static String fileName;
	private GameGraphical game;

	@FXML
	AnchorPane anchorPane;

	@FXML
	Text redPoints;

	@FXML
	Text greenPoints;

	@FXML
	Text txtInfo;

	public static void setMode(String p_mode) {
		mode = p_mode;
	}

	public static void setFolder(String p_folder) {
		folder = p_folder;
	}

	public static void setFileName(String p_fileName) {
		fileName = p_fileName;
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtInfo.setText("");

		game = new GameGraphical(1, 1,mode, anchorPane, redPoints, greenPoints, txtInfo);
		DomXMLReader<GameGraphical> domXMLReader = new DomXMLReader<>(game);
		domXMLReader.setGameFieldFromXML(folder,fileName);

	}

	@FXML
	public void onClickBack(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
