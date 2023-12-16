package eu.nerdfactor.bowling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * Wrapper Object to transfer basic status information about
 * the Api.
 */
@Getter
@Setter
public class ApiStatus extends RepresentationModel<ApiStatus> {

	private boolean status = true;

	// todo: get version from gradle build file?
	private String version = "1.0.0";
}
