package model.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import model.field.Field.States;
import model.field.FieldGraphical;

public class GameGraphical extends AbstractGameGraphical {

	Text redPoints;
	Text greenPoints;
	Text txtInfo;

	public GameGraphical(Integer sizeX, Integer sizeY, String mode, AnchorPane anchorPane, Text redPoints,
			Text greenPoints, Text txtInfo) {
		super(sizeX, sizeY, mode, anchorPane);
		this.redPoints = redPoints;
		this.greenPoints = greenPoints;
		this.txtInfo = txtInfo;
		if (getPlayer().getPlayer() == States.RED_PLAYER) {
			txtInfo.setText("Red Player Next!");
		} else {
			txtInfo.setText("Green Player Next!");
		}
	}

	@Override
	public EventHandler<MouseEvent> initMouseEventPolygon() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Polygon polygon = (Polygon) event.getSource();
				FieldGraphical clickedField = getFieldByPolygon(polygon);
				if (isMovable(clickedField)) {
					moveSelectedTo(clickedField);
					getFieldsAsList().stream().forEach(x -> x.setHighlight(Color.BLACK));
					if (mode.equals("hvc")) {
						getComuterFrom().setHighlight(Color.GREEN);
						getComuterWhere().setHighlight(Color.RED);
					}
					updateAnchorPane();
					redPoints.setText(new Integer(getRedPlayerCount()).toString());
					greenPoints.setText(new Integer(getGreenPlayerCount()).toString());
					if (isEnd()) {
						int whoWon = whoWon();
						if (whoWon == 1) {
							txtInfo.setText("Red Player Won!");
							txtInfo.setFill(Color.RED);
						} else if (whoWon == 2) {
							txtInfo.setText("Green Player Won!");
							txtInfo.setFill(Color.GREEN);
						} else if (whoWon == 0) {
							txtInfo.setText("Draw!");
						}
					} else {
						txtInfo.setFill(Color.BLACK);
						if (getPlayer().getPlayer() == States.RED_PLAYER) {
							txtInfo.setText("Red Player Next!");
						} else {
							txtInfo.setText("Green Player Next!");
						}
					}

				}
			}
		};
	}

	@Override
	public EventHandler<MouseEvent> initMouseEventCircle() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Circle circle = (Circle) event.getSource();
				FieldGraphical clickedField = getFieldByCircle(circle);
				if (isSelectable(clickedField)) {
					select(clickedField);
					getFieldsAsList().stream().forEach(x -> x.setHighlight(Color.BLACK));
					clickedField.setHighlight(Color.YELLOW);
					getCloseToSelectedField().stream().forEach(x -> x.setHighlight(Color.LIGHTGREEN));
					getFarToSelectedField().stream().forEach(x -> x.setHighlight(Color.YELLOW));
				}
			}
		};
	}

	@Override
	public void updateAnchorPane() {
		anchorPane.getChildren().clear();
		anchorPane.getChildren().addAll(getVisiblePolygons());
		anchorPane.getChildren().addAll(getVisibleCircles());
		redPoints.setText(new Integer(getRedPlayerCount()).toString());
		greenPoints.setText(new Integer(getGreenPlayerCount()).toString());

	}

}
