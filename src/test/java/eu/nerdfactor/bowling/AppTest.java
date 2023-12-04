package eu.nerdfactor.bowling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Basic Application tests.
 */
@SpringBootTest
public class AppTest {


	/**
	 * Check if the Spring ApplicationContext is loaded after starting the Application.
	 *
	 * @param applicationContext The application context.
	 */
	@Test
	void springApplicationContextLoads(@Autowired ApplicationContext applicationContext) {
		assertNotNull(applicationContext);
	}

}
