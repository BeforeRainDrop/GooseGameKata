package cimillo.kata.goosegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Giovanna
 *
 *         Main class for the "Goose Game"
 */
public class GooseGame {

	public static void main(String[] args) {

		System.out.println("****************************************");
		System.out.println("\tWelcome to Goose Game!");
		System.out.println("****************************************");
		System.out.println("Press Enter to continue ");
		try (Scanner gameInput = new Scanner(System.in)) {
			gameInput.nextLine();
			GooseGame game = new GooseGame(gameInput);
			game.letsPlay();
		}

	}

	/**
	 * Board on which the game takes place
	 */
	private Board board;

	private Scanner gameInput = null;

	/**
	 * Game participants
	 */
	private List<Player> playersList;

	public GooseGame(Scanner gameInput2) {
		this.gameInput = gameInput2;
		board = new Board();
		playersList = new ArrayList<Player>();
	}

	private void addPlayers(int playersNumber) {
		for (int i = 1; i < playersNumber + 1; i++) {
			boolean validName = false;
			while (!validName) {
				System.out.println("Player " + i + " enter your name:");
				String playerName = gameInput.nextLine();
				if (playerName.trim().isEmpty()) {
					System.out.println("Player " + i + " please enter a not empty name.");
				} else {
					validName = checkParticipant(playerName) || validName;
					if (validName) {
						playersList.add(new Player(playerName));

						String currentPlayers = playersList.stream().map(Player::getName)
								.collect(Collectors.joining(", "));
						System.out.println("\nPlayers: " + currentPlayers);
					}
				}
			}
		}
		System.out.println("********************************************");
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

	/**
	 * Access point to start a set of the game
	 * 
	 * @return the player winning the game
	 */
	private Player letsPlay() {
		System.out.println("The game begins...\n");
		int playersNumber;
		System.out.println("How many players? ");
		playersNumber = gameInput.nextInt();
		gameInput.nextLine(); // to consume the enter after the number for the subsequent nextLine
		addPlayers(playersNumber);
		makesMoves(playersNumber);
		return null;
	}

	private void makesMoves(int playersNumber) {
		for (int i = 0; i < playersNumber; i++) {
			Player currentPlayer = playersList.get(i);
			System.out.println("\n" + currentPlayer.getName() + " press Enter to play!");
			gameInput.nextLine();
			System.out.println("\t" + currentPlayer.getName() + " thows dice...");
			int[] score = throwDice();
			System.out.println(
					"* " + currentPlayer.getName() + " * gets " + score[0] + ", " + score[1] + " at the dice!");
			currentPlayer.advance(score[0] + score[1]);
			System.out.println(Board.playerStateDescription(currentPlayer));

			String msgFromBoard = board.movePlayers(currentPlayer);
			if (!msgFromBoard.isEmpty()) {
				System.out.println(msgFromBoard);
			}

			System.out.println("********************************************");
		}
	}

	private int[] throwDice() {
		int dice[] = new int[2];
		int minScore = 1;
		int maxScore = 6;
		dice[0] = new Random().nextInt(maxScore - minScore) + minScore;
		dice[1] = new Random().nextInt(maxScore - minScore) + minScore;
		return dice;
	}

}
