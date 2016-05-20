package model.game;

import model.field.Field.States;

public class Player {
	private States player;

	public Player() {
		player = States.RED_PLAYER;
	}

	public void setPlayer(States player) {
		if (player == States.GREEN_PLAYER || player == States.RED_PLAYER) {
			this.player = player;
		}
	}

	public States getPlayer() {
		return player;
	}

	public States getOppositePlayer() {
		States result = States.GREEN_PLAYER;
		if (player == States.GREEN_PLAYER) {
			result = States.RED_PLAYER;
		}
		return result;
	}

	public void setToOppositePlayer() {
		this.player = getOppositePlayer();
	}
}
