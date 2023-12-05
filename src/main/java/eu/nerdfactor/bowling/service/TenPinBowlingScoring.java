package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import org.springframework.stereotype.Component;

@Component
public class TenPinBowlingScoring implements ScoringStrategy {

	private BowlingGame game;

	private BowlingRuleset ruleset;

	@Override
	public int countScore(BowlingGame game, BowlingRuleset ruleset) {
		this.game = game;
		this.ruleset = ruleset;
		int currentScore = 0;
		int checkedRoll = 0;
		for (int frame = 0; frame < ruleset.amountOfFrames(); frame++) {
			if (game.isRollAStrike(currentScore, ruleset)) {
				currentScore += countScoreForStrike(checkedRoll);
				checkedRoll++;
			} else if (game.isRollASpare(currentScore, ruleset)) {
				currentScore += countScoreForSpare(checkedRoll);
				checkedRoll += 2;
			} else {
				currentScore += countScoreForOpenFrame(checkedRoll);
				checkedRoll += 2;
			}
		}
		return currentScore;
	}


	/**
	 * Calculates the score for a strike.
	 * The score for a strike is counted by adding the knocked over pins (not the score) of
	 * the next two rolls to the amount of knocked over pins from the strike (always the maximum).
	 *
	 * @param roll The roll that should be checked. Assumes that the checked roll is the first roll in the frame.
	 * @return The score for the strike.
	 */
	private int countScoreForStrike(int roll) {
		int strikeBonus = game.getKnockedOverPinsOfRoll(roll + 1) + game.getKnockedOverPinsOfRoll(roll + 2);
		return strikeBonus + ruleset.amountOfPins();
	}

	/**
	 * Calculates the score for a spare.
	 * The score for a spare is counted by adding the knocked over pins (not the score) of
	 * the first roll in the next frame to the knocked over pins from the spare (always the maximum).
	 *
	 * @param roll The roll that should be checked. Assumes that the checked roll is the first roll in the frame.
	 * @return The score of the spare.
	 */
	private int countScoreForSpare(int roll) {
		int spareBonus = game.getKnockedOverPinsOfRoll(roll + 2);
		return spareBonus + ruleset.amountOfPins();
	}

	/**
	 * Calculates the score for an open frame.
	 * An open frame is a frame that not is a spare or strike. The score is calculated by
	 * adding the knocked over pins of both rolls.
	 *
	 * @param roll The roll that should be checked. Assumes that the checked roll is the first roll in the frame.
	 * @return The score of the open frame.
	 */
	private int countScoreForOpenFrame(int roll) {
		return game.getKnockedOverPinsOfRoll(roll) + game.getKnockedOverPinsOfRoll(roll + 1);
	}
}
