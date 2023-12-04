package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.Game;
import eu.nerdfactor.bowling.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for basic CRUD operations on {@link Game Games}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GameCrudService {

	/**
	 * Inject a specific implementation of a repository for data access.
	 */
	private final GameRepository gameRepository;

	/**
	 * List all {@link Game Games}.
	 *
	 * @return A {@link List} containing all {@link Game Games}.
	 */
	public List<Game> listGames() {
		return this.gameRepository.findAll();
	}

	/**
	 * Create a {@link Game}.
	 *
	 * @param game The {@link Game} to be created.
	 * @return The created {@link Game} with an id from the database.
	 */
	public Game createGame(Game game) {
		return this.gameRepository.save(game);
	}

	/**
	 * Read a {@link Game} by its id.
	 *
	 * @param id The id of the specified {@link Game}.
	 * @return An {@link Optional} of the {@link Game}. It contains the {@link Game} if it was found. It is null if the game was not found.
	 */
	public Optional<Game> readGame(int id) {
		return this.gameRepository.findById(id);
	}

	/**
	 * Update a {@link Game}.
	 *
	 * @param game The updated data of the {@link Game}.
	 * @return The updated {@link Game}.
	 */
	public Game updateGame(Game game) {
		return this.gameRepository.save(game);
	}

	/**
	 * Delete a {@link Game}.
	 *
	 * @param game The {@link Game} to be deleted.
	 */
	public void deleteGame(Game game) {
		this.gameRepository.delete(game);
	}

	/**
	 * Delete a {@link Game} specifying its id.
	 *
	 * @param id The id of the to be deleted {@link Game}.
	 */
	public void deleteGameById(int id) {
		this.gameRepository.deleteById(id);
	}
}
