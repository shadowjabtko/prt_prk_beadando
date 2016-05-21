package model.game;

import model.field.Field;
/**
 * Represents the Game in normal mode (not graphically).
 * It is useful for testing,because it is not need graphical objects, only using 
 * the Game core.
 * 
 * @author ShadowJabtko
 * @see model.game.Game
 */
public class GameNormal extends Game<Field>{

	public GameNormal(Integer sizeX, Integer sizeY, String mode) {
		super(sizeX, sizeY,mode);
	}

}
