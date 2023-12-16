package eu.nerdfactor.bowling.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

/**
 * Provide special Jackson configuration for HAL serialization.
 *
 * @see <a href="https://github.com/spring-projects/spring-hateoas/issues/1345#issuecomment-669985346">source</a>
 */
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class JacksonHalConfiguration extends AbstractJackson2HttpMessageConverter {
	public JacksonHalConfiguration(ObjectMapper objectMapper) {


		super(objectMapper, MediaTypes.HAL_JSON);
		Jackson2HalModule module = new Jackson2HalModule();
		objectMapper.registerModule(module);
		objectMapper.setHandlerInstantiator(
				new Jackson2HalModule.HalHandlerInstantiator(new AnnotationLinkRelationProvider(), CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));
	}

	@Override
	protected boolean supports(@NotNull Class<?> cls) {
		return RepresentationModel.class.isAssignableFrom(cls);
	}
}
