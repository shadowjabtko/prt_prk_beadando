package model.field;
/**
 * The {@code Field} is represents the smallest area in the game.
 * 
 * @author ShadowJabtko
 *
 */
public class Field {
	
	/**
	 * All available state for the {@code Field} object.
	 * @author ShadowJabtko
	 *
	 */
	public static enum States {
		SELECT, DELETE, RED_PLAYER, GREEN_PLAYER
	}
	
	/**
	 * The state of the {@code Field}
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
	 * Constructs a newly allocated {@code Field} object that represents 
	 * the (axisX,axisY) field of the game. 
	 * @param axisX Coordinate X.
	 * @param axisY Coordinate Y.
	 */
	public Field(Integer axisX, Integer axisY) {
		this.axisX = axisX;
		this.axisY = axisY;
		this.state = States.SELECT;
	}
	
	/**
	 * Returns the state of this {@code Field}
	 * @return State of the {@code Field}
	 */
	public States getState() {
		return state;
	}
	
	/**
	 * Set the state of the {@code Field}
	 * @param state New state of the {@code Field}
	 */
	public void setState(States state) {
		this.state = state;
	}
	
	/**
	 * Returns the axisX of this {@code Field}
	 * @return The axisX of this {@code Field}
	 */
	public Integer getAxisX() {
		return axisX;
	}
	
	/**
	 * Returns the axisY of this {@code Field}
	 * @return The axisY of this {@code Field}
	 */
	public Integer getAxisY() {
		return axisY;
	}

	@Override
	public String toString() {
		return "(" + axisX + "," + axisY + ")" + ": " + state;
	}
	
	

}
