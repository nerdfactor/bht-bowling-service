package eu.nerdfactor.bowling.validation;

import eu.nerdfactor.bowling.service.BowlingRuleset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaxPossibleScoreValidator implements ConstraintValidator<MaxPossibleScore, Object> {

	@Autowired
	private BowlingRuleset bowlingRuleset;

	@Override
	public void initialize(MaxPossibleScore constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (this.bowlingRuleset == null) {
			return false;
		}
		if (value instanceof Integer || value instanceof Long) {
			return ((int) value) <= this.bowlingRuleset.amountOfMaxScore();
		}
		return false;
	}
}
