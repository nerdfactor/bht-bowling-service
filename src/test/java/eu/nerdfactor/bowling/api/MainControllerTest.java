package eu.nerdfactor.bowling.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nerdfactor.bowling.dto.ApiStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
		mockMvc.perform(get("/api/v1/status"))
				.andExpect(status().isOk())
				.andExpect(content().json(this.jsonMapper.writeValueAsString(new ApiStatus())));
	}
}
