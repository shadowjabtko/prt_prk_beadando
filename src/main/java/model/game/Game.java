package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.field.Field;
import model.field.Field.States;

/**
 * The {@code Game} represents the whole game.
 * 
 * 
 * @author ShadowJabtko
 *
 * @param <T>
 *            Class which extends Field class.
 */
public class Game<T extends Field> {

	/**
	 * All {@code Field} object in the {@code Game}.
	 */
	protected ArrayList<ArrayList<T>> fields;
	/**
	 * The maximum number of {@code Field} in a row.
	 */
	protected Integer sizeX;
	/**
	 * The maximum number of {@code Field} in a column.
	 */
	protected Integer sizeY;
	/**
	 * The {@code Game} mode. Human vs. Human (hvh) or Human vs. Computer (hvc).
	 */
	protected String mode;

	/**
	 * Holding the current player.
	 */
	private Player player;
	/**
	 * The selected {@code Field} in the game area.
	 */
	private T selectedField;
	/**
	 * A list that contains all the available {@code Field} for the selected
	 * {@code Field} which distance is 1 from selected {@code Field}.
	 * 
	 */
	private List<T> closeToSelectedField;
	/**
	 * A list that contains all the available {@code Field} for the selected
	 * {@code Field} which distance is 2 from selected {@code Field}.
	 */
	private List<T> farToSelectedField;
	/**
	 * The selected {@code Field} of the computer movement.
	 */
	private T computerFrom;
	/**
	 * The destination {@code Field} of the computer movement.
	 */
	private T computerWhere;
	/**
	 * The {@code Logger} object of the {@code Game}.
	 */
	private Logger logger = LoggerFactory.getLogger(Game.class);

	/**
	 * Constructs a newly allocated {@code Game} with the given size and mode.
	 * 
	 * @param sizeX
	 *            The sizeX of the {@code Game}.
	 * @param sizeY
	 *            The sizeX of the {@code Game}.
	 * @param mode
	 *            The mode of the {@code Game}.
	 */
	public Game(Integer sizeX, Integer sizeY, String mode) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.mode = mode;
		player = new Player();
		fields = new ArrayList<ArrayList<T>>();
		for (int i = 0; i < sizeX; i++) {
			ArrayList<T> column = new ArrayList<T>();
			for (int j = 0; j < sizeY; j++) {
				T field = newFieldInstance(i, j);
				column.add(field);
			}
			fields.add(column);
		}
		logger.trace("New game created.");
	}

	/**
	 * Constructs a newly allocated {@code Game} from given {@code Game}.
	 * 
	 * @param game
	 *            The {@code Game} we want to copy.
	 */
	public Game(Game<T> game) {
		this.sizeX = game.getSizeX();
		this.sizeY = game.getSizeY();
		this.mode = game.getMode();
		player = new Player();
		fields = new ArrayList<ArrayList<T>>();
		for (int i = 0; i < sizeX; i++) {
			ArrayList<T> column = new ArrayList<T>();
			for (int j = 0; j < sizeY; j++) {
				T field = newFieldInstance(i, j);
				try {
					field.setState(game.getField(i, j).getState());
					column.add(field);
				} catch (NullPointerException e) {
					logger.error(e.getMessage());
					logger.warn("Make sure the parmeter is initalizeded.");
				}

			}
			fields.add(column);
		}
		player.setPlayer(game.getPlayer().getPlayer());
		logger.trace("New game from game.");
	}

	/**
	 * Returns the mode of the {@code Game}.
	 * 
	 * @return The mode of the {@code Game}.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Returns the {@code Field} which is on (x,y) coordinate of the game area .
	 * 
	 * @param x
	 *            Coordinate X.
	 * @param y
	 *            Coordinate Y.
	 * @return The {@code Field} on (x,y) coordinate.
	 */
	public T getField(int x, int y) {
		if (x >= sizeX || y >= sizeY) {
			logger.error("Coordinates can not be equals or bigger than game size");
			return null;
		} else {
			return fields.get(x).get(y);
		}
	}

	/**
	 * Returns the sizeX of the {@code Game}.
	 * 
	 * @return The sizeX of the {@code Game}.
	 */
	public Integer getSizeX() {
		return sizeX;
	}

	/**
	 * Returns the sizeY of the {@code Game}.
	 * 
	 * @return The sizeY of the {@code Game}.
	 */
	public Integer getSizeY() {
		return sizeY;
	}

	/**
	 * Sets the sizeX of the {@code Game}.
	 * 
	 * @param sizeX
	 *            The new sizeX of the {@code Game}.
	 */
	public void setSizeX(int sizeX) {
		if (sizeX > this.sizeX) {
			for (int i = 0; i < sizeX - this.sizeX; i++) {
				ArrayList<T> column = new ArrayList<T>();
				for (int j = 0; j < sizeY; j++) {
					T field = newFieldInstance(this.sizeX + i, j);
					column.add(field);
				}
				fields.add(column);
			}
		} else if (this.sizeX > sizeX) {
			for (int i = 0; i < this.sizeX - sizeX; i++) {
				fields.remove(this.sizeX - 1 - i);
			}
		}
		logger.trace("Game sizeX changed from " + this.sizeX + " to " + sizeX);

		this.sizeX = sizeX;

	}

	/**
	 * Sets the sizeY of the {@code Game}.
	 * 
	 * @param sizeY
	 *            The new sizeY of the {@code Game}.
	 */
	public void setSizeY(int sizeY) {
		if (sizeY > this.sizeY) {
			for (int i = 0; i < sizeX; i++) {

				for (int j = 0; j < sizeY - this.sizeY; j++) {
					T field = newFieldInstance(i, this.sizeY + j);
					fields.get(i).add(field);
				}

			}
		} else if (sizeY < this.sizeY) {
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < this.sizeY - sizeY; j++) {
					fields.get(i).remove(this.sizeY - 1 - j);
				}
			}
		}
		logger.trace("Game sizeX changed from " + this.sizeY + " to " + sizeY);
		this.sizeY = sizeY;
	}

	/**
	 * Creates a new instance of {@code Field}. Every object which extends
	 * {@code Game} has to override this method.
	 * 
	 * @param i
	 *            The axisX of the {@code Field}.
	 * @param j
	 *            The axisY of the {@code Field}
	 * @return The newly created {@code Field}
	 */
	public T newFieldInstance(int i, int j) {

		try {
			@SuppressWarnings("unchecked")
			T field = (T) new Field(i, j);
			return field;
		} catch (ClassCastException e) {
			logger.error(e.getMessage());
			logger.warn("Make sure you implemented this method.");
		}
		return null;
	}

	/**
	 * Returns all {@code Field} of the {@code Game} as a single list.
	 * 
	 * @return A list with all {@code Field}.
	 */

	public List<T> getFieldsAsList() {
		return fields.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
	}

	/**
	 * Returns all {@code Field} of the {@code Game} which is available for the
	 * players.
	 * 
	 * @return A list with all available {@code Field}.
	 */
	public List<T> getVisibleFields() {
		return getFieldsAsList().stream().filter(x -> !(x.getState() == States.DELETE)).collect(Collectors.toList());
	}

	/**
	 * Returns all {@code Field} of the {@code Game} where the players are.
	 * 
	 * @return A list with players {@code Field}.
	 */
	public List<T> getPlayersField() {
		return getFieldsAsList().stream()
				.filter(x -> x.getState() == States.RED_PLAYER || x.getState() == States.GREEN_PLAYER)
				.collect(Collectors.toList());
	}

	/**
	 * Returns all {@code Field} which distance is 1 from the parameter.
	 * 
	 * @param field
	 *            The {@code Field} where wants to calculate the distance.
	 * @return A list with all {@code Field} which distance is 1 from the
	 *         parameter.
	 */
	public List<T> getCloseFields(Field field) {
		Integer axisX = field.getAxisX();
		Integer axisY = field.getAxisY();
		if (axisX % 2 == 0) {
			return getFieldsAsList().stream()
					.filter(x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 1 || x.getAxisY() == axisY + 1))
							|| ((x.getAxisX() == axisX - 1 || x.getAxisX() == axisX + 1)
									&& (x.getAxisY() == axisY || x.getAxisY() == axisY - 1)))
					.collect(Collectors.toList());
		} else {
			return getFieldsAsList().stream()
					.filter(x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 1 || x.getAxisY() == axisY + 1))
							|| ((x.getAxisX() == axisX - 1 || x.getAxisX() == axisX + 1)
									&& (x.getAxisY() == axisY || x.getAxisY() == axisY + 1)))
					.collect(Collectors.toList());
		}

	}

	/**
	 * Returns all {@code Field} which distance is 2 from the parameter.
	 * 
	 * @param field
	 *            The {@code Field} where wants to calculate the distance.
	 * @return A list with all {@code Field} which distance is 2 from the
	 *         parameter.
	 */
	public List<T> getFarFields(Field field) {
		Integer axisX = field.getAxisX();
		Integer axisY = field.getAxisY();
		if (axisX % 2 == 0) {
			return getFieldsAsList()
					.stream().filter(
							x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 2 || x.getAxisY() == axisY + 2))
									|| ((x.getAxisX() == axisX + 1
											|| x.getAxisX() == axisX - 1)
											&& (x.getAxisY() == axisY - 2 || x.getAxisY() == axisY + 1))
									|| ((x.getAxisX() == axisX - 2 || x.getAxisX() == axisX + 2)
											&& (x.getAxisY() == axisY || x.getAxisY() == axisY + 1
													|| x.getAxisY() == axisY - 1)))
					.collect(Collectors.toList());
		} else {
			return getFieldsAsList()
					.stream().filter(
							x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 2 || x.getAxisY() == axisY + 2))
									|| ((x.getAxisX() == axisX + 1
											|| x.getAxisX() == axisX - 1)
											&& (x.getAxisY() == axisY - 1 || x.getAxisY() == axisY + 2))
									|| ((x.getAxisX() == axisX - 2 || x.getAxisX() == axisX + 2)
											&& (x.getAxisY() == axisY || x.getAxisY() == axisY + 1
													|| x.getAxisY() == axisY - 1)))
					.collect(Collectors.toList());
		}
	}

	/**
	 * Returns all {@code Field} which distance is 1 from the parameter and the
	 * state of {@code Field} is suit the filter.
	 * 
	 * @param field
	 *            The {@code Field} where wants to calculate the distance.
	 * 
	 * @param filter
	 *            The selection criteria.
	 * 
	 * @return A list with all {@code Field} which distance is 1 from the
	 *         parameter suits the filter.
	 * 
	 * @see model.game.Game#getCloseFields(Field)
	 */
	public List<T> getCloseFieldsFiltered(Field field, States filter) {
		return getCloseFields(field).stream().filter(x -> x.getState() == filter).collect(Collectors.toList());
	}

	/**
	 * Returns all {@code Field} which distance is 2 from the parameter and the
	 * state of {@code Field} is suit the filter.
	 * 
	 * @param field
	 *            The {@code Field} where wants to calculate the distance.
	 * @param filter
	 *            The selection criteria.
	 * @return A list with all {@code Field} which distance is 2 from the
	 *         parameter suits the filter.
	 * @see model.game.Game#getFarFields(Field)
	 */
	public List<T> getFarFieldsFiltered(Field field, States filter) {
		return getFarFields(field).stream().filter(x -> x.getState() == filter).collect(Collectors.toList());
	}

	/**
	 * Decides if the parameter {@code Field} is selectable or not.
	 * 
	 * @param field
	 *            The {@code Field} we want to examine.
	 * @return {@code true} if the {@code Field} is selectable; {@code false}
	 *         otherwise.
	 */
	public boolean isSelectable(T field) {
		if (field.getState() == player.getPlayer()) {
			logger.info("Selectable!");
			return true;
		}
		logger.info("Not selectable!");
		return false;
	}

	/**
	 * Select the given {@code Field} is it is possible and sets the
	 * {@code closeToSelectedField} variable and the
	 * {@code closeToSelectedField} variable.
	 * 
	 * @param field
	 *            The {@code Field} we want to select,
	 * 
	 * @see model.game.Game#isSelectable(Field)
	 */
	public void select(T field) {
		if (isSelectable(field)) {
			selectedField = field;
			closeToSelectedField = getCloseFieldsFiltered(selectedField, States.SELECT);
			farToSelectedField = getFarFieldsFiltered(selectedField, States.SELECT);
		}

	}

	/**
	 * Returns the private {@code closeToSelectedField} list.
	 * 
	 * @return {@code closeToSelectedField} list.
	 */
	public List<T> getCloseToSelectedField() {
		return closeToSelectedField;
	}

	/**
	 * Returns the private {@code farToSelectedField} list.
	 * 
	 * @return {@code farToSelectedField} list.
	 */
	public List<T> getFarToSelectedField() {
		return farToSelectedField;
	}

	/**
	 * Decides if the selected {@code Field} filed is movable to the desired
	 * position.
	 * 
	 * @param where
	 *            The {@code Field} where we want to move the selected
	 *            {@code Field}.
	 * @return {@code true} if the selected {@code Field} is movable to the
	 *         desired position; {@code false} otherwise;
	 */
	public boolean isMovable(T where) {
		if (selectedField != null && (closeToSelectedField.contains(where) || farToSelectedField.contains(where))) {
			logger.info("Movable!");
			return true;
		} else {
			logger.info("Not movable!");
			return false;
		}

	}

	/**
	 * Reset the selection.
	 */
	public void unSelect() {
		selectedField = null;
		closeToSelectedField = null;
		farToSelectedField = null;
	}

	/**
	 * Returns the captured {@code Fields} after movement and moves the selected
	 * {@code Field} to the desired {@code Field} if it is possible and sets the
	 * player to the opposite player. If the {@code Game} mode is
	 * "Human vs. Computer" then this method call the computerMove() method.
	 * 
	 * 
	 * @param where
	 *            Destination of movement.
	 * @return The captured {@code Fields} after movement.
	 * @see model.game.Game#computerMove()
	 */
	public int moveSelectedTo(T where) {
		int result = 0;
		if (isMovable(where)) {
			where.setState(player.getPlayer());
			if (farToSelectedField.contains(where)) {
				selectedField.setState(States.SELECT);
			}
			result = getCloseFieldsFiltered(where, player.getOppositePlayer()).size();
			getCloseFieldsFiltered(where, player.getOppositePlayer()).forEach(x -> x.setState(player.getPlayer()));
			player.setToOppositePlayer();
			selectedField = null;
			farToSelectedField = null;
			closeToSelectedField = null;

			if (isEnd()) {
				return 0;
			}
			if (mode.equals("hvc")) {
				if (player.getPlayer() == States.GREEN_PLAYER) {
					computerMove();
				}
			}
		}

		return result;
	}

	/**
	 * Returns the amount of {@code Field} of the current player.
	 * 
	 * @return The amount of {@code Field} of the current player.
	 * @see model.game.Game#getRedPlayerCount()
	 * @see model.game.Game#getGreenPlayerCount()
	 * 
	 */
	public int getCurrentPlayerCount() {
		if (player.getPlayer() == States.RED_PLAYER) {
			return getRedPlayerCount();
		} else {
			return getGreenPlayerCount();
		}
	}

	/**
	 * Returns the amount of {@code Field} of the red player.
	 * 
	 * @return The amount of {@code Field} of the red player.
	 */
	public int getRedPlayerCount() {
		return (int) getFieldsAsList().stream().filter(x -> x.getState() == States.RED_PLAYER).count();
	}

	/**
	 * Returns the amount of {@code Field} of the green player.
	 * 
	 * @return The amount of {@code Field} of the green player.
	 */
	public int getGreenPlayerCount() {
		return (int) getFieldsAsList().stream().filter(x -> x.getState() == States.GREEN_PLAYER).count();
	}

	/**
	 * Returns the amount of remained {@code Fields}.
	 * 
	 * @return The amount of remained {@code Fields}.
	 */
	public int getReaminedFieldsCount() {
		return (int) getFieldsAsList().stream().filter(x -> x.getState() == States.SELECT).count();
	}

	/**
	 * Decides if the current player has available {@code Field} or not.
	 * 
	 * @return {@code true} if the current player has available {@code Field};
	 *         {@code false} otherwise;
	 */
	public boolean isAvailableMove() {
		int count = getFieldsAsList().stream().filter(x -> x.getState() == player.getPlayer()).mapToInt(
				x -> getCloseFieldsFiltered(x, States.SELECT).size() + getFarFieldsFiltered(x, States.SELECT).size())
				.sum();
		if (count == 0) {
			logger.warn("No available move!");
			return false;
		}
		return true;
	}

	/**
	 * Decides if the game is over or not.
	 * 
	 * @return {@code true} If game over; {@code false} otherwise.
	 */
	public boolean isEnd() {
		if (getReaminedFieldsCount() == 0 || getRedPlayerCount() == 0 || getGreenPlayerCount() == 0
				|| !isAvailableMove()) {
			return true;
		}
		return false;
	}

	/**
	 * Decides who won the game.
	 * 
	 * @return {@code 1} if the red player won; {@code 2} if the green player
	 *         won; {@code 0} if draw; {@code -1} if the game is not over yet.
	 */
	public int whoWon() {
		if (isEnd()) {
			logger.info("Game over!");
			if (getRedPlayerCount() > getGreenPlayerCount()) {
				return 1;
			} else if (getRedPlayerCount() < getGreenPlayerCount()) {
				return 2;
			} else {
				return 0;
			}
		}
		logger.info("Game is not over yet.");
		return -1;
	}

	/**
	 * Calculates the maximum number of attackable {@code Fields}.
	 * 
	 * @return The maximum number of attackable {@code Fields}.
	 */
	public int getMaxAttackable() {
		List<T> oppositePlayerFields = getFieldsAsList().stream()
				.filter(x -> x.getState() == player.getOppositePlayer()).collect(Collectors.toList());
		List<T> availableOppositeFields = oppositePlayerFields.stream().flatMap(x -> {
			ArrayList<T> result = new ArrayList<T>();
			result.addAll(getCloseFieldsFiltered(x, States.SELECT));
			result.addAll(getFarFieldsFiltered(x, States.SELECT));
			return result.stream();
		}).sorted((x, y) -> {
			if (x.getAxisX() == y.getAxisX()) {
				return x.getAxisY().compareTo(y.getAxisY());
			} else {
				return x.getAxisX().compareTo(y.getAxisX());
			}
		}).distinct().collect(Collectors.toList());
		OptionalInt max = availableOppositeFields.stream()
				.mapToInt(x -> getCloseFieldsFiltered(x, player.getPlayer()).size()).max();
		if (max.isPresent()) {
			logger.info("Max attackable: " + new Integer(max.getAsInt()).toString());
			return max.getAsInt();
		} else {
			logger.info("Max attackable: 0");
			return 0;
		}

	}

	/**
	 * Calculates the current {@code Game} goodness according to
	 * currentPlayerCount and maxAttackable.
	 * 
	 * @return The current {@code Game} goodness.
	 * @see model.game.Game#getCurrentPlayerCount()
	 * @see model.game.Game#getMaxAttackable()
	 */
	public int getGoodness() {
		int goodness = getCurrentPlayerCount() - getMaxAttackable();
		logger.info("Goodness: " + new Integer(goodness).toString());
		return goodness;

	}

	/**
	 * This method is calculate the best move according to game goodness and
	 * captured fields. After the calculation it performs the movement and also
	 * sets the variables which holding this information.
	 * 
	 */
	public void computerMove() {
		List<T> playerFields = getFieldsAsList().stream().filter(x -> x.getState() == player.getPlayer())
				.collect(Collectors.toList());
		T from = null;
		T where = null;
		int max = -1;
		for (int i = 0; i < playerFields.size(); i++) {
			List<T> availableFields = new ArrayList<T>();
			availableFields.addAll(getCloseFieldsFiltered(playerFields.get(i), States.SELECT));
			availableFields.addAll(getFarFieldsFiltered(playerFields.get(i), States.SELECT));
			for (int j = 0; j < availableFields.size(); j++) {
				Game<T> game = new Game<T>(this);

				T gameSelect = game.getField(playerFields.get(i).getAxisX(), playerFields.get(i).getAxisY());
				game.select(gameSelect);

				T gameMoveTo = game.getField(availableFields.get(j).getAxisX(), availableFields.get(j).getAxisY());
				int captured = game.moveSelectedTo(gameMoveTo);
				game.getPlayer().setToOppositePlayer();

				if (game.getGoodness() + captured > max) {
					max = game.getGoodness() + captured;
					from = playerFields.get(i);
					where = availableFields.get(j);
				}
			}
		}
		// System.out.println("from: " + from.getAxisX() + " " + from.getAxisY()
		// + " where: " + where.getAxisX() + " " + where.getAxisY());
		computerFrom = from;
		computerWhere = where;
		logger.info("Computer movement: " + from.toString() + " -> " + where.toString());
		select(from);
		moveSelectedTo(where);
	}

	/**
	 * Returns the Player object.
	 * 
	 * @return The Player object.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Returns the source {@code Field} of computer movement.
	 * 
	 * @return The source {@code Field} of computer movement.
	 */
	public T getComuterFrom() {
		return computerFrom;
	}

	/**
	 * Returns the destination {@code Field} of computer movement.
	 * 
	 * @return The destination {@code Field} of computer movement.
	 */
	public T getComuterWhere() {
		return computerWhere;
	}

}