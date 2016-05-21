package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import data.DomXMLReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.game.GameViewGraphical;

public class NewGameController implements Initializable {

	ToggleGroup toggleGroup;

	GameViewGraphical gameView;
	DomXMLReader<GameViewGraphical> domXMLReader;

	@FXML
	RadioButton rbHumanVHuman;

	@FXML
	RadioButton rbHumanVComputer;

	@FXML
	ListView<String> listView;

	@FXML
	AnchorPane anchorPane;

	HashMap<String, String> maps = new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		toggleGroup = new ToggleGroup();
		rbHumanVHuman.setToggleGroup(toggleGroup);
		rbHumanVHuman.setSelected(true);
		rbHumanVComputer.setToggleGroup(toggleGroup);

		maps.put("map1.xml", "maps");
		maps.put("map2.xml", "maps");
		maps.put("map3.xml", "maps");

		Path path = Paths.get(System.getProperty("user.home"), "Hexxagon Game", "custom_maps");
		File file = path.toFile();
		if (file.exists()) {
			String[] customMaps = file.list();
			for (int i = 0; i < customMaps.length; i++) {
				maps.put(customMaps[i], "custom_maps");
			}
		}

		ArrayList<String> namesForList = new ArrayList<>();
		maps.entrySet().stream().sorted((x, y) -> x.getKey().compareTo(y.getKey()))
				.forEach(x -> namesForList.add(x.getValue() + ": " + x.getKey().replace(".xml", "")));

		ObservableList<String> items = FXCollections.observableArrayList(namesForList);
		listView.setItems(items);
		listView.getSelectionModel().select(0);

		gameView = new GameViewGraphical(1, 1, "map", anchorPane);
		domXMLReader = new DomXMLReader<GameViewGraphical>(gameView);
		String[] splitted = listView.getSelectionModel().getSelectedItem().split(":");
		domXMLReader.setGameFieldFromXML(splitted[0], splitted[1].substring(1)+".xml");


		listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				String[] splitted = listView.getSelectionModel().getSelectedItem().split(":");
				domXMLReader.setGameFieldFromXML(splitted[0], splitted[1].substring(1)+".xml");
				gameView.setTransformations(anchorPane.getWidth(), anchorPane.getHeight(), gameView.getSizeX(),
						gameView.getSizeY());
				gameView.updateAnchorPane();
			}
		});

	}

	@FXML
	public void onClickStart(ActionEvent event) {
		if (rbHumanVComputer.isSelected()) {
			GameController.setMode("hvc");
		} else if (rbHumanVHuman.isSelected()) {
			GameController.setMode("hvh");
		}

		String[] splitted = listView.getSelectionModel().getSelectedItem().split(":");
		GameController.setFolder(splitted[0]);
		GameController.setFileName(splitted[1].substring(1)+".xml");
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/Game.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
