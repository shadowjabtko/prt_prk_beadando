package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

	ArrayList<String> allPath = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		toggleGroup = new ToggleGroup();
		rbHumanVHuman.setToggleGroup(toggleGroup);
		rbHumanVHuman.setSelected(true);
		rbHumanVComputer.setToggleGroup(toggleGroup);
	
		
		
		allPath.add("maps " + "map1.xml");
		allPath.add("maps " + "map2.xml");
		allPath.add("maps " + "map3.xml");
		
		Path path = Paths.get(System.getProperty("user.home"), "Hexxagon Game","custom_maps");
		File file = path.toFile();
		ArrayList<String> names = new ArrayList<String>(Arrays.asList(file.list()));
		names.stream().forEach(x -> allPath.add("custom_maps " + x));
		
		names.clear();
		allPath.stream().forEach(x -> names.add(x.toString().replace(".xml", "").replaceFirst(" ", ": ")));

		ObservableList<String> items = FXCollections.observableArrayList(names);
		listView.setItems(items);
		listView.getSelectionModel().select(0);

		gameView = new GameViewGraphical(1, 1,"map", anchorPane);
		domXMLReader = new DomXMLReader<GameViewGraphical>(gameView);
		
		
		domXMLReader.setGameFieldFromXML(allPath.get(0));
		
		listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				domXMLReader.setGameFieldFromXML(allPath.get(newValue.intValue()));
				gameView.setTransformations(anchorPane.getWidth(), anchorPane.getHeight(), gameView.getSizeX(), gameView.getSizeY());
				gameView.updateAnchorPane();
			}
		});

	}

	@FXML
	public void onClickStart(ActionEvent event) {
		if (rbHumanVComputer.isSelected()) {
			GameController.setMode("hvc");
		} else if (rbHumanVHuman.isSelected()){
			GameController.setMode("hvh");
		}
		
		GameController.setPath(allPath.get(listView.getSelectionModel().getSelectedIndex()));
		// GameController.setPath(Paths.get("maps", "map1.xml"));
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
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/GameMenu.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
