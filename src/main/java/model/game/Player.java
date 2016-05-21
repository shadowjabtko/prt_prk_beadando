package model.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.field.Field.States;

/**
 * A {@code Player} represent the player of the {@code Game}.
 * @author ShadowJabtko
 *
 */

public class Player {
	
	Logger logger = LoggerFactory.getLogger(Player.class);
	
	/**
	 * It is holding the current player.
	 */
	private States player;
	
	/**
	 * Construct a newly allocated {@code Player} and sets the starting player
	 * to Red player.
	 */
	public Player() {
		player = States.RED_PLAYER;
	}
	
	/**
	 * Set the current player.
	 * @param player The new state of the player.
	 */
	public void setPlayer(States player) {
		if (player == States.GREEN_PLAYER || player == States.RED_PLAYER) {
			this.player = player;
		} else {
			logger.warn("Player states can be: " + States.RED_PLAYER + " or " + States.GREEN_PLAYER);
		}
	}
	
	/**
	 * Returns the current player.
	 * @return The current player.
	 */
	public States getPlayer() {
		return player;
	}
	/**
	 * Returns the opposite player of the current player.
	 * @return The opposite player of the current player.
	 */
	public States getOppositePlayer() {
		States result = States.GREEN_PLAYER;
		if (player == States.GREEN_PLAYER) {
			result = States.RED_PLAYER;
		}
		return result;
	}
	/**
	 * Sets the current player to the opposite player.
	 */
	public void setToOppositePlayer() {
		this.player = getOppositePlayer();
	}
}
