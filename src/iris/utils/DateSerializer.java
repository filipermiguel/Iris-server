package iris.utils;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public final class DateSerializer extends JsonSerializer<Date> {

	// private final DateTimeFormatter formatter =
	// DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		// String dateString = date.format(formatter);
		generator.writeString("2015-10-01");
	}
}