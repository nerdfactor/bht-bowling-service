package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.repo.BowlingGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Test for database integration of {@link BowlingGame} CRUD operations.
 */
@SpringBootTest
class BowlingGameCrudIntegrationTest {

	@Autowired
	BowlingGameCrudService gameCrudService;

	/**
	 * Setup by deleting all existing {@link BowlingGame Games} and creating on specific {@link BowlingGame} that
	 * can be tested for.
	 *
	 * @param gameRepository A repository implementation for data access.
	 */
	@BeforeAll
	public static void setUp(@Autowired BowlingGameRepository gameRepository) {
		gameRepository.save(BowlingGame.createTestGame(1, 100));
	}

	/**
	 * Check if a {@link BowlingGame} can be created.
	 */
	@Test
	@Transactional
	@Rollback
	void gameCanBeCreated() {
		BowlingGame prepared = new BowlingGame();
		BowlingGame created = this.gameCrudService.createGame(prepared);
		Assertions.assertNotNull(created);
		Assertions.assertNotEquals(0, created.getId());
	}

	/**
	 * Check if a {@link BowlingGame} can be read.
	 */
	@Test
	@Transactional
	@Rollback
	void gameCanBeRead() {
		BowlingGame read = this.gameCrudService.readGame(1).orElseThrow();
		Assertions.assertNotNull(read);
		Assertions.assertEquals(1, read.getId());
	}

	/**
	 * Check if a {@link BowlingGame} can be updated.
	 */
	@Test
	@Transactional
	@Rollback
	void gameCanBeUpdated() {
		BowlingGame prepared = BowlingGame.createTestGame(2, 100);
		BowlingGame updated = this.gameCrudService.updateGame(prepared);
		Assertions.assertNotNull(updated);
		Assertions.assertEquals(prepared.getCurrentScore(), updated.getCurrentScore());
	}

	/**
	 * Check if a {@link BowlingGame} can be deleted.
	 */
	@Test
	@Transactional
	@Rollback
	void gameCanBeDeleted() {
		this.gameCrudService.deleteGameById(1);
		Assertions.assertThrows(Throwable.class, () -> {
			this.gameCrudService.readGame(1).orElseThrow();
		});
	}

	/**
	 * Check if {@link BowlingGame Games} can be listed.
	 */
	@Test
	@Transactional
	@Rollback
	void gamesCanBeListed() {
		List<BowlingGame> allGames = this.gameCrudService.listGames();
		Assertions.assertFalse(allGames.isEmpty());
	}

}
