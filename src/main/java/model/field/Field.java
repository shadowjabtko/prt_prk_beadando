package model.field;

public class Field {

	public static enum States {
		SELECT, DELETE, RED_PLAYER, GREEN_PLAYER
	}

	protected States state;
	protected Integer axisX;
	protected Integer axisY;

	public Field(Integer axisX, Integer axisY) {
		this.axisX = axisX;
		this.axisY = axisY;
		this.state = States.SELECT;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}

	public Integer getAxisX() {
		return axisX;
	}

	public Integer getAxisY() {
		return axisY;
	}

	@Override
	public String toString() {
		return "axisX: " + axisX + " axisY: " + axisY + " State: " + state;
	}

}
