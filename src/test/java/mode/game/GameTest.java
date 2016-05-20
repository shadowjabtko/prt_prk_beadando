package mode.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.DomXMLReader;
import model.field.Field.States;
import model.game.GameNormal;

public class GameTest {
	GameNormal game;
	GameNormal game2;
	
	@Before
	public void init(){
		game = new GameNormal(1, 1, "hvh");
		game2 = new GameNormal(1, 1, "hvh");
		DomXMLReader<GameNormal> domXMLReader = new DomXMLReader<>(game);
		domXMLReader.setGameFieldFromXML("maps test1.xml");
		
		domXMLReader = new DomXMLReader<GameNormal>(game2);
		domXMLReader.setGameFieldFromXML("maps test2.xml");
	}
	
	@Test
	public void redPlayerCount() {
		assertEquals(2, game.getRedPlayerCount());
		assertEquals(2, game2.getRedPlayerCount());
	};

	@Test
	public void greenPlayerCount() {
		assertEquals(2, game.getGreenPlayerCount());
		assertEquals(1, game2.getGreenPlayerCount());
	};
	
	@Test
	public void playersField() {
		assertEquals(4, game.getPlayersField().size());
		assertEquals(3, game2.getPlayersField().size());
	};
	
	@Test
	public void remainedFieldsCount() {
		assertEquals(21, game.getReaminedFieldsCount());
		assertEquals(22, game2.getReaminedFieldsCount());
	};
	
	@Test
	public void currentPlayer() {
		assertEquals(States.RED_PLAYER, game.getPlayer().getPlayer());
	};
	
	@Test
	public void isEnd() {
		assertEquals(false, game.isEnd());
	};
	
	@Test
	public void isSelectable() {
		assertEquals(false, game.isSelectable(game.getField(3, 3)));
		assertEquals(true, game.isSelectable(game.getField(0, 0)));
		assertEquals(false, game2.isSelectable(game2.getField(0, 0)));
	};
	
	@Test
	public void isMovable() {
		game.select(game.getField(0, 0));
		assertEquals(false, game.isMovable(game.getField(3, 3)));
		assertEquals(true, game.isMovable(game.getField(0, 1)));
		game.unSelect();
	};

	@Test
	public void isAvailableMove() {
		assertEquals(true, game.isAvailableMove());
	};
	
	@Test
	public void mode() {
		assertEquals("hvh", game.getMode());
	}
	
	@Test
	public void closeFields(){
		game.select(game.getField(0, 0));
		assertEquals(true, game.getCloseToSelectedField().contains(game.getField(0, 1)));
		assertEquals(true, game.getCloseToSelectedField().contains(game.getField(1, 0)));
		assertEquals(false, game.getCloseToSelectedField().contains(game.getField(1, 1)));
		game.unSelect();
	}

	@Test
	public void farFields(){
		game.select(game.getField(0, 0));
		assertEquals(false, game.getFarToSelectedField().contains(game.getField(0, 1)));
		assertEquals(true, game.getFarToSelectedField().contains(game.getField(0, 2)));
		assertEquals(true, game.getFarToSelectedField().contains(game.getField(1, 1)));
		game.unSelect();
	}
	
	@Test
	public void maxAttackable() {
		assertEquals(0, game.getMaxAttackable());
		assertEquals(2, game2.getMaxAttackable());
	};

	@Test
	public void move() {
		game2.getPlayer().setToOppositePlayer();
		game2.select(game2.getField(0, 2));
		game2.moveSelectedTo(game2.getField(0, 0));
		assertEquals(0, game2.getRedPlayerCount());
		assertEquals(true, game2.isEnd());
		assertEquals(States.GREEN_PLAYER, game2.getField(0, 1).getState());
		assertEquals(States.GREEN_PLAYER, game2.getField(1, 0).getState());
	};
	
}
