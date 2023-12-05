package eu.nerdfactor.bowling.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom converter for knockedOverPins Integer List into json string.
 * This may be necessary in order to store the list in the database but not have a super
 * complex new object that stores single integer values in a separate table and keeps the
 * order in which the pins are stored in the list.
 */
@Converter
@RequiredArgsConstructor
public class KnockedOverPinsConvert implements AttributeConverter<List<Integer>, String> {

	/**
	 * A {@link ObjectMapper} in order to map the json strings.
	 */
	private final ObjectMapper jsonMapper;

	/**
	 * Converts a list into a json string.
	 *
	 * @param attribute the entity attribute value to be converted
	 * @return The list as json.
	 */
	@Override
	public String convertToDatabaseColumn(List<Integer> attribute) {
		try {
			return this.jsonMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			return "[]";
		}
	}

	/**
	 * Converts the json string to a list.
	 *
	 * @param string the data from the database column to be
	 *               converted
	 * @return The json as list.
	 */
	@Override
	public List<Integer> convertToEntityAttribute(String string) {
		try {
			return string != null && string.startsWith("[") ? this.jsonMapper.readValue(string, new TypeReference<>() {
			}) : new ArrayList<>();
		} catch (JsonProcessingException e) {
			return new ArrayList<>();
		}
	}
}
