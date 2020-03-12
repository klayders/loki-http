package com.stoloto.logs.config.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stream {

    @JsonProperty("appName")
    private String appName;

    @JsonProperty("ts")
    private long ts;

    public static Stream of(String appName, long date) {
        return Stream.builder()
                .appName(appName)
                .ts(date)
                .build();
    }

}