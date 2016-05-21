package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import data.DomXMLWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.field.Field.States;
import model.game.BuilderGraphical;

public class BuilderController implements Initializable {

	private BuilderGraphical builderGame;
	private DomXMLWriter<BuilderGraphical> domXMLWriter;

	@FXML
	Button btnSelect;
	@FXML
	Button btnDelete;
	@FXML
	Button btnRed;
	@FXML
	Button btnGreen;
	@FXML
	AnchorPane anchorPane;
	@FXML
	Slider horizontalSlider;
	@FXML
	Slider verticalSlider;
	@FXML
	TextField tfFileName;
	@FXML
	Text txtWarn;

	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/pngs/select.png"));
		btnSelect.setGraphic(new ImageView(image));
		btnSelect.setEffect(new DropShadow());
		image = new Image(getClass().getResourceAsStream("/pngs/delete.png"));
		btnDelete.setGraphic(new ImageView(image));
		image = new Image(getClass().getResourceAsStream("/pngs/red.png"));
		btnRed.setGraphic(new ImageView(image));
		image = new Image(getClass().getResourceAsStream("/pngs/green.png"));
		btnGreen.setGraphic(new ImageView(image));

		builderGame = new BuilderGraphical(18, 12,"map", anchorPane, horizontalSlider, verticalSlider);
		domXMLWriter = new DomXMLWriter<BuilderGraphical>(builderGame);
		txtWarn.setFill(Color.RED);
		txtWarn.setText("");
	}

	@FXML
	private void onClickButtons(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		clearButtonsEffect();
		clickedButton.setEffect(new DropShadow());
		if (clickedButton.equals(btnSelect)) {
			builderGame.setBuilderMode(States.SELECT);
		} else if (clickedButton.equals(btnDelete)) {
			builderGame.setBuilderMode(States.DELETE);
		} else if (clickedButton.equals(btnRed)) {
			builderGame.setBuilderMode(States.RED_PLAYER);
		} else {
			builderGame.setBuilderMode(States.GREEN_PLAYER);
		}

	}

	@FXML
	private void onClickSave(ActionEvent event) {
			
		if (builderGame.isEnd()) {
			txtWarn.setText("WARN: game is not valid!");
			return;
		}
		
		if (!tfFileName.getText().equals("")) {
			domXMLWriter.setFileName(tfFileName.getText());
			if (domXMLWriter.isExist() && !domXMLWriter.getReWrite()) {
				txtWarn.setText("WARN: file exist\n Press again to\n overwrite!");
				domXMLWriter.setReWrite(true);
			} else {
				domXMLWriter.writeOut();
				txtWarn.setText("File saved.");
			}
		} else {
			txtWarn.setText("WARN: need filename!");
		}

	}

	@FXML
	private void onClickBack(ActionEvent event) {
		try {
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
			Main.getRoot().setCenter(borderPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void clearButtonsEffect() {
		btnDelete.setEffect(null);
		btnSelect.setEffect(null);
		btnRed.setEffect(null);
		btnGreen.setEffect(null);
	}

}
