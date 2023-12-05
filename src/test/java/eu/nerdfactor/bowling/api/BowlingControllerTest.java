package eu.nerdfactor.bowling.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.repo.BowlingGameRepository;
import eu.nerdfactor.bowling.service.BowlingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link BowlingGame} controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BowlingControllerTest {

	private static final String API_PATH = "/api/v1/bowling";

	@Autowired
	ObjectMapper jsonMapper;

	@Autowired
	private MockMvc mockMvc;

	/**
	 * Mocked {@link BowlingService} in order to provide
	 * mock responses to the tested controller.
	 */
	@MockBean
	BowlingService bowlingService;

	/**
	 * Setup by deleting all existing {@link BowlingGame Games} and creating on specific {@link BowlingGame} that
	 * can be tested for.
	 *
	 * @param gameRepository A repository implementation for data access.
	 */
	@BeforeAll
	public static void setUp(@Autowired BowlingGameRepository gameRepository) {
		gameRepository.deleteAll();
		gameRepository.save(BowlingGame.createTestGame(1, 0));
		gameRepository.save(BowlingGame.createTestGame(2, 21, List.of(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 3, 2)));

	}

	/**
	 * Check if a roll can be added to a {@link BowlingGame}.
	 */
	@Test
	public void rollsCanBeAddedToGame() throws Exception {
		BowlingGame mockGame = BowlingGame.createTestGame(1, 1, List.of(5));
		Mockito.when(bowlingService.addNextRoll(anyInt(), anyInt()))
				.thenReturn(mockGame);

		mockMvc.perform(post(API_PATH + "/1/roll/5"))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonMapper.writeValueAsString(mockGame)));
	}

	/**
	 * Check if the score for a {@link BowlingGame} can be counted.
	 */
	@Test
	public void scoreOfGameCanBeCounted() throws Exception {
		BowlingGame mockGame = BowlingGame.createTestGame(2, 69, 21, List.of(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 3, 2));
		Mockito.when(bowlingService.calculateCurrentScore(anyInt()))
				.thenReturn(mockGame);

		mockMvc.perform(get(API_PATH + "/1/score"))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonMapper.writeValueAsString(mockGame)));
	}
}
