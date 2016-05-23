package model.field;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

/**
 * Representing the {@code Field} graphically.
 * 
 * @author ShadowJabtko
 * @see model.field.Field
 */
public class FieldGraphical extends Field {

	/**
	 * A polygon which representing the hexagon.
	 */
	private Polygon polygon;
	/**
	 * A circle which representing the players.
	 */
	private Circle circle;
	/**
	 * Hexagonal points.
	 */
	private static final Double[] polygonPoints = new Double[] { -30.0, 0.0, -15.0, -25.0, 15.0, -25.0, 30.0, 0.0, 15.0,
			25.0, -15.0, 25.0 };

	/**
	 * Represents the {@code Field} graphically.
	 * 
	 * @param axisX
	 *            Coordinate X
	 * @param axisY
	 *            Coordinate Y
	 * 
	 * 
	 */
	public FieldGraphical(Integer axisX, Integer axisY) {
		super(axisX, axisY);
		polygon = new Polygon();
		polygon.getPoints().addAll(polygonPoints);
		polygon.setStroke(Color.BLACK);
		polygon.setStrokeWidth(5.0);
		polygon.setStrokeType(StrokeType.INSIDE);

		circle = new Circle();
		circle.setRadius(17.0);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(1.0);
		circle.setStrokeType(StrokeType.INSIDE);
		setState(States.SELECT);
	}

	/**
	 * Set the state of the {@code FieldGraphical}.
	 * 
	 * @param state
	 *            New state of the {@code FieldGraphical}.
	 * 
	 */
	@Override
	public void setState(States state) {
		super.setState(state);
		switch (state) {
		case SELECT:
			polygon.setFill(Color.MAGENTA);
			circle.setFill(Color.GRAY);
			break;
		case DELETE:
			polygon.setFill(Color.GRAY);
			circle.setFill(Color.GRAY);
			break;
		case RED_PLAYER:
			polygon.setFill(Color.MAGENTA);
			circle.setFill(Color.RED);
			break;
		case GREEN_PLAYER:
			polygon.setFill(Color.MAGENTA);
			circle.setFill(Color.GREEN);
			break;
		}
	}

	/**
	 * Returns the polygon.
	 * 
	 * @return The polygon.
	 */
	public Polygon getPolygon() {
		return polygon;
	}

	/**
	 * Returns the circle.
	 * 
	 * @return The circle.
	 */
	public Circle getCircle() {
		return circle;
	}

	/**
	 * Set the polygon and circle scale according to parameters.
	 * 
	 * @param scaleX
	 *            The scale on x axis.
	 * @param scaleY
	 *            The scale on y axis.
	 */
	private void setScale(double scaleX, double scaleY) {
		polygon.setScaleX(scaleX);
		polygon.setScaleY(scaleY);
		circle.setScaleX(scaleX);
		circle.setScaleY(scaleY);
	}

	/**
	 * Set the polygon and circle translate according to parameters.
	 * 
	 * @param translateX
	 *            The translate on x axis.
	 * @param translateY
	 *            The translate on y axis.
	 */
	private void setTranslate(double translateX, double translateY) {
		polygon.setTranslateX(translateX);
		polygon.setTranslateY(translateY);
		circle.setTranslateX(translateX);
		circle.setTranslateY(translateY);
	}

	/**
	 * Calculate and performs the transformations.
	 * 
	 * @param paneWidth
	 *            AnchorPane width where the game is appear.
	 * @param paneHeight
	 *            AnchorPane height where the game is appear.
	 * @param sizeX
	 *            The sizeX of the current game.
	 * @param sizeY
	 *            The sizeY of the current game.
	 */
	public void setTransformations(double paneWidth, double paneHeight, double sizeX, double sizeY) {
		double scaleX = paneWidth / (sizeX * 45 + 15);
		double scaleY = paneHeight / (sizeY * 50 + 25);
		
		double plusX2 = 0.0;
		double plusY3 = 0.0;
		if (scaleX > scaleY) {
			scaleX = scaleY;
			double newWidth = sizeX * 45 * scaleX + 15 * scaleX;
			plusX2 = (paneWidth - newWidth) / 2.0;
		} else {
			scaleY = scaleX;
			double newHeight = sizeY * 50 * scaleY + 25 * scaleY;
			plusY3 = (paneHeight - newHeight) / 2.0;
		}

		double plusX = 45 * scaleX;
		double plusY = 50 * scaleY;
		double plusY2 = 25 * scaleY;
		double translateX = axisX * plusX + plusX2;
		double translateY = axisX % 2 == 0 ? axisY * plusY + plusY3: axisY * plusY + plusY2 + plusY3;
		setScale(scaleX, scaleY);
		setTranslate(translateX - polygonPoints[0] * scaleX, translateY + polygonPoints[11] * scaleY);
	}

	/**
	 * Sets the polygon border color (highlight).
	 * 
	 * @param color
	 *            The color of the highlight.
	 */
	public void setHighlight(Color color) {
		polygon.setStroke(color);
	}

	/**
	 * Sets the behavior of the polygon and circle.
	 * 
	 * @param mouseEventPolygon
	 *            Behavior of polygon.
	 * @param mouseEventCircle
	 *            Behavior of circle.
	 */
	public void setMouseEvents(EventHandler<MouseEvent> mouseEventPolygon, EventHandler<MouseEvent> mouseEventCircle) {
		polygon.setOnMouseClicked(mouseEventPolygon);
		circle.setOnMouseClicked(mouseEventCircle);
	}
}
