package com.stoloto.logs.config.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamsItem {

    @JsonProperty("stream")
    private Stream stream;
    @JsonProperty("values")
//    private Map<Long, String> values;
    private List<Object> values;

}