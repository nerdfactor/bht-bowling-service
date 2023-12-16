package eu.nerdfactor.bowling.dto;

import eu.nerdfactor.bowling.config.KnockedOverPinsConvert;
import eu.nerdfactor.bowling.entity.BowlingGame;
import eu.nerdfactor.bowling.validation.MaxCurrentRolls;
import eu.nerdfactor.bowling.validation.MaxPossibleScore;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BowlingGameDto extends RepresentationModel<BowlingGameDto> {

	private int id;

	/**
	 * The roll this {@link BowlingGame} is on. Required to access the correct
	 * array element in the knockedOverPins.
	 */
	@Min(0)
	@MaxCurrentRolls
	private int currentRoll;

	/**
	 * The amount of knocked over pins for each roll. The array will
	 * never exceed the maximum amount of rolls.
	 */
	@Convert(converter = KnockedOverPinsConvert.class)
	private List<Integer> knockedOverPinsPerRoll = new ArrayList<>();

	/**
	 * The current score for this {@link BowlingGame}.
	 */
	@Min(0)
	@MaxPossibleScore
	private int currentScore;
}
