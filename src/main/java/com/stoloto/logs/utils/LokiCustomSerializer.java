package com.stoloto.logs.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Map;

public class LokiCustomSerializer extends JsonSerializer<Map<Long, String>> {

	@Override
	public void serialize(Map<Long, String> map, JsonGenerator generator,
						  SerializerProvider serializerProvider) throws IOException {

		generator.writeStartArray();
		for (var entry : map.entrySet()) {
			generator.writeStartArray();

			generator.writeString(String.valueOf(entry.getKey() * 1000000));
			generator.writeString(String.valueOf(entry.getValue()));

			generator.writeEndArray();
		}
		generator.writeEndArray();

	}
}
