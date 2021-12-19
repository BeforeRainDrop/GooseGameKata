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
		position += score;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
