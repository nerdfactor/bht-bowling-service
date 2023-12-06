package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.service.BowlingGameCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/bowling")
@RequiredArgsConstructor
public class BowlingRestController {

	private final BowlingGameCrudService gameCrudService;

	@GetMapping
	public ResponseEntity<List<BowlingGame>> listGames() {
		return ResponseEntity.ok(this.gameCrudService.listGames());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BowlingGame> readGame(@PathVariable int id) {
		Optional<BowlingGame> read = this.gameCrudService.readGame(id);
		return ResponseEntity.of(read);
	}

	@PostMapping
	public ResponseEntity<BowlingGame> createGame(@RequestBody BowlingGame game) {
		BowlingGame created = this.gameCrudService.createGame(game);
		return ResponseEntity.ok(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BowlingGame> updateGame(@PathVariable int id, @RequestBody BowlingGame game) {
		BowlingGame updated = this.gameCrudService.updateGame(game);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGame(@PathVariable int id) {
		this.gameCrudService.deleteGameById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
