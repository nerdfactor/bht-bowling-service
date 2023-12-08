package eu.nerdfactor.bowling.config;

import eu.nerdfactor.bowling.service.BowlingRuleset;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to get Hibernate to use Spring dependency injection
 * on Entities (which are not managed by Spring).
 */
@Configuration
public class ValidationConfig {


	/**
	 * Add Springs validation factory to Hibernate, so that it uses a factory
	 * that Spring can inject bean into. This allows the custom validators to
	 * get a {@link BowlingRuleset} bean.
	 *
	 * @param validator Some {@link Validator} from Spring.
	 * @return Customized Spring Properties.
	 * @see <a href="https://stackoverflow.com/a/56557189"/>
	 */
	@Bean
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final Validator validator) {
		return hibernateProperties -> hibernateProperties.put("javax.persistence.validation.factory", validator);
	}
}
