package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.dto.BowlingGameDto;
import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.service.BowlingGameCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/bowling")
@RequiredArgsConstructor
@Tag(name = "Bowling", description = "Bowling Game Api")
public class BowlingRestController {

	private final ModelMapper modelMapper;
	private final BowlingGameCrudService bowlingGameCrudService;

	@GetMapping(produces = "application/hal+json")
	@Operation(
			summary = "Get a list of Bowling Games.",
			description = "Get a list of all Bowling Games."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	public ResponseEntity<List<BowlingGameDto>> listGames() {
		return ResponseEntity.ok(this.bowlingGameCrudService.listGames().stream()
				.map(bowlingGame -> {
					BowlingGameDto dto = this.modelMapper.map(bowlingGame, BowlingGameDto.class);
					dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
					return dto;
				}).toList());
	}

	@GetMapping(value = "/{id}", produces = "application/hal+json")
	@Operation(
			summary = "Get a Bowling Game.",
			description = "Get a Bowling Game specified by the provided id."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	public ResponseEntity<BowlingGameDto> readGame(@PathVariable int id) {
		BowlingGameDto dto = this.bowlingGameCrudService.readGame(id)
				.map(entity -> this.modelMapper.map(entity, BowlingGameDto.class))
				.orElseThrow();
		dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
		return ResponseEntity.ok(dto);
	}

	@PostMapping(produces = "application/hal+json")
	@Operation(
			summary = "Create a Bowling Game.",
			description = "Create a new Bowling Game with the provided data about a Bowling Game."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	public ResponseEntity<BowlingGameDto> createGame(@RequestBody @Valid BowlingGame game) {
		BowlingGame created = this.bowlingGameCrudService.createGame(game);
		BowlingGameDto dto = this.modelMapper.map(created, BowlingGameDto.class);
		dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
		return ResponseEntity.ok(dto);
	}

	@PutMapping(value = "/{id}", produces = "application/hal+json")
	@Operation(
			summary = "Update a Bowling Game.",
			description = "Update a Bowling game with the provided data and specified id."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BowlingGameDto.class), mediaType = "application/hal+json")})
	public ResponseEntity<BowlingGameDto> updateGame(@PathVariable int id, @RequestBody @Valid BowlingGame game) {
		BowlingGame updated = this.bowlingGameCrudService.updateGame(game);
		BowlingGameDto dto = this.modelMapper.map(updated, BowlingGameDto.class);
		dto.add(linkTo(methodOn(BowlingRestController.class).readGame(dto.getId())).withSelfRel());
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Delete a Bowling Game.",
			description = "Delete a Bowling Game specified by the id."
	)
	@ApiResponse(responseCode = "204", content = {@Content()})
	public ResponseEntity<Void> deleteGame(@PathVariable int id) {
		this.bowlingGameCrudService.deleteGameById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
