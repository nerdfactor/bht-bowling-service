package eu.nerdfactor.bowling.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * A simple game of bowling.
 */
@Getter
@Setter
@Entity
public class Game {

	/**
	 * Internal identifier for this specific {@link Game}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * The roll this {@link Game} is on. Required to access the correct
	 * array element in the knockedOverPins.
	 */
	private int currentRoll;

	/**
	 * The amount of knocked over pins for each roll. The array will
	 * never exceed the maximum amount of rolls.
	 */
	@Column(name = "knocked_pins")
	private int[] knockedOverPinsPerRoll;

	/**
	 * The current score for this {@link Game}.
	 */
	public int currentScore;
}
