package eu.nerdfactor.bowling.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.service.BowlingGameCrudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test for {@link BowlingGame} REST controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BowlingRestControllerTest {

	private static final String API_PATH = "/api/v1/bowling";

	@Autowired
	ObjectMapper jsonMapper;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Mocked {@link BowlingGameCrudService} in order to provide
	 * mock responses to the tested REST controller.
	 */
	@MockBean
	BowlingGameCrudService gameCrudService;

	/**
	 * Check if {@link BowlingGame Games} can be listed.
	 */
	@Test
	public void gamesCanBeListed() throws Exception {
		List<BowlingGame> mockGames = Arrays.asList(
				BowlingGame.createTestGame(1, 10),
				BowlingGame.createTestGame(2, 10)
		);
		Mockito.when(gameCrudService.listGames())
				.thenReturn(mockGames);

		mockMvc.perform(get(API_PATH))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonMapper.writeValueAsString(mockGames)));
	}

	/**
	 * Check if a {@link BowlingGame} can be created.
	 */
	@Test
	public void gameCanBeCreated() throws Exception {
		BowlingGame mockGame = BowlingGame.createTestGame(1, 10);
		Mockito.when(gameCrudService.createGame(any(BowlingGame.class)))
				.thenReturn(mockGame);

		mockMvc.perform(post(API_PATH)
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.jsonMapper.writeValueAsString(new BowlingGame()))
				).andExpect(status().isOk())
				.andExpect(content().json(jsonMapper.writeValueAsString(mockGame)));
	}

	/**
	 * Check if a {@link BowlingGame} can be read.
	 */
	@Test
	public void gameCanBeRead() throws Exception {
		BowlingGame mockGame = BowlingGame.createTestGame(1, 10);
		Mockito.when(gameCrudService.readGame(any(int.class)))
				.thenReturn(Optional.of(mockGame));

		mockMvc.perform(get(API_PATH + "/1"))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonMapper.writeValueAsString(mockGame)));
	}

	/**
	 * Check if a {@link BowlingGame} can be updated.
	 */
	@Test
	public void gameCanBeUpdated() throws Exception {
		BowlingGame mockGame = BowlingGame.createTestGame(1, 100);
		Mockito.when(gameCrudService.updateGame(argThat(argument -> argument.getId() == mockGame.getId())))
				.thenReturn(mockGame);

		mockMvc.perform(put(API_PATH + "/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.jsonMapper.writeValueAsString(BowlingGame.createTestGame(1, 100)))
				).andExpect(status().isOk())
				.andExpect(content().json(jsonMapper.writeValueAsString(mockGame)));
	}

	/**
	 * Check if a {@link BowlingGame} can be deleted.
	 */
	@Test
	public void gameCanBeDeleted() throws Exception {
		mockMvc.perform(delete(API_PATH + "/1"))
				.andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}
}
