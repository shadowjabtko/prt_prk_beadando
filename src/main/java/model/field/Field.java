package model.field;

/**
 * The {@code Field} is represents the smallest area in the game on
 * (axisX,axisY) coordinate.
 * 
 * @author ShadowJabtko
 *
 */
public class Field {
	/**
	 * Enum that contains all the available state for one {@code Field}.
	 * @author ShadowJabtko
	 *
	 */
	public static enum States {
		/**
		 * The {@code Field} is part of the game area.
		 */
		SELECT,
		/**
		 * The {@code Field} is not part of the game area.
		 */
		DELETE,
		/**
		 * The {@code Field} is part of the game area and the red player own it.
		 */
		RED_PLAYER,
		/**
		 * The {@code Field} is part of the game area and the green player own
		 * it.
		 */
		GREEN_PLAYER
	}

	/**
	 * The state of the {@code Field}.
	 */
	protected States state;
	/**
	 * The {@code Field} position on the X axis.
	 */
	protected Integer axisX;
	/**
	 * The {@code Field} position on the Y axis.
	 */
	protected Integer axisY;

	/**
	 * Constructs a newly allocated {@code Field} with (axisX,axisY) coordinate.
	 * The state is set to SELECT
	 * 
	 * @param axisX
	 *            Coordinate X.
	 * @param axisY
	 *            Coordinate Y.
	 */
	public Field(Integer axisX, Integer axisY) {
		this.axisX = axisX;
		this.axisY = axisY;
		this.state = States.SELECT;
	}

	/**
	 * Returns the state of this {@code Field}.
	 * 
	 * @return State of the {@code Field}.
	 */
	public States getState() {
		return state;
	}

	/**
	 * Set the state of the {@code Field}.
	 * 
	 * @param state
	 *            New state of the {@code Field}.
	 */
	public void setState(States state) {
		this.state = state;
	}

	/**
	 * Returns the axisX of this {@code Field}.
	 * 
	 * @return The axisX of this {@code Field}.
	 */
	public Integer getAxisX() {
		return axisX;
	}

	/**
	 * Returns the axisY of this {@code Field}.
	 * 
	 * @return The axisY of this {@code Field}.
	 */
	public Integer getAxisY() {
		return axisY;
	}

	@Override
	public String toString() {
		return "(" + axisX + "," + axisY + ")" + ": " + state;
	}

}
