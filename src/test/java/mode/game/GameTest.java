package mode.game;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import data.DomXMLReader;
import data.DomXMLWriter;
import model.field.Field.States;
import model.game.GameNormal;

public class GameTest {
	GameNormal game;
	GameNormal game2;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void init() {
		game = new GameNormal(1, 1, "hvh");
		game2 = new GameNormal(1, 1, "hvh");
		DomXMLReader<GameNormal> domXMLReader = new DomXMLReader<>(game);
		domXMLReader.setGameFieldFromXML("maps", "test1.xml");

		domXMLReader = new DomXMLReader<GameNormal>(game2);
		domXMLReader.setGameFieldFromXML("maps", "test2.xml");
	}

	@Test
	public void getFieldTest() {
		assertEquals(2, game.getRedPlayerCount());
		assertEquals(2, game2.getRedPlayerCount());
	};

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
	public void closeFields() {
		game.select(game.getField(0, 0));
		assertEquals(true, game.getCloseToSelectedField().contains(game.getField(0, 1)));
		assertEquals(true, game.getCloseToSelectedField().contains(game.getField(1, 0)));
		assertEquals(false, game.getCloseToSelectedField().contains(game.getField(1, 1)));
		game.unSelect();
	}

	@Test
	public void farFields() {
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
	public void goodness() {
		assertEquals(2, game.getGoodness());
	}

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

	@Test
	public void visibleTest(){
		assertEquals(25, game2.getVisibleFields().size());
		game2.getField(2, 2).setState(States.DELETE);
		assertEquals(24, game2.getVisibleFields().size());
	}
	
	@Test
	public void sizeChanging(){
		game2.setSizeX(game2.getSizeX() - 1);
		game2.setSizeY(game2.getSizeY() - 2);
		assertEquals(4, game2.getSizeX().intValue());
		assertEquals(3, game2.getSizeY().intValue());
	}
	
	
	@Test
	public void xmlWriter() {
		DomXMLWriter<GameNormal> domXMLWriter = new DomXMLWriter<GameNormal>(game);
		try {
			domXMLWriter.writeOut(folder.newFile("test_save.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void computerTest() {
		game = new GameNormal(1, 1, "hvc");
		DomXMLReader<GameNormal> domXMLReader = new DomXMLReader<>(game);
		domXMLReader.setGameFieldFromXML("maps", "test1.xml");
		game.getPlayer().setToOppositePlayer();
		game.computerMove();
		assertEquals(true, game.getComuterFrom() != null);
		assertEquals(true, game.getComuterWhere() != null);
	}

}
