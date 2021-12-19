package cimillo.kata.goosegame;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Giovanna
 *
 *         Class that represents the board of the "Goose Game"
 */
public class Board {

	/**
	 * Constant indicating the last box on the board
	 */
	private final static int LAST_POSITION = 63;

	/**
	 * box represents the state of a box on the board.
	 * 
	 * true -> the box is currently occupied by one player;
	 * 
	 * false -> the box is free
	 */
	private boolean[] box;

	/**
	 * positions is a representation of current positioning of the players on the
	 * board
	 */
	private Map<Integer, Player> positions;

	public Board() {
		super();
		box = new boolean[LAST_POSITION];
		positions = new HashMap<Integer, Player>();
	}

}
