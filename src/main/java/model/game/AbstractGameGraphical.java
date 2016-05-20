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

public abstract class AbstractGameGraphical extends Game<FieldGraphical>{



	protected AnchorPane anchorPane;
	protected EventHandler<MouseEvent> mouseEventPolygon;
	protected EventHandler<MouseEvent> mouseEventCircle;

	public AbstractGameGraphical(Integer sizeX, Integer sizeY, String mode,AnchorPane anchorPane) {
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

	public abstract EventHandler<MouseEvent> initMouseEventPolygon();
	public abstract EventHandler<MouseEvent> initMouseEventCircle();

	public abstract void updateAnchorPane();

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

	public List<Polygon> getPolygons() {
		return getFieldsAsList().stream().map(x -> x.getPolygon()).collect(Collectors.toList());
	}

	public List<Polygon> getVisiblePolygons() {
		return getVisibleFields().stream().map(x -> x.getPolygon()).collect(Collectors.toList());		
	}

	public List<Circle> getVisibleCircles() {
		return getPlayersField().stream().map(x -> x.getCircle()).collect(Collectors.toList());
	}

	public void setTransformations(double paneWidth, double paneHeight, double sizeX, double sizeY) {
		fields.stream().flatMap(x -> x.stream())
				.forEach(x -> x.setTransformations(paneWidth, paneHeight, sizeX, sizeY));
	}

	public void setMouseEvents() {
		fields.stream().flatMap(x -> x.stream()).forEach(x -> x.setMouseEvents(mouseEventPolygon, mouseEventCircle));
	}

	public FieldGraphical getFieldByPolygon(Polygon polygon) {
		return fields.stream().flatMap(x -> x.stream()).filter(x -> x.getPolygon().equals(polygon))
				.collect(Collectors.toList()).get(0);
	}

	public FieldGraphical getFieldByCircle(Circle circle) {
		return fields.stream().flatMap(x -> x.stream()).filter(x -> x.getCircle().equals(circle))
				.collect(Collectors.toList()).get(0);
	}
}
