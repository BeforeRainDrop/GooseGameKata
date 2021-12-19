package cimillo.kata.goosegame;

public class Player {

	private String name;

	private Integer position = 0;
	private Integer previousPosition;

	public Player(String name) {
		super();
		this.name = name;
	}

	void advance(int score) {
		previousPosition = position;
		int temp = position + score;
		position = temp >= 0 ? temp : 0;
	}

	public String getName() {
		return name;
	}

	public Integer getPosition() {
		return position;
	}

	public Integer getPreviousPosition() {
		return previousPosition;
	}

	String playerStateDescription() {
		return "\n* " + this.getName() + " * moves from " + this.getPreviousPosition() + " to " + this.getPosition()
				+ " on the board!";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
