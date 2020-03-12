package com.stoloto.logs.config.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UnWrapper {
    private Map<Long, String> ignore;

    @JsonAnyGetter
    public Map<Long, String> getIgnore() {
        return ignore;
    }

    public void setIgnore(Map<Long, String> ignore) {
        this.ignore = ignore;
    }
}
