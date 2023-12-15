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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * so that consumers know that there is something behind the api.
	 *
	 * @return ResponseEntity with HTTP 200.
	 */
	@GetMapping(value = {"", "/"})
	@Hidden
	public ResponseEntity<Void> index() {
		return ResponseEntity.ok().build();
	}

	/**
	 * A simple status endpoint to show more information about the api.
	 *
	 * @return ResponseEntity with HTTP 200 and basic status info.
	 */
	@Operation(
			summary = "Retrieve status information about the Api",
			description = "The endpoint returns status information about theApi. This should help to find out if the Api is running and functioning. In addition it will contain more information about the Api (version, spec etc.)."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ApiStatus.class), mediaType = "application/json")})
	})
	@GetMapping(value = "/api/v1/status", produces = "application/json")
	public ResponseEntity<ApiStatus> status() {
		return ResponseEntity.ok(new ApiStatus());
	}
}
