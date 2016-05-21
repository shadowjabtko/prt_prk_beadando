package model.game;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import model.field.FieldGraphical;

/**
 * Representing the Game graphically.
 * 
 * @author ShadowJabtko
 * @see model.game.Game
 */

public abstract class AbstractGameGraphical extends Game<FieldGraphical> {

	/**
	 * The AnchorPane where the game will appear.
	 */
	protected AnchorPane anchorPane;
	/**
	 * The mouse event which describe the polygon behaviors.
	 */
	protected EventHandler<MouseEvent> mouseEventPolygon;
	/**
	 * The mouse event which describe the circle behaviors.
	 */
	protected EventHandler<MouseEvent> mouseEventCircle;

	public AbstractGameGraphical(Integer sizeX, Integer sizeY, String mode, AnchorPane anchorPane) {
		super(sizeX, sizeY, mode);
		this.anchorPane = anchorPane;
		mouseEventPolygon = initMouseEventPolygon();
		mouseEventCircle = initMouseEventCircle();
		initAnchorPane();
	}

	@Override
	public FieldGraphical newFieldInstance(int i, int j) {
		return new FieldGraphical(i, j);
	}

	/**
	 * The initialize method for polygon behavior.
	 * 
	 * @return The mouse event which describes the behavior.
	 */
	public abstract EventHandler<MouseEvent> initMouseEventPolygon();

	/**
	 * The initialize method for circle behavior.
	 * 
	 * @return The mouse event which describes the behavior.
	 */
	public abstract EventHandler<MouseEvent> initMouseEventCircle();

	/**
	 * This method describe which graphical element should appear on the
	 * AnchorPane
	 */
	public abstract void updateAnchorPane();

	/**
	 * Initializing the AnchorPane size change behaviors.
	 */
	public void initAnchorPane() {
		anchorPane.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (anchorPane.getHeight() != 0.0) {
					setTransformations(newValue.doubleValue(), anchorPane.getHeight(), sizeX, sizeY);
					if (anchorPane.getChildren().isEmpty()) {
						updateAnchorPane();
					}
					setMouseEvents();
				}

			}
		});
		anchorPane.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (anchorPane.getWidth() != 0.0) {
					setTransformations(anchorPane.getWidth(), newValue.doubleValue(), sizeX, sizeY);
					if (anchorPane.getChildren().isEmpty()) {
						updateAnchorPane();
					}
					setMouseEvents();
				}
			}
		});
	}

	/**
	 * Returns all polygons.
	 * 
	 * @return A list with all polygon.
	 */
	public List<Polygon> getPolygons() {
		return getFieldsAsList().stream().map(x -> x.getPolygon()).collect(Collectors.toList());
	}

	/**
	 * Returns all visible polygons.
	 * 
	 * @return A list with visible polygons.
	 */
	public List<Polygon> getVisiblePolygons() {
		return getVisibleFields().stream().map(x -> x.getPolygon()).collect(Collectors.toList());
	}

	/**
	 * Returns all visible circle.
	 * 
	 * @return A list with visible circles.
	 */
	public List<Circle> getVisibleCircles() {
		return getPlayersField().stream().map(x -> x.getCircle()).collect(Collectors.toList());
	}

	/**
	 * Sets the {@code GraphicalFields} transformation.
	 * 
	 * @param paneWidth
	 *            The AnchorPane width.
	 * @param paneHeight
	 *            The AnchorPane height.
	 * @param sizeX
	 *            The sizeX of the Game area.
	 * @param sizeY
	 *            The sizeY of the Game area.
	 */
	public void setTransformations(double paneWidth, double paneHeight, double sizeX, double sizeY) {
		fields.stream().flatMap(x -> x.stream())
				.forEach(x -> x.setTransformations(paneWidth, paneHeight, sizeX, sizeY));
	}

	/**
	 * Sets the mouse events for the polygon and circle.
	 */
	public void setMouseEvents() {
		fields.stream().flatMap(x -> x.stream()).forEach(x -> x.setMouseEvents(mouseEventPolygon, mouseEventCircle));
	}

	/**
	 * Returns the {@code Field} which contains the polygon given by parameter.
	 * 
	 * @param polygon
	 *            The polygon we want to find.
	 * @return The {@code Field} which contains the polygon given by parameter.
	 */
	public FieldGraphical getFieldByPolygon(Polygon polygon) {
		return fields.stream().flatMap(x -> x.stream()).filter(x -> x.getPolygon().equals(polygon))
				.collect(Collectors.toList()).get(0);
	}

	/**
	 * Returns the {@code Field} which contains the circle given by parameter.
	 * 
	 * @param circle
	 *            The polygon we want to find.
	 * @return The {@code Field} which contains the cirlce given by parameter.
	 */
	public FieldGraphical getFieldByCircle(Circle circle) {
		return fields.stream().flatMap(x -> x.stream()).filter(x -> x.getCircle().equals(circle))
				.collect(Collectors.toList()).get(0);
	}
}
