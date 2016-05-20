package model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.field.Field;
import model.field.Field.States;

public class Game<T extends Field> implements GameInterface<T>{

	protected ArrayList<ArrayList<T>> fields;
	protected Integer sizeX;
	protected Integer sizeY;
	protected String mode;

	private Player player = new Player();
	private T selectedField;
	private List<T> closeToSelectedField;
	private List<T> farToSelectedField;
	private T computerFrom;
	private T computerWhere;

	public Game(Integer sizeX, Integer sizeY, String mode) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.mode = mode;
		fields = new ArrayList<ArrayList<T>>();
		for (int i = 0; i < sizeX; i++) {
			ArrayList<T> column = new ArrayList<T>();
			for (int j = 0; j < sizeY; j++) {
				T field = newFieldInstance(i, j);
				column.add(field);
			}
			fields.add(column);
		}
	}
	
	public Game(Game<T> game){
		this.sizeX = game.getSizeX();
		this.sizeY = game.getSizeY();
		this.mode = game.getMode();
		
		fields = new ArrayList<ArrayList<T>>();
		for (int i = 0; i < sizeX; i++) {
			ArrayList<T> column = new ArrayList<T>();
			for (int j = 0; j < sizeY; j++) {
				T field = newFieldInstance(i, j);
				field.setState(game.getField(i, j).getState());
				column.add(field);
			}
			fields.add(column);
		}
		player.setPlayer(game.getPlayer().getPlayer());
	}

	@Override
	public T newFieldInstance(int i, int j) {
		@SuppressWarnings("unchecked")
		T field = (T) new Field(i,j);
		return field;
	}
	
	public void setField(T field){
		fields.get(field.getAxisX()).add(field);
	}

	public String getMode(){
		return mode;
	}
	
	public T getField(int i, int j) {
		return fields.get(i).get(j);
	}

	public Integer getSizeX() {
		return sizeX;
	}

	public Integer getSizeY() {
		return sizeY;
	}

	public ArrayList<ArrayList<T>> getFields() {
		return fields;
	}

	public void setSizeX(int sizeX) {
		if (sizeX > this.sizeX) {
			for (int i = 0; i < sizeX - this.sizeX; i++) {
				ArrayList<T> column = new ArrayList<T>();
				
				for (int j = 0; j < sizeY; j++) {
					// @SuppressWarnings("unchecked")
					// T field =(T) new FieldGraphical(States.SELECT,this.sizeX
					// + i, j);
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

		this.sizeX = sizeX;
	}
	
	//abstract T newInstance(int i, int j);
	
	public void setSizeY(int sizeY) {
		if (sizeY > this.sizeY) {
			for (int i = 0; i < sizeX; i++) {
				
				for (int j = 0; j < sizeY - this.sizeY; j++) {
					// @SuppressWarnings("unchecked")
					// T field =(T) new
					// FieldGraphical(States.SELECT,i,this.sizeY + j);
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

		this.sizeY = sizeY;
	}

	public List<T> getFieldsAsList() {
		return fields.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
	}

	public List<T> getVisibleFields() {
		return fields.stream().flatMap(x -> x.stream()).filter(x -> !(x.getState() == States.DELETE))
				.collect(Collectors.toList());
	}

	public List<T> getPlayersField() {
		return fields.stream().flatMap(x -> x.stream())
				.filter(x -> x.getState() == States.RED_PLAYER || x.getState() == States.GREEN_PLAYER)
				.collect(Collectors.toList());
	}

	public List<T> getCloseFields(Field field) {
		Integer axisX = field.getAxisX();
		Integer axisY = field.getAxisY();
		if (axisX % 2 == 0) {
			return fields.stream().flatMap(x -> x.stream())
					.filter(x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 1 || x.getAxisY() == axisY + 1))
							|| ((x.getAxisX() == axisX - 1 || x.getAxisX() == axisX + 1)
									&& (x.getAxisY() == axisY || x.getAxisY() == axisY - 1)))
					.collect(Collectors.toList());
		} else {
			return fields.stream().flatMap(x -> x.stream())
					.filter(x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 1 || x.getAxisY() == axisY + 1))
							|| ((x.getAxisX() == axisX - 1 || x.getAxisX() == axisX + 1)
									&& (x.getAxisY() == axisY || x.getAxisY() == axisY + 1)))
					.collect(Collectors.toList());
		}

	}

	public List<T> getFarFields(Field field) {
		Integer axisX = field.getAxisX();
		Integer axisY = field.getAxisY();
		if (axisX % 2 == 0) {
			return fields.stream()
					.flatMap(x -> x.stream()).filter(
							x -> (x.getAxisX() == axisX && (x.getAxisY() == axisY - 2 || x.getAxisY() == axisY + 2))
									|| ((x.getAxisX() == axisX + 1
											|| x.getAxisX() == axisX - 1)
											&& (x.getAxisY() == axisY - 2 || x.getAxisY() == axisY + 1))
									|| ((x.getAxisX() == axisX - 2 || x.getAxisX() == axisX + 2)
											&& (x.getAxisY() == axisY || x.getAxisY() == axisY + 1
													|| x.getAxisY() == axisY - 1)))
					.collect(Collectors.toList());
		} else {
			return fields.stream()
					.flatMap(x -> x.stream()).filter(
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

	public List<T> getCloseFieldsFiltered(Field field, States filter) {
		return getCloseFields(field).stream().filter(x -> x.getState() == filter).collect(Collectors.toList());
	}

	public List<T> getFarFieldsFiltered(Field field, States filter) {
		return getFarFields(field).stream().filter(x -> x.getState() == filter).collect(Collectors.toList());
	}

	public boolean isSelectable(T field) {
		if (field.getState() == player.getPlayer()) {
			return true;
		}
		return false;
	}

	public void select(T field) {
		if (isSelectable(field)) {
			selectedField = field;
			closeToSelectedField = getCloseFieldsFiltered(selectedField, States.SELECT);
			farToSelectedField = getFarFieldsFiltered(selectedField, States.SELECT);
		} else {
			System.err.println("Not selectable!");
		}
	}

	public List<T> getCloseToSelectedField() {
		return closeToSelectedField;
	}

	public List<T> getFarToSelectedField() {
		return farToSelectedField;
	}

	public boolean isMovable(T where) {
		if (selectedField != null && (closeToSelectedField.contains(where) || farToSelectedField.contains(where))) {
			return true;
		}
		
		return false;
	}
	
	public void unSelect(){
		selectedField = null;
		closeToSelectedField = null;
		farToSelectedField = null;
	}
	
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
		} else {
			//System.out.println(player.getPlayer() + " " + selectedField + " -> " + where);
			System.err.println("Not movable!");
		}
		
		return result;
	}
	
	public int getCurrentPlayerCount(){
		if (player.getPlayer() == States.RED_PLAYER) {
			return getRedPlayerCount();
		} else {
			return getGreenPlayerCount();
		}
	}
	
	public int getRedPlayerCount() {
		return (int) getFieldsAsList().stream().filter(x -> x.getState() == States.RED_PLAYER).count();
	}

	public int getGreenPlayerCount() {
		return (int) getFieldsAsList().stream().filter(x -> x.getState() == States.GREEN_PLAYER).count();
	}

	public int getReaminedFieldsCount() {
		return (int) getFieldsAsList().stream().filter(x -> x.getState() == States.SELECT).count();
	}

	public boolean isAvailableMove() {
		int count = getFieldsAsList().stream().filter(x -> x.getState() == player.getPlayer()).mapToInt(
				x -> getCloseFieldsFiltered(x, States.SELECT).size() + getFarFieldsFiltered(x, States.SELECT).size())
				.sum();
		if (count == 0) {
			return false;
		}
		return true;
	}

	public boolean isEnd() {
		if (getReaminedFieldsCount() == 0 || getRedPlayerCount() == 0 || getGreenPlayerCount() == 0
				|| !isAvailableMove()) {
			return true;
		}
		return false;
	}
	
	public int whoWon() {
		if (isEnd()) {
			if (getRedPlayerCount() > getGreenPlayerCount()) {
				return 1;
			} else if (getRedPlayerCount() < getGreenPlayerCount()) {
				return 2;
			} else {
				return 0;
			}
		}
		return -1;
	}
	
	public int getMaxAttackable(){
		List<T> oppositePlayerFields = getFieldsAsList().stream().filter(x -> x.getState() == player.getOppositePlayer()).collect(Collectors.toList());
		List<T> availableOppositeFields = oppositePlayerFields.stream().flatMap(x -> {
			ArrayList<T> result = new ArrayList<T>();
			result.addAll(getCloseFieldsFiltered(x, States.SELECT));
			result.addAll(getFarFieldsFiltered(x, States.SELECT));
			return result.stream();
		}).sorted((x,y) -> {
			if (x.getAxisX() == y.getAxisX()) {
				return x.getAxisY().compareTo(y.getAxisY());
			} else{
				return x.getAxisX().compareTo(y.getAxisX()); 
			}
		}).distinct().collect(Collectors.toList());
		return availableOppositeFields.stream().mapToInt(x -> getCloseFieldsFiltered(x, player.getPlayer()).size()).max().getAsInt();
		//return 0;
	}
	
	
	public int getCost() {
		//System.out.println(getMaxAttackable());
		return getCurrentPlayerCount() - getMaxAttackable();
		
	}
	
	public void computerMove(){
		List<T> playerFields = getFieldsAsList().stream().filter(x -> x.getState() == player.getPlayer()).collect(Collectors.toList());
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
				
				//System.out.println("Captured: " + captured + " Att: " + game.getMaxAttackable() + " Curr: " + game.getCurrentPlayerCount() + " ossz: " + (game.getCost() + captured));
				
				if (game.getCost() + captured > max) {
					max = game.getCost() + captured;
					from = playerFields.get(i);
					where = availableFields.get(j);
				}
			}
		}
		//System.out.println("from: " + from.getAxisX() + " " + from.getAxisY() + " where: " + where.getAxisX() + " " + where.getAxisY());
		computerFrom = from;
		computerWhere = where;
		select(from);
		moveSelectedTo(where);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public T getComuterFrom(){
		return computerFrom;
	}
	
	public T getComuterWhere(){
		return computerWhere;
	}
	

}