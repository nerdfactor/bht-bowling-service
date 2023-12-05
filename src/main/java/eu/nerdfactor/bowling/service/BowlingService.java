package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;
import eu.nerdfactor.bowling.repo.BowlingGameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BowlingService {

	/**
	 * The used {@link BowlingRuleset} for scoring and checks.
	 */
	private final BowlingRuleset bowlingRuleset;

	/**
	 * The used {@link ScoringStrategy} for scoring.
	 */
	private final ScoringStrategy scoringStrategy;

	/**
	 * Inject a specific implementation of a repository for data access.
	 */
	private final BowlingGameRepository gameRepository;

	/**
	 * Adds the next roll to a game by executing its next roll with the provided amount
	 * of knocked over pins.
	 *
	 * @param id              The id of the game.
	 * @param knockedOverPins The amount of knocked over pins.
	 * @return The updated game.
	 * @throws WrongAmountOfPinsException        If a wrong amount off knocked over pins was passed.
	 * @throws MaxAmountOfRollsExceededException If the maximum of rolls in the game was exceeded.
	 * @throws EntityNotFoundException           If the game could not be found.
	 */
	public BowlingGame addNextRoll(int id, int knockedOverPins)
			throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException, EntityNotFoundException {
		BowlingGame game = this.gameRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		if (bowlingRuleset.wouldKnockOverWrongAmountOfPins(knockedOverPins)) {
			throw new WrongAmountOfPinsException();
		}
		if (bowlingRuleset.wouldExceedMaxRolls(game.getCurrentRoll())) {
			throw new MaxAmountOfRollsExceededException();
		}
		game.nextRoll(knockedOverPins);
		this.gameRepository.save(game);
		return game;
	}

	/**
	 * Calculates the score for a game with the currently used scoring strategy.
	 *
	 * @param id The id of the game.
	 * @return The updated game.
	 * @throws EntityNotFoundException If the game could not be found.
	 */
	public BowlingGame calculateCurrentScore(int id) throws EntityNotFoundException {
		BowlingGame game = this.gameRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);

		int totalScore = this.scoringStrategy.countScore(game, this.bowlingRuleset);
		game.dangerouslyOverrideCurrentScore(totalScore);
		this.gameRepository.save(game);
		return game;
	}

}
