package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static BorderPane root;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			root = new BorderPane();
			BorderPane borderPane = (BorderPane) FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
			root.setCenter(borderPane);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(600.0);
			primaryStage.setMinWidth(800.0);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static BorderPane getRoot() {
		return root;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
