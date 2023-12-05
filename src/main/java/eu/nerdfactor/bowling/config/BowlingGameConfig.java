package eu.nerdfactor.bowling.config;

import eu.nerdfactor.bowling.repo.BowlingGameRepository;
import eu.nerdfactor.bowling.service.BowlingService;
import eu.nerdfactor.bowling.service.TenPinBowlingRuleset;
import eu.nerdfactor.bowling.service.TenPinBowlingScoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Bowling game configurations.
 */
@Configuration
public class BowlingGameConfig {

	/**
	 * Create a ten pin bowling game service with corresponding ruleset
	 * and scoring strategy.
	 *
	 * @param gameRepository An implementation of repository for data access.
	 * @return A BowlingService for ten pin bowling.
	 */
	@Bean
	@Primary
	@Qualifier("TenPinBowling")
	public BowlingService getTenPinBowlingGameService(@Autowired BowlingGameRepository gameRepository) {
		return new BowlingService(
				new TenPinBowlingRuleset(),
				new TenPinBowlingScoring(),
				gameRepository
		);
	}
}
