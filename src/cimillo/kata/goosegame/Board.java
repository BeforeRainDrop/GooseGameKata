package cimillo.kata.goosegame;

/**
 * @author Giovanna
 *
 *         Class that represents the board of the "Goose Game"
 */
public class Board {

	/**
	 * @author Giovanna
	 *
	 *         This Enum represent the various types of boxes on the board;
	 */
	private enum BoxType {
		BRIDGE, GOOSE, STANDARD, VICTORY;

		/**
		 * @param position
		 * @return the type of the box at the specified position
		 */
		private static BoxType getBoxType(int position) {
			if (position == LAST_POSITION) {
				return VICTORY;
			}
			if (position == 6) {
				return BRIDGE;
			}
			if (position != 0 && (position % 9 == 5 || position % 9 == 0)) {
				return GOOSE;
			}
			return STANDARD;
		}

	}

	/**
	 * Constant indicating the position of the box on which the player jumps on the
	 * bridge
	 */
	private final static int BRIDGE_JUMP_POSITION = 12;

	/**
	 * Constant indicating the last box on the board
	 */
	final static int LAST_POSITION = 63;

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

	/**
	 * Reference to the game of the board
	 */
	private GooseGame gooseGame;

	public Board(GooseGame gooseGame) {
		this.gooseGame = gooseGame;
		boxes = new Player[LAST_POSITION + 1]; // The board has a box 0 for "Start"
	}

	String movePlayer(Player playerWithDice, int score) {

		int previousPositionCurrentPlayer = playerWithDice.getPosition();
		playerWithDice.move(score);
		boolean existSomeoneElse = gooseGame.getPlayersList().stream()
				.anyMatch(p -> playerWithDice.getPreviousPosition() != 0
						&& !p.getName().equalsIgnoreCase(playerWithDice.getName())
						&& p.getPosition() == playerWithDice.getPreviousPosition());
		if (!existSomeoneElse) { // in case of subsequent jokes
			boxes[playerWithDice.getPreviousPosition()] = null;
		}
		String msg = "";

		int newPositionCurrentPlayer = playerWithDice.getPosition();
		if (previousPositionCurrentPlayer != newPositionCurrentPlayer) {
			msg += playerWithDice.playerStateDescription();
		}

		if (BoxType.getBoxType(newPositionCurrentPlayer) == BoxType.VICTORY) {
			gooseGame.setThereIsAWinner(true);
			return msg + "\n\t:) WOW! :)\n " + playerWithDice.getName() + " YOU WIN! :)\n";
		}
		if (previousPositionCurrentPlayer + score > LAST_POSITION) {
			newPositionCurrentPlayer = previousPositionCurrentPlayer + 1;
			msg += playerWithDice.playerStateDescription() + "\n* " + playerWithDice.getName() + " *  bounces!\n"
					+ playerWithDice.getName() + " goes back to " + playerWithDice.getPosition() + " on the board!";
			msg += movePlayer(playerWithDice, 1);
		}

		Player oldPlayerInPosition = boxes[newPositionCurrentPlayer];
		if (newPositionCurrentPlayer != 0) {
			boxes[newPositionCurrentPlayer] = playerWithDice;
		}

		switch (BoxType.getBoxType(newPositionCurrentPlayer)) {
		case BRIDGE:
			msg += "\n\tWOW!\n " + playerWithDice.getName() + " You happened upon the bridge!\n";
			msg += movePlayer(playerWithDice, BRIDGE_JUMP_POSITION - newPositionCurrentPlayer);
			break;
		case GOOSE:
			msg += "\n\tWOW!\n " + playerWithDice.getName() + " You catch a goose!\n";
			msg += movePlayer(playerWithDice, newPositionCurrentPlayer - playerWithDice.getPreviousPosition());
			break;

		default:
			break;
		}

		if (oldPlayerInPosition != null) {
			msg += "\n\tATTENTION!\n" + oldPlayerInPosition.getName() + " falls in the joke!\n";
			int joke = newPositionCurrentPlayer - playerWithDice.getPreviousPosition();

			msg += movePlayer(oldPlayerInPosition, -joke);
		}
		return msg;

	}

}
