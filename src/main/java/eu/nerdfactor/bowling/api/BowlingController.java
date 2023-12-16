package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.dto.BowlingGameDto;
import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.exceptions.MaxAmountOfRollsExceededException;
import eu.nerdfactor.bowling.exceptions.WrongAmountOfPinsException;
import eu.nerdfactor.bowling.service.BowlingGameCrudService;
import eu.nerdfactor.bowling.service.BowlingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/bowling")
@RequiredArgsConstructor
@Tag(name = "Bowling", description = "Bowling Game Api")
public class BowlingController {

	private final ModelMapper modelMapper;
	private final BowlingService bowlingService;
	private final BowlingGameCrudService bowlingGameCrudService;

	@PostMapping(value = "/start", produces = "application/hal+json")
	@Operation(
			summary = "Start a new Bowling Game.",
			description = "Start a new Bowling Game that can be used to execute rolls and calculate scores."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	public ResponseEntity<BowlingGameDto> startGame() {
		BowlingGame game = new BowlingGame();
		this.bowlingGameCrudService.createGame(game);
		BowlingGameDto dto = this.modelMapper.map(game, BowlingGameDto.class);
		dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
		return ResponseEntity.ok(dto);
	}

	@PostMapping(value = "/{id}/roll/{pins}", produces = "application/hal+json")
	@Operation(
			summary = "Execute the next roll of a Bowling Game.",
			description = "The next roll of a Bowling Game can be executed by providing the Id of the specific game and the pins that should be next over by the roll."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	@ApiResponse(responseCode = "404", content = {@Content()})
	public ResponseEntity<BowlingGameDto> nextRoll(@PathVariable int id, @PathVariable(name = "pins") int knockedOverPins)
			throws EntityNotFoundException, WrongAmountOfPinsException, MaxAmountOfRollsExceededException {
		BowlingGame game = this.bowlingService.addNextRoll(id, knockedOverPins);
		BowlingGameDto dto = this.modelMapper.map(game, BowlingGameDto.class);
		dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/{id}/score", produces = "application/hal+json")
	@Operation(
			summary = "Calculate the score of a Bowling Game.",
			description = "The score of a Bowling Game can be calculated by providing the Id of the specific game. The score will be calculated for the current state of the game. It does not have to be finished to be scored."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	@ApiResponse(responseCode = "404", content = {@Content()})
	public ResponseEntity<BowlingGameDto> calculateScore(@PathVariable int id) throws EntityNotFoundException {
		BowlingGame game = this.bowlingService.calculateCurrentScore(id);
		BowlingGameDto dto = this.modelMapper.map(game, BowlingGameDto.class);
		dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
		return ResponseEntity.ok(dto);
	}
}
