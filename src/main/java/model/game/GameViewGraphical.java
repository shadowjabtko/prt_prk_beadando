package model.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * Extended {@code AbstractGameGraphical} object which use for showing the game.
 * @author ShadowJabtko
 *
 */
public class GameViewGraphical extends AbstractGameGraphical{
	/**
	 * Constructs a newly allocated {@code GameViewGraphical} with the specified parameters.
	 * @param sizeX sizeX of {@code GameViewGraphical}.
	 * @param sizeY sizeY of {@code GameViewGraphical}.
	 * @param mode mode of {@code GameViewGraphical}.
	 * @param anchorPane anchorPane AnchorPane where {@code GameGraphical} will appear.
	 */
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
