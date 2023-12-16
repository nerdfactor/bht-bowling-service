package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import org.springframework.stereotype.Component;

/**
 * Strategy for scoring bowling games.
 */
@Component
public interface ScoringStrategy {

	/**
	 * Count the score for a {@link BowlingGame} using a specified {@link BowlingRuleset}.
	 *
	 * @param game    The {@link BowlingGame} to score.
	 * @param ruleset The {@link BowlingRuleset} used for scoring.
	 * @return The total score.
	 */
	int countScore(BowlingGame game, BowlingRuleset ruleset);
}
