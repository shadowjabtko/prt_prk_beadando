package model.field;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class FieldGraphical extends Field {

	private Polygon polygon;
	private Circle circle;

	private static final Double[] polygonPoints = new Double[] { -30.0, 0.0, -15.0, -25.0, 15.0, -25.0, 30.0, 0.0, 15.0,
			25.0, -15.0, 25.0 };

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

	public Polygon getPolygon() {
		return polygon;
	}

	public Circle getCircle() {
		return circle;
	}

	private void setScale(double scaleX, double scaleY) {
		polygon.setScaleX(scaleX);
		polygon.setScaleY(scaleY);
		circle.setScaleX(scaleX);
		circle.setScaleY(scaleY);
	}

	private void setTranslate(double translateX, double translateY) {
		polygon.setTranslateX(translateX);
		polygon.setTranslateY(translateY);
		circle.setTranslateX(translateX);
		circle.setTranslateY(translateY);
	}

	public void setTransformations(double paneWidth, double paneHeight, double sizeX, double sizeY) {
		double scaleX = paneWidth / (sizeX * 45 + 15);
		double scaleY = paneHeight / (sizeY * 50 + 25);
		/*
		if (scaleX > scaleY) {
			scaleX = scaleY;
		} else {
			scaleY = scaleX;
		}
		*/
		double plusX = 45 * scaleX;
		double plusY = 50 * scaleY;
		double plusY2 = 25 * scaleY;
		double translateX = axisX * plusX;
		double translateY = axisX % 2 == 0 ? axisY * plusY : axisY * plusY + plusY2;
		setScale(scaleX, scaleY);
		setTranslate(translateX - polygonPoints[0] * scaleX, translateY + polygonPoints[11] * scaleY);
	}

	public void setHighlight(Color color) {
		polygon.setStroke(color);
	}

	public void setMouseEvents(EventHandler<MouseEvent> mouseEventPolygon, EventHandler<MouseEvent> mouseEventCircle) {
		polygon.setOnMouseClicked(mouseEventPolygon);
		circle.setOnMouseClicked(mouseEventCircle);
	}
}
