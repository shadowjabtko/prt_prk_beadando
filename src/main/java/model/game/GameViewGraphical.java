package model.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameViewGraphical extends AbstractGameGraphical{
	
	public GameViewGraphical(Integer sizeX, Integer sizeY,String mode, AnchorPane anchorPane) {
		super(sizeX, sizeY,mode, anchorPane);
	}
	
	@Override
	public EventHandler<MouseEvent> initMouseEventPolygon() {
		return null;
	}

	@Override
	public EventHandler<MouseEvent> initMouseEventCircle() {
		return null;
	}

	@Override
	public void updateAnchorPane() {
		anchorPane.getChildren().clear();
		anchorPane.getChildren().addAll(getVisiblePolygons());
		anchorPane.getChildren().addAll(getVisibleCircles());		
	}



}
