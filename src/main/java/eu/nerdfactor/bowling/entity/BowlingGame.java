package eu.nerdfactor.bowling.entity;

import eu.nerdfactor.bowling.config.KnockedOverPinsConvert;
import eu.nerdfactor.bowling.service.BowlingRuleset;
import eu.nerdfactor.bowling.validation.MaxCurrentRolls;
import eu.nerdfactor.bowling.validation.MaxPossibleScore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple game of bowling.
 */
@Entity
@Getter
@Validated
@NoArgsConstructor
public class BowlingGame {

	/**
	 * Internal identifier for this specific {@link BowlingGame}.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * The roll this {@link BowlingGame} is on. Required to access the correct
	 * array element in the knockedOverPins.
	 */
	@Min(0)
	@MaxCurrentRolls
	private int currentRoll;

	/**
	 * The amount of knocked over pins for each roll. The array will
	 * never exceed the maximum amount of rolls.
	 */
	@Column(name = "knocked_pins")
	@Convert(converter = KnockedOverPinsConvert.class)
	private List<Integer> knockedOverPinsPerRoll = new ArrayList<>();

	/**
	 * The current score for this {@link BowlingGame}.
	 */
	@Min(0)
	@MaxPossibleScore
	private int currentScore;

	public void dangerouslyOverrideCurrentScore(int score) {
		this.currentScore = score;
	}

	/**
	 * Executes the next roll in the game. The result of the roll
	 * are the knocked over pins, which will be recorded in order to
	 * count the score.
	 *
	 * @param knockedOverPins The amount of pins that where knocked over in the roll.
	 */
	public void nextRoll(int knockedOverPins) {
		this.knockedOverPinsPerRoll.add(knockedOverPins);
		this.currentRoll++;
	}

	/**
	 * Get the knocked over pins in a specific roll.
	 *
	 * @param roll The specified roll.
	 * @return The amount of pins knocked over in a roll.
	 * @throws IndexOutOfBoundsException If the specified roll is not within the possible amount of rolls.
	 */
	public int getKnockedOverPinsOfRoll(int roll) throws IndexOutOfBoundsException {
		return this.knockedOverPinsPerRoll.get(roll);
	}

	/**
	 * Check if a knockedOverPins is a strike.
	 * A strike is a knockedOverPins that knocks over all pins at once.
	 *
	 * @param roll    The knockedOverPins that should be checked.
	 * @param ruleset The {@link BowlingRuleset} to be checked against.
	 * @return True if the knockedOverPins is a strike.
	 */
	public boolean isRollAStrike(int roll, BowlingRuleset ruleset) {
		return this.knockedOverPinsPerRoll.get(roll) == ruleset.amountOfPins();
	}

	/**
	 * Check if a roll is a spare.
	 * A spare is a roll where both rolls of a frame knock over all pins.
	 *
	 * @param roll    The roll that should be checked.
	 * @param ruleset The {@link BowlingRuleset} to be checked against.
	 * @return True if the roll is a spare.
	 */
	public boolean isRollASpare(int roll, BowlingRuleset ruleset) {
		return this.knockedOverPinsPerRoll.get(roll) + this.knockedOverPinsPerRoll.get(roll + 1) == ruleset.amountOfPins();
	}

	public static BowlingGame createTestGame(int id, int score) {
		// todo: just needed for tests. won't be needed after introducing DTO?
		BowlingGame game = new BowlingGame();
		game.id = id;
		game.currentScore = score;
		return game;
	}

	public static BowlingGame createTestGame(int id, int currentRoll, List<Integer> knockedOverPinsPerRoll) {
		// todo: just needed for tests. won't be needed after introducing DTO?
		BowlingGame game = new BowlingGame();
		game.id = id;
		game.currentRoll = currentRoll;
		game.knockedOverPinsPerRoll = knockedOverPinsPerRoll;
		return game;
	}

	public static BowlingGame createTestGame(int id, int score, int currentRoll, List<Integer> knockedOverPinsPerRoll) {
		// todo: just needed for tests. won't be needed after introducing DTO?
		BowlingGame game = new BowlingGame();
		game.id = id;
		game.currentScore = score;
		game.currentRoll = currentRoll;
		game.knockedOverPinsPerRoll = knockedOverPinsPerRoll;
		return game;
	}
}
