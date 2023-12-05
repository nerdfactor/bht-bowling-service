package eu.nerdfactor.bowling.service;

import eu.nerdfactor.bowling.entity.BowlingGame;
import org.springframework.stereotype.Component;

@Component
public interface ScoringStrategy {

	int countScore(BowlingGame game, BowlingRuleset ruleset);
}
