package eu.nerdfactor.bowling.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * A simple game of bowling.
 */
@Getter
@Setter
public class Game {

	/**
	 * Internal identifier for this specific game.
	 */
	private int id;

	/**
	 * The roll this game is on. Required to access the correct
	 * array element in the knockedOverPins.
	 */
	private int currentRoll;

	/**
	 * The amount of knocked over pins for each roll. The array will
	 * never exceed the maximum amount of rolls.
	 */
	private int[] knockedOverPinsPerRoll;

	/**
	 * The current score for this game.
	 */
	public int currentScore;
}
