package eu.nerdfactor.bowling.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = MaxPossibleScoreValidator.class)
public @interface MaxPossibleScore {

	String message() default "{BowlingGame.MaxPossibleScore.Exceeded}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}