package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.service.BowlingGameCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Bowling", description = "Bowling Game Api")
public class BowlingRestController {

	private final BowlingGameCrudService bowlingGameCrudService;

	@GetMapping
	@Operation(
			summary = "Get a list of Bowling Games.",
			description = "Get a list of all Bowling Games."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGame.class), mediaType = "application/json")}),
	})
	public ResponseEntity<List<BowlingGame>> listGames() {
		return ResponseEntity.ok(this.bowlingGameCrudService.listGames());
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get a Bowling Game.",
			description = "Get a Bowling Game specified by the provided id."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGame.class), mediaType = "application/json")}),
	})
	public ResponseEntity<BowlingGame> readGame(@PathVariable int id) {
		Optional<BowlingGame> read = this.bowlingGameCrudService.readGame(id);
		return ResponseEntity.of(read);
	}

	@PostMapping
	@Operation(
			summary = "Create a Bowling Game.",
			description = "Create a new Bowling Game with the provided data about a Bowling Game."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGame.class), mediaType = "application/json")}),
	})
	public ResponseEntity<BowlingGame> createGame(@RequestBody @Valid BowlingGame game) {
		BowlingGame created = this.bowlingGameCrudService.createGame(game);
		return ResponseEntity.ok(created);
	}

	@PutMapping("/{id}")
	@Operation(
			summary = "Update a Bowling Game.",
			description = "Update a Bowling game with the provided data and specified id."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGame.class), mediaType = "application/json")}),
	})
	public ResponseEntity<BowlingGame> updateGame(@PathVariable int id, @RequestBody @Valid BowlingGame game) {
		BowlingGame updated = this.bowlingGameCrudService.updateGame(game);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Delete a Bowling Game.",
			description = "Delete a Bowling Game specified by the id."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "204", content = {@Content()})
	})
	public ResponseEntity<Void> deleteGame(@PathVariable int id) {
		this.bowlingGameCrudService.deleteGameById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
