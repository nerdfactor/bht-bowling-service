package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;
import eu.nerdfactor.bowling.service.BowlingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/bowling")
@RequiredArgsConstructor
public class BowlingController {

	private final BowlingService gameService;

	@PostMapping("/{id}/roll/{pins}")
	public ResponseEntity<BowlingGame> nextRoll(@PathVariable int id, @PathVariable(name = "pins") int knockedOverPins)
			throws EntityNotFoundException, WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		BowlingGame game = this.gameService.addNextRoll(id, knockedOverPins);
		return ResponseEntity.ok(game);
	}

	@GetMapping("/{id}/score")
	public ResponseEntity<BowlingGame> calculateScore(@PathVariable int id) throws EntityNotFoundException {
		BowlingGame game = this.gameService.calculateCurrentScore(id);
		return ResponseEntity.ok(game);
	}
}
