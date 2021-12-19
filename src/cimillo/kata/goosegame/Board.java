package cimillo.kata.goosegame;

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

	static String playerStateDescription(Player currentPlayer) {
		return "\n* " + currentPlayer.getName() + " * moves from " + currentPlayer.getPreviousPosition() + " to "
				+ currentPlayer.getPosition() + " on the board!";
	}

	/**
	 * boxes represents the state of the boxes that make up the board.
	 * 
	 * boxes[p] : null -> the box in position p is free
	 * 
	 * not null - boxes [p] contains player P -> the box is currently occupied by
	 * the instance P of Player class;
	 * 
	 */
	private Player[] boxes;

	public Board() {
		super();
		boxes = new Player[LAST_POSITION];
	}

	String movePlayers(Player playerWithDice) {

		String msg = "";

		int positionCurrentPlayer = playerWithDice.getPosition();

		if (boxes[positionCurrentPlayer] != null) {
			Player oldPlayerInPosition = boxes[positionCurrentPlayer];
			msg = "\tATTENTION!\n" + oldPlayerInPosition.getName() + " falls in the first joke!\n";
			oldPlayerInPosition.advance(playerWithDice.getPreviousPosition() - positionCurrentPlayer);
			msg += playerStateDescription(oldPlayerInPosition);
		}
		boxes[positionCurrentPlayer] = playerWithDice;
		return msg;

	}

}
