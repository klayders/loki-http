package com.stoloto.logs.logger.loki.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LokiRequest {

    @JsonProperty("streams")
    private List<StreamsItem> streams;

}
