package cimillo.kata.goosegame;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
		System.exit(0);

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

	private boolean thereIsAWinner = false;

	public GooseGame(Scanner gameInput) {
		this.gameInput = gameInput;
		board = new Board(this);
		playersList = new ArrayList<Player>();
	}

	private void addPlayers(int playersNumber) {
		for (int i = 1; i < playersNumber + 1; i++) {
			boolean validName = false;
			while (!validName) {
				System.out.println("\nPlayer " + i + " enter your name:");
				String playerName = gameInput.nextLine();
				if (playerName.trim().isEmpty()) {
					System.out.println("Player " + i + " please enter a not empty name.");
				} else {
					validName = checkParticipantName(playerName) || validName;
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
	private boolean checkParticipantName(String playerName) {
		boolean existPlayer = playersList.stream().anyMatch(p -> p.getName().equalsIgnoreCase(playerName));
		if (existPlayer) {
			System.out.println(playerName + ": player already present\n");
		}
		return !existPlayer;
	}

	public List<Player> getPlayersList() {
		return playersList;
	}

	/**
	 * Access point to start a game
	 * 
	 */
	private void letsPlay() {
		System.out.println("The game begins...\n");
		int playersNumber = 0;
		while (playersNumber == 0) {
			System.out.println("How many players? ");
			try {
				playersNumber = gameInput.nextInt();
			} catch (InputMismatchException e) {
				gameInput.nextLine();
				System.out.println("Insert a valid number");
			}
		}
		gameInput.nextLine();
		addPlayers(playersNumber);
		makesMoves(playersNumber);
	}

	private void makesMoves(int playersNumber) {
		int i = 0;

		while (!thereIsAWinner && i <= playersNumber) {
			Player currentPlayer = playersList.get(i);
			System.out.println("\n" + currentPlayer.getName() + " press Enter to play!");
			gameInput.nextLine();
			System.out.println("\t" + currentPlayer.getName() + " thows dice...");

//			Player throws dice
			int[] score = throwDice();
			System.out.println(
					"* " + currentPlayer.getName() + " * gets " + score[0] + ", " + score[1] + " at the dice!");

//			Move player on the board
			String msgFromBoard = board.movePlayer(currentPlayer, score[0] + score[1]);
			System.out.println(msgFromBoard);

			System.out.println("********************************************");
			i = i == playersNumber - 1 ? 0 : i + 1;
		}
	}

	public void setThereIsAWinner(Boolean thereIsAWinner) {
		this.thereIsAWinner = thereIsAWinner;
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
