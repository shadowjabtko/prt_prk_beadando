package controller;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class GameMenuController {

	@FXML
	Button btnNewGame;

	@FXML
	Button btnLoad;

	@FXML
	Button btnBack;

	@FXML
	private void clickBack(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void clickNewGame(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/NewGame.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
