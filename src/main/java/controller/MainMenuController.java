package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenuController implements Initializable {

	@FXML
	Button btnGame;

	@FXML
	Button btnBuilder;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@FXML
	private void clickBuilder(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/Builder.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void clickGame(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/NewGame.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
