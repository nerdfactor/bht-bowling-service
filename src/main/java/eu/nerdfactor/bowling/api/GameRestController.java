package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.entity.Game;
import eu.nerdfactor.bowling.service.GameCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameRestController {

	private final GameCrudService gameCrudService;

	@GetMapping
	public ResponseEntity<List<Game>> listGames() {
		return ResponseEntity.ok(this.gameCrudService.listGames());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Game> readGame(@PathVariable int id) {
		Optional<Game> read = this.gameCrudService.readGame(id);
		return ResponseEntity.of(read);
	}

	@PostMapping
	public ResponseEntity<Game> createGame(@RequestBody Game game) {
		Game created = this.gameCrudService.createGame(game);
		return ResponseEntity.ok(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Game> updateGame(@PathVariable int id, @RequestBody Game game) {
		Game updated = this.gameCrudService.updateGame(game);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGame(@PathVariable int id) {
		this.gameCrudService.deleteGameById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
