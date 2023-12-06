package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;
import eu.nerdfactor.bowling.repo.BowlingGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class BowlingServiceTest {

	@MockBean
	BowlingGameRepository bowlingGameRepository;

	BowlingRuleset bowlingRuleset = new TenPinBowlingRuleset();

	@Autowired
	BowlingService bowlingService;

	@BeforeEach
	void setUpMockRepository() {
		Mockito.when(bowlingGameRepository.save(any()))
				.then(AdditionalAnswers.returnsFirstArg());
	}

	/**
	 * Check if a game without any knocked over pins has a score of zero.
	 */
	@Test
	void gameWithoutKnockedOverPinsHasScoreOfZero() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 0;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		for (int roll = 0; roll < bowlingRuleset.amountOfMaxRolls(); roll++) {
			this.bowlingService.addNextRoll(1, 0);
		}
		BowlingGame scored = this.bowlingService.calculateCurrentScore(1);
		Assertions.assertEquals(expectedScore, scored.getCurrentScore());
	}

	/**
	 * Check if the total score of the game is counted correctly using
	 * the same amount of knocked over pins for each roll.
	 */
	@Test
	void countTotalScoreOfKnockedOverPins() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 60;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		for (int roll = 0; roll < bowlingRuleset.amountOfMaxRolls(); roll++) {
			this.bowlingService.addNextRoll(1, 3);
		}
		BowlingGame scored = this.bowlingService.calculateCurrentScore(1);
		Assertions.assertEquals(expectedScore, scored.getCurrentScore());
	}

	/**
	 * Check if the total score of the game is counted correctly if all
	 * rolls are strikes. The amount of rolls will be one for each frame
	 * * and two additional bonus rolls for the last frame being a strike.
	 */
	@Test
	void countTotalScoreOfGameWithAllStrikes() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 300;
		int amountOfFrames = bowlingRuleset.amountOfFrames() + 2;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		for (int roll = 0; roll < amountOfFrames; roll++) {
			this.bowlingService.addNextRoll(1, bowlingRuleset.amountOfPins());
		}
		BowlingGame scored = this.bowlingService.calculateCurrentScore(1);
		Assertions.assertEquals(expectedScore, scored.getCurrentScore());
	}

	/**
	 * Check if the total score of the game is counted correctly using
	 * a random amount of knocked over pins without strikes.
	 */
	@Test
	void countTotalScoreOfRandomKnockedOverPins() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 0;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		for (int roll = 1; roll < bowlingRuleset.amountOfMaxRolls(); roll += 2) {
			expectedScore += this.rollRandomTestFrame(1);
		}
		BowlingGame scored = this.bowlingService.calculateCurrentScore(1);
		Assertions.assertEquals(expectedScore, scored.getCurrentScore());
	}

	/**
	 * Rolls a complete frame with a randomly selected amount of knocked over pins
	 * split over two rolls. Makes sure that the frame won't contain a spare or
	 * a strike.
	 *
	 * @param id The game id that the rolls will be for.
	 * @return The amount of randomly selected knocked over pins.
	 */
	private int rollRandomTestFrame(int id) throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int possibleKnockedOverPinsWithoutStrikes = bowlingRuleset.amountOfPins() - 1;
		int randomKnockedOverPins = new Random().nextInt(0, possibleKnockedOverPinsWithoutStrikes);
		int pinsInFirstRoll = randomKnockedOverPins / 2;
		int pinsInSecondRoll = randomKnockedOverPins - pinsInFirstRoll;
		this.bowlingService.addNextRoll(id, pinsInFirstRoll);
		this.bowlingService.addNextRoll(id, pinsInSecondRoll);
		return randomKnockedOverPins;
	}

	/**
	 * Check if the total score of the game is counted correctly in the
	 * edge case that the last frame is a spare. This must include one
	 * 21st roll as a bonus roll.
	 */
	@Test
	void countTotalScoreWithLastFrameSpare() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 68;
		int amountOfRollsWithoutLastFrame = bowlingRuleset.amountOfMaxRolls() - 2;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		for (int roll = 1; roll < amountOfRollsWithoutLastFrame; roll++) {
			this.bowlingService.addNextRoll(1, 3);
		}
		// roll a spare in the last frame
		this.bowlingService.addNextRoll(1, bowlingRuleset.amountOfPins() / 2);
		this.bowlingService.addNextRoll(1, bowlingRuleset.amountOfPins() / 2);

		// and add a 21st bonus roll
		this.bowlingService.addNextRoll(1, 4);

		BowlingGame scored = this.bowlingService.calculateCurrentScore(1);
		Assertions.assertEquals(expectedScore, scored.getCurrentScore());
	}


	/**
	 * Check if the total score of the game is counted correctly in the
	 * edge case that the last frame is a strike. This must include one
	 * 21st roll as a bonus roll.
	 */
	@Test
	void countTotalScoreWithLastFrameStrike() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int expectedScore = 71;
		int amountOfRollsWithoutLastFrame = bowlingRuleset.amountOfMaxRolls() - 2;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		for (int roll = 1; roll < amountOfRollsWithoutLastFrame; roll++) {
			this.bowlingService.addNextRoll(1, 3);
		}
		// roll a strike in the last frame
		this.bowlingService.addNextRoll(1, bowlingRuleset.amountOfPins());

		// add the normal 20th roll
		this.bowlingService.addNextRoll(1, 3);

		// and add a 21st bonus roll
		this.bowlingService.addNextRoll(1, 4);

		BowlingGame scored = this.bowlingService.calculateCurrentScore(1);
		Assertions.assertEquals(expectedScore, scored.getCurrentScore());
	}


	/**
	 * Check if the total score of the game is counted correctly in the
	 * edge case that the last frame is a strike by comparing to different
	 * bonus rolls.
	 */
	@Test
	void gameCorrectlyCountsBonusRollInLastFrame() throws WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		int[] firstFullGameWithBonusRoll = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 3, 4};
		BowlingGame mockGame1 = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame1));
		for (int i : firstFullGameWithBonusRoll) {
			this.bowlingService.addNextRoll(1, i);
		}
		BowlingGame firstGameTotalScored = this.bowlingService.calculateCurrentScore(1);

		int[] secondFullGameWithBonusRoll = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 3, 2};
		BowlingGame mockGame2 = BowlingGame.createTestGame(2, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame2));
		for (int i : secondFullGameWithBonusRoll) {
			this.bowlingService.addNextRoll(2, i);
		}
		BowlingGame secondGameTotalScored = this.bowlingService.calculateCurrentScore(1);


		int scoreDifferenceBetweenGames = secondGameTotalScored.getCurrentScore() - firstGameTotalScored.getCurrentScore();
		int expectedScoreDifferenceBetweenGames = secondFullGameWithBonusRoll[secondFullGameWithBonusRoll.length - 1] - firstFullGameWithBonusRoll[firstFullGameWithBonusRoll.length - 1];

		Assertions.assertNotEquals(firstGameTotalScored.getCurrentScore(), secondGameTotalScored.getCurrentScore());
		Assertions.assertEquals(scoreDifferenceBetweenGames, expectedScoreDifferenceBetweenGames);
	}


	/**
	 * Check if the game won't allow wrong amounts of knocked over pins
	 * in a roll. Wrong amounts of knocked over pins are either negativ
	 * or exceeding the {@link BowlingRuleset#amountOfPins()  maximum amount of pins}.
	 */
	@Test
	void gameWontAllowWrongAmountOfKnockedOverPins() {
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		Assertions.assertThrows(WrongAmountOfPinsException.class, () -> this.bowlingService.addNextRoll(1, bowlingRuleset.amountOfPins() + 1));
		Assertions.assertThrows(WrongAmountOfPinsException.class, () -> this.bowlingService.addNextRoll(1, -1));
	}

	/**
	 * Check if a game won't allow to exceed the
	 * {@link BowlingRuleset#amountOfMaxRolls() maximum amount of rolls}.
	 */
	@Test
	void gameWontExceedMaxAmountOfRolls() {
		int amountOfRolls = bowlingRuleset.amountOfMaxRolls() + 1;
		BowlingGame mockGame = BowlingGame.createTestGame(1, 0);
		Mockito.when(bowlingGameRepository.findById(anyInt()))
				.thenReturn(Optional.of(mockGame));
		Assertions.assertThrows(MaxAmountOfRollsExceededException.class, () -> {
			for (int currentRoll : new int[amountOfRolls]) {
				this.bowlingService.addNextRoll(1, currentRoll);
			}
		});

	}
}
