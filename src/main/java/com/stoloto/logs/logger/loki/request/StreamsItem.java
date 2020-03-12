package com.stoloto.logs.logger.loki.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stoloto.logs.utils.LokiCustomSerializer;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamsItem {

    @JsonProperty("stream")
    private Stream stream;
    @JsonProperty("values")
	@JsonSerialize(using = LokiCustomSerializer.class)
	private Map<Long, String> values;

}
