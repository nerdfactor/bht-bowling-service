package eu.nerdfactor.bowling.service;

public interface BowlingRuleset {

	/**
	 * The amount of maximum rolls in a bowling game.
	 */
	int amountOfMaxRolls();

	/**
	 * The amount of possible bonus rolls in a bowling game.
	 */
	int amountOfBonusRolls();

	/**
	 * The amount of possible frames in a bowling game.
	 */
	int amountOfFrames();

	/**
	 * The amount of pins a bowling game has.
	 */
	int amountOfPins();

	/**
	 * Check if the amount of knocked over pins is not possible.
	 * todo: may require a full game for more specific checks?
	 *
	 * @param knockedOverPins The amount of knocked over pins.
	 * @return True if the amount of knocked over pins are not possible.
	 */
	boolean wouldKnockOverWrongAmountOfPins(int knockedOverPins);

	/**
	 * Check if the roll would exceed the maximum amount of rolls.
	 * todo: may require a full game for more specific checks?
	 *
	 * @param roll The checked roll.
	 * @return True if the maximum amount is exceeded.
	 */
	boolean wouldExceedMaxRolls(int roll);
}
