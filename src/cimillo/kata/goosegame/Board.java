package cimillo.kata.goosegame;

/**
 * @author Giovanna
 *
 *         Class that represents the board of the "Goose Game"
 */
public class Board {

	private enum BoxType {
		BRIDGE, GOOSE, STANDARD, VICTORY;

		private static BoxType getBoxType(int position) {
			if (position == 6) {
				return BRIDGE;
			}
			if (position != 0 && (position % 9 == 5 || position % 9 == 0)) {
				return GOOSE;
			}
			if (position == LAST_POSITION) {
				return VICTORY;
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
	private final static int LAST_POSITION = 63;

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

		if (positionCurrentPlayer > LAST_POSITION) {
			// TODO;
			return "Vittoria";
		}
		Player oldPlayerInPosition = boxes[positionCurrentPlayer];

		if (positionCurrentPlayer != 0) {
			boxes[positionCurrentPlayer] = playerWithDice;
		}

		switch (BoxType.getBoxType(positionCurrentPlayer)) {
		case BRIDGE:
			playerWithDice.advance(BRIDGE_JUMP_POSITION - positionCurrentPlayer);
			msg += "\n\tWOW!\n " + playerWithDice.getName() + " You happened upon the bridge!\n"
					+ playerWithDice.playerStateDescription();
			break;
		case GOOSE:
			playerWithDice.advance(positionCurrentPlayer - playerWithDice.getPreviousPosition());
			msg += "\n\tWOW!\n " + playerWithDice.getName() + " You catch a goose!\n"
					+ playerWithDice.playerStateDescription();
			movePlayers(playerWithDice);
			break;

		default:
			break;
		}

		if (oldPlayerInPosition != null) {

			msg += "\n\tATTENTION!\n" + oldPlayerInPosition.getName() + " falls in the first joke!\n";
			oldPlayerInPosition.advance(-1
					* (playerWithDice.getPosition() - (playerWithDice.getPreviousPosition() - positionCurrentPlayer)));
			msg += oldPlayerInPosition.playerStateDescription();
			msg += movePlayers(oldPlayerInPosition);
		}

		return msg;

	}

}
