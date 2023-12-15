package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;
import eu.nerdfactor.bowling.service.BowlingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/bowling")
@RequiredArgsConstructor
@Tag(name = "Bowling", description = "Bowling Game Api")
public class BowlingController {

	private final BowlingService bowlingService;

	@PostMapping("/{id}/roll/{pins}")
	@Operation(
			summary = "Execute the next roll of a Bowling Game.",
			description = "The next roll of a Bowling Game can be executed by providing the Id of the specific game and the pins that should be next over by the roll."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGame.class), mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", content = {@Content()})
	})
	public ResponseEntity<BowlingGame> nextRoll(@PathVariable int id, @PathVariable(name = "pins") int knockedOverPins)
			throws EntityNotFoundException, WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		BowlingGame game = this.bowlingService.addNextRoll(id, knockedOverPins);
		return ResponseEntity.ok(game);
	}

	@GetMapping("/{id}/score")
	@Operation(
			summary = "Calculate the score of a Bowling Game.",
			description = "The score of a Bowling Game can be calculated by providing the Id of the specific game. The score will be calculated for the current state of the game. It does not have to be finished to be scored."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGame.class), mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", content = {@Content()})
	})
	public ResponseEntity<BowlingGame> calculateScore(@PathVariable int id) throws EntityNotFoundException {
		BowlingGame game = this.bowlingService.calculateCurrentScore(id);
		return ResponseEntity.ok(game);
	}
}
