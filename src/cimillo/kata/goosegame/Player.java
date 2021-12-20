package cimillo.kata.goosegame;

/**
 * @author Giovanna
 *
 *         Class representing a player in the game
 */
public class Player {

	private String name;
	private int position = 0;
	private int previousPosition;

	static String positionsDescription(Player subject, int previousPosition, int newPosition) {
		return "\n* " + subject.getName() + " * moves from " + previousPosition + " to " + newPosition + " on the board!";
	}
	
	public Player(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public int getPreviousPosition() {
		return previousPosition;
	}

	/**
	 * @param score - the sum to use to update the player's positions
	 */
	void move(int score) {
		int temp = position + score;
		if (temp <= Board.LAST_POSITION) {
			previousPosition = position;
			position = temp >= 0 ? temp : 0;
		}
	}

	String playerStateDescription() {
		return positionsDescription(this, this.getPreviousPosition(), this.getPosition());
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
