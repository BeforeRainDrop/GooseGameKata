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
	 * 
	 *         BRIDGE: the player that land in position 6 goes ahead to the position
	 *         number BRIDGE_JUMP_POSITION;
	 * 
	 *         GOOSE: the player who lands in this sort of boxes can go on for more
	 *         positions;
	 * 
	 *         STANDARD: boxes with no special behavior;
	 * 
	 *         VICTORY: the player in this special box is the winner;
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

		private static boolean isSpecial(int position) {
			return getBoxType(position) != STANDARD;
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
	 * Reference to the game of the board; is used to handle the victory event
	 * 
	 */
	private GooseGame gooseGame;

	public Board(GooseGame gooseGame) {
		this.gooseGame = gooseGame;
		boxes = new Player[LAST_POSITION + 1]; // LAST_POSITION + 1: The board has a box 0 for "Start"
	}

	/**
	 * @param playerWithDice: player object of the move to be made
	 * @param score:          sum of dice scores or number of positions for the move
	 *                        to be made
	 * @return game-dependent messages
	 */
	String movePlayer(Player playerWithDice, int score) {

		int positionBeforeMove = playerWithDice.getPosition();
		playerWithDice.move(score);
//		In case of subsequent jokes we cannot free the previous position (set null to previous)
		boolean existSomeoneElse = gooseGame.getPlayersList().stream()
				.anyMatch(p -> playerWithDice.getPreviousPosition() != 0
						&& !p.getName().equalsIgnoreCase(playerWithDice.getName())
						&& p.getPosition() == playerWithDice.getPreviousPosition());
		if (!existSomeoneElse) {
			boxes[playerWithDice.getPreviousPosition()] = null;
		}

		String msg = "";
		int newPosition = playerWithDice.getPosition();
		if (positionBeforeMove != newPosition) {
			msg += playerWithDice.playerStateDescription();
		}

		if (BoxType.getBoxType(newPosition) == BoxType.VICTORY) {
			gooseGame.setThereIsAWinner(true);
			return msg + "\n\t:) WOW! :)\n " + playerWithDice.getName() + " YOU WIN! :)\n";
		}

//		handle the bounce when player goes beyond the last position
		if (positionBeforeMove + score > LAST_POSITION) {
			newPosition = positionBeforeMove + 1;
			msg += playerWithDice.playerStateDescription() + "\n* " + playerWithDice.getName() + " *  bounces!\n"
					+ playerWithDice.getName() + " goes back to " + playerWithDice.getPosition() + " on the board!";
			msg += movePlayer(playerWithDice, 1);
		}

//		handle special boxes
		switch (BoxType.getBoxType(newPosition)) {
		case BRIDGE:
			msg += "\n\tWOW!\n " + playerWithDice.getName() + " You happened upon the bridge!\n";
			msg += movePlayer(playerWithDice, BRIDGE_JUMP_POSITION - newPosition);
			break;
		case GOOSE:
			msg += "\n\tWOW!\n " + playerWithDice.getName() + " You catch a goose!\n";
			msg += movePlayer(playerWithDice, newPosition - playerWithDice.getPreviousPosition());
			break;

		default:
			break;
		}

		Player oldPlayerInPosition = boxes[newPosition];
		if (newPosition != 0) {
			boxes[newPosition] = playerWithDice;
		}

//		handle the "joke" - position already occupied
		if (oldPlayerInPosition != null) {
			msg += "\n\tATTENTION!\n" + oldPlayerInPosition.getName() + " falls in the joke!\n";
			int joke = newPosition - playerWithDice.getPreviousPosition();
			int jokePosition = oldPlayerInPosition.getPosition() - score;

//			handle the "joke" - SPECIAL position already occupied
//			In this case the player fallen in joke is bounced to start,
//			so we can avoid infinite cycles
			if (BoxType.isSpecial(jokePosition)) {
				msg += "\n\tSorry! :(\nThe joke is seurious." + " bounces to \"Start\"!";
				msg += movePlayer(oldPlayerInPosition, -oldPlayerInPosition.getPosition());
			} else {
				msg += movePlayer(oldPlayerInPosition, -joke);
			}
		}
		return msg;

	}

}
