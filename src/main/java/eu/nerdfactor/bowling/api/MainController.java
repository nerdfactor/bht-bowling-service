package eu.nerdfactor.bowling.api;

import eu.nerdfactor.bowling.dto.ApiStatus;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Tag(name = "Bowling", description = "Bowling Game Api")
@OpenAPIDefinition(
		info = @Info(
				title = "Bowling Game Api",
				description = "An Api for Bowling Game related functionality like scoring games.",
				license = @License(name = "MIT License")
		),
		servers = {@Server(description = "Bowling Game Api")}
)
public class MainController {

	/**
	 * The main endpoint of the application should just show a life sign
	 * so that consumers know that there is something behind the server.
	 *
	 * @return {@link ResponseEntity} with {@link HttpStatus} 200.
	 */
	@GetMapping(value = {"", "/"})
	@Hidden
	public ResponseEntity<Void> index() {
		return ResponseEntity.ok().build();
	}


	/**
	 * The main api entrypoint will return some status information about the api.
	 * It also contains links for clients to follow and start using the application.
	 *
	 * @return {@link ApiStatus} with basic status information.
	 */
	@Operation(
			summary = "Retrieve basic information about the Api",
			description = "The endpoint returns basic information about the Api. This should help to find out if the Api is running and functioning. In addition it will contain more information about the Api (version, spec etc.)."
	)
	@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ApiStatus.class), mediaType = "application/hal+json")})
	@GetMapping(value = "/api/v1", produces = "application/hal+json")
	public ResponseEntity<ApiStatus> api() {
		ApiStatus model = new ApiStatus();

		model.add(linkTo(methodOn(MainController.class).api()).withSelfRel());
		model.add(linkTo(methodOn(MainController.class).api()).slash("docs").withRel("docs"));
		model.add(linkTo(methodOn(MainController.class).api()).slash("swagger").withRel("swagger"));
		model.add(linkTo(methodOn(BowlingController.class).startGame()).withRel("startBowlingGame"));
		model.add(linkTo(methodOn(BowlingRestController.class).listGames()).withRel("listBowlingGames"));

		return ResponseEntity.ok(model);
	}

	/**
	 * The old status endpoint now redirects to the Api entry endpoint.
	 */
	@GetMapping(value = "/api/v1/status")
	@Hidden
	public void status(HttpServletResponse response) throws IOException {
		response.sendRedirect("/api/v1");
	}

	/**
	 * The Api endpoint without version redirects to the current versioned entry endpoint.
	 */
	@GetMapping(value = "/api")
	@Hidden
	public void apiWithoutVersion(HttpServletResponse response) throws IOException {
		response.sendRedirect("/api/v1");
	}
}
