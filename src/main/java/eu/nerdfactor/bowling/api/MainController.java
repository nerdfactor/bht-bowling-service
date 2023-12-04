package eu.nerdfactor.bowling.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainController {

	/**
	 * The main endpoint of the application should just show a life sign
	 * so that consumers know that there is something behind the api.
	 *
	 * @return ResponseEntity with HTTP 200.
	 */
	@GetMapping
	public ResponseEntity<?> index() {
		return ResponseEntity.ok().build();
	}

	/**
	 * A simple status endpoint to show more information about the api.
	 *
	 * @return ResponseEntity with HTTP 200 and basic status info.
	 */
	@GetMapping("/status")
	public ResponseEntity<?> status() {
		return ResponseEntity.ok("{\"status\":true}");
	}
}
