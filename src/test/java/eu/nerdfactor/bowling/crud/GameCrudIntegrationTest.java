package eu.nerdfactor.bowling.crud;

import eu.nerdfactor.bowling.entity.Game;
import eu.nerdfactor.bowling.repo.GameRepository;
import eu.nerdfactor.bowling.service.GameCrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Test for database integration of {@link Game} CRUD operations.
 */
@SpringBootTest
public class GameCrudIntegrationTest {

	@Autowired
	GameCrudService gameCrudService;

	/**
	 * Setup by deleting all existing {@link Game Games} and creating on specific {@link Game} that
	 * can be tested for.
	 *
	 * @param gameRepository A repository implementation for data access.
	 */
	@BeforeAll
	public static void setUp(@Autowired GameRepository gameRepository) {
		gameRepository.deleteAll();
		gameRepository.save(new Game());
	}

	/**
	 * Check if a {@link Game} can be created.
	 */
	@Test
	@Transactional
	@Rollback
	public void gameCanBeCreated() {
		Game prepared = new Game();
		Game created = this.gameCrudService.createGame(prepared);
		Assertions.assertNotNull(created);
		Assertions.assertNotEquals(0, created.getId());
	}

	/**
	 * Check if a {@link Game} can be read.
	 */
	@Test
	@Transactional
	@Rollback
	public void gameCanBeRead() {
		Game read = this.gameCrudService.readGame(1).orElseThrow();
		Assertions.assertNotNull(read);
		Assertions.assertEquals(1, read.getId());
	}

	/**
	 * Check if a {@link Game} can be updated.
	 */
	@Test
	@Transactional
	@Rollback
	public void gameCanBeUpdated() {
		int testScore = 100;
		Game prepared = new Game();
		prepared.setId(1);
		prepared.setCurrentScore(testScore);
		Game updated = this.gameCrudService.updateGame(prepared);
		Assertions.assertNotNull(updated);
		Assertions.assertEquals(prepared.getCurrentScore(), updated.currentScore);
	}

	/**
	 * Check if a {@link Game} can be deleted.
	 */
	@Test
	@Transactional
	@Rollback
	public void gameCanBeDeleted() {
		this.gameCrudService.deleteGameById(1);
		Assertions.assertThrows(Throwable.class, () -> {
			this.gameCrudService.readGame(1).orElseThrow();
		});
	}

	/**
	 * Check if {@link Game Games} can be listed.
	 */
	@Test
	@Transactional
	@Rollback
	public void gamesCanBeListed() {
		List<Game> allGames = this.gameCrudService.listGames();
		Assertions.assertEquals(1, allGames.size());
	}

}
