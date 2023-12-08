package eu.nerdfactor.bowling.validation;

import eu.nerdfactor.bowling.service.BowlingRuleset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaxCurrentRollsValidator implements ConstraintValidator<MaxCurrentRolls, Object> {

	@Autowired
	private BowlingRuleset bowlingRuleset;

	@Override
	public void initialize(MaxCurrentRolls constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (this.bowlingRuleset == null) {
			return false;
		}
		if (value instanceof Integer || value instanceof Long) {
			return !bowlingRuleset.wouldExceedMaxRolls(((int) value) - 1);
		}
		return false;
	}
}
