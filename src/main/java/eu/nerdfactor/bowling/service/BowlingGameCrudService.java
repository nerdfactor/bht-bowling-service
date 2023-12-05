package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.repo.BowlingGameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for basic CRUD operations on {@link BowlingGame Games}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BowlingGameCrudService {

	/**
	 * Inject a specific implementation of a repository for data access.
	 */
	private final BowlingGameRepository gameRepository;

	/**
	 * List all {@link BowlingGame Games}.
	 *
	 * @return A {@link List} containing all {@link BowlingGame Games}.
	 */
	public List<BowlingGame> listGames() {
		return this.gameRepository.findAll();
	}

	/**
	 * Create a {@link BowlingGame}.
	 *
	 * @param game The {@link BowlingGame} to be created.
	 * @return The created {@link BowlingGame} with an id from the database.
	 */
	public BowlingGame createGame(BowlingGame game) {
		return this.gameRepository.save(game);
	}

	/**
	 * Read a {@link BowlingGame} by its id.
	 *
	 * @param id The id of the specified {@link BowlingGame}.
	 * @return An {@link Optional} of the {@link BowlingGame}. It contains the {@link BowlingGame} if it was found. It is null if the game was not found.
	 */
	public Optional<BowlingGame> readGame(int id) {
		return this.gameRepository.findById(id);
	}

	/**
	 * Update a {@link BowlingGame}.
	 *
	 * @param game The updated data of the {@link BowlingGame}.
	 * @return The updated {@link BowlingGame}.
	 */
	public BowlingGame updateGame(BowlingGame game) {
		return this.gameRepository.save(game);
	}

	/**
	 * Delete a {@link BowlingGame}.
	 *
	 * @param game The {@link BowlingGame} to be deleted.
	 */
	public void deleteGame(BowlingGame game) {
		this.gameRepository.delete(game);
	}

	/**
	 * Delete a {@link BowlingGame} specifying its id.
	 *
	 * @param id The id of the to be deleted {@link BowlingGame}.
	 */
	public void deleteGameById(int id) {
		this.gameRepository.deleteById(id);
	}
}
