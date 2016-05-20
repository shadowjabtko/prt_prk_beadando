package model.game;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import model.field.Field.States;
import model.field.FieldGraphical;

public class BuilderGraphical extends AbstractGameGraphical {

	private States builderState;
	private Slider horizontalSlider;
	private Slider verticalSlider;

	public BuilderGraphical(Integer sizeX, Integer sizeY,String mode, AnchorPane anchorPane, Slider horizontalSlider,Slider verticalSlider) {
		super(sizeX, sizeY,mode, anchorPane);
		this.horizontalSlider = horizontalSlider;
		this.verticalSlider = verticalSlider;
		initSliders();
	}

	public void setBuilderMode(States builderState) {
		this.builderState = builderState;
	}

	@Override
	public EventHandler<MouseEvent> initMouseEventPolygon() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Polygon polygon = (Polygon) event.getSource();
				FieldGraphical clickedField = getFieldByPolygon(polygon);
				if (builderState == States.DELETE || builderState == States.SELECT) {
					if (clickedField.getState() == States.RED_PLAYER
							|| clickedField.getState() == States.GREEN_PLAYER) {
						anchorPane.getChildren().remove(clickedField.getCircle());
					}
					clickedField.setState(builderState);
				} else if (builderState == States.RED_PLAYER || builderState == States.GREEN_PLAYER) {
					if (clickedField.getState() == States.DELETE || clickedField.getState() == States.SELECT) {
						anchorPane.getChildren().add(clickedField.getCircle());
					}
					clickedField.setState(builderState);
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
				if (builderState == States.DELETE || builderState == States.SELECT) {
					anchorPane.getChildren().remove(circle);
					clickedField.setState(builderState);
				} else {
					clickedField.setState(builderState);
				}
			}
		};
	}
	

	@Override
	public void updateAnchorPane() {
		anchorPane.getChildren().clear();
		anchorPane.getChildren().addAll(getPolygons());
		anchorPane.getChildren().addAll(getVisibleCircles());
	}
	
	public void initSliders(){
		verticalSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (oldValue.intValue() != newValue.intValue()) {
					setSizeY(newValue.intValue());
					setTransformations(anchorPane.getWidth(), anchorPane.getHeight(), sizeX, sizeY);
					updateAnchorPane();
					setMouseEvents();

				}
			}
		});

		horizontalSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (oldValue.intValue() != newValue.intValue()) {
					setSizeX(newValue.intValue());
					setTransformations(anchorPane.getWidth(), anchorPane.getHeight(), sizeX, sizeY);
					updateAnchorPane();
					setMouseEvents();
				}
			}
		});
	}


}
