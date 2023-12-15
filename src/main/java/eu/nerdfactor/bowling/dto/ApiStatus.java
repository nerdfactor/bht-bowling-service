package eu.nerdfactor.bowling.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Wrapper Object to transfer basic status information about
 * the Api.
 */
@Getter
@Setter
public class ApiStatus {

	private boolean status = true;

	// todo: get version from gradle build file?
	private String version = "1.0.0";

	private String mainUrl = "/api/v1";

	private String docsUrl = "/api/v1/docs";

	private String swaggerUrl = "/api/v1/swagger";
}
