package eu.nerdfactor.bowling.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nerdfactor.bowling.dto.ApiStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

	@Autowired
	ObjectMapper jsonMapper;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Check if the status response can be loaded from the api.
	 */
	@Test
	void applicationStatusCanBeRequested() throws Exception {
		String response = mockMvc.perform(get("/api/v1"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		ApiStatus status = this.jsonMapper.readValue(response, ApiStatus.class);
		Assertions.assertTrue(status.isStatus());
		Assertions.assertTrue(status.hasLinks());
		Assertions.assertTrue(status.hasLink("swagger"));
	}
}
