package eu.nerdfactor.bowling.service;

import org.springframework.stereotype.Component;

/**
 * An ruleset implementation for ten pin bowling.
 */
@Component
public class TenPinBowlingRuleset implements BowlingRuleset {

	/**
	 * The amount of maximum rolls in a game.The player has 2 rolls in
	 * 10 frames each. The amount rolls can only exceed 20, if the last
	 * frame contains a spare or strike. This would result in one additional
	 * bonus roll.
	 */
	@Override
	public int amountOfMaxRolls() {
		return 21;
	}

	/**
	 * The amount of possible bonus rolls in a bowling game.
	 */
	@Override
	public int amountOfBonusRolls() {
		return 1;
	}

	/**
	 * The amount of possible frames in a game. Each frame will contain two
	 * rolls. There may be a bonus roll in an additional frame, if the last
	 * frame contains a spare or strike
	 */
	@Override
	public int amountOfFrames() {
		return 10;
	}

	/**
	 * The amount of pins a bowling game has.
	 */
	@Override
	public int amountOfPins() {
		return 10;
	}

	/**
	 * Check if the amount of knocked over pins is not possible.
	 *
	 * @param knockedOverPins The amount of knocked over pins.
	 * @return True if the amount of knocked over pins are not possible.
	 */
	public boolean wouldKnockOverWrongAmountOfPins(int knockedOverPins) {
		return knockedOverPins < 0 || knockedOverPins > amountOfPins();
	}

	/**
	 * Check if the roll would exceed the maximum amount of rolls.
	 *
	 * @return True if the maximum amount is exceeded.
	 */
	public boolean wouldExceedMaxRolls(int roll) {
		return roll >= amountOfMaxRolls();
	}
}
