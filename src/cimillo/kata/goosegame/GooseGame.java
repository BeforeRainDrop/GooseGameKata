package cimillo.kata.goosegame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Giovanna
 *
 *         Main class for the "Goose Game"
 */
public class GooseGame {

	/**
	 * Access point to start a set of the game
	 * 
	 * @return the player winning the game
	 */
	private static Player letsPlay() {
		System.out.println("The game begins...\n");
		GooseGame game = new GooseGame();

		try (Scanner gameInput = new Scanner(System.in)) {
			int playersNumber;
			System.out.println("How many players? ");
			playersNumber = gameInput.nextInt();
			game.addPlayers(playersNumber);

		}
		return null;
	}

	public static void main(String[] args) {

		System.out.println("****************************************");
		System.out.println("\tWelcome to Goose Game!");
		System.out.println("****************************************");
		System.out.println("Press Enter to continue ");
		try {
			System.in.read();
			letsPlay();
		} catch (IOException e) {
			System.out.println("Sorry, something has gone wrong.");
		}

	}

	/**
	 * Game participants
	 */
	private List<Player> playersList;

	/**
	 * Board on which the game takes place
	 */
	private Board board;

	public GooseGame() {
		board = new Board();
		playersList = new ArrayList<Player>();
	}

	private void addPlayers(int playersNumber) {
		try (Scanner gameInput = new Scanner(System.in)) {
			for (int i = 1; i < playersNumber + 1; i++) {
				boolean validName = false;
				while (!validName) {
					System.out.println("Player " + i + " enter your name: ");
					String playerName = gameInput.nextLine();

					if (playerName == null || playerName.trim().isEmpty()) {
						System.out.println("Player " + i + " please enter a not empty name.");
					} else {
						validName = checkParticipant(playerName) || validName;
						if (validName) {
							playersList.add(new Player(playerName));

							String currentPlayers = playersList.stream().map(Player::getName)
									.collect(Collectors.joining(", "));
							System.out.println("Players: " + currentPlayers);
						}
					}
				}
			}
		}
	}

	/**
	 * @param playerName
	 * @return true if the player name is unique in the current state of the game
	 */
	private boolean checkParticipant(String playerName) {
		boolean existPlayer = playersList.stream().anyMatch(p -> p.getName().equalsIgnoreCase(playerName));
		if (existPlayer) {
			System.out.println(playerName + ": player already present");
		}
		return !existPlayer;
	}

}
