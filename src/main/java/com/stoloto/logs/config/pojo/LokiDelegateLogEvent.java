package com.stoloto.logs.config.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LokiDelegateLogEvent {

    @JsonProperty("ts")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SSS")
    private Date date;
    private Level level;
    private String loggerName;
    private Message message;
    private StackTraceElement source;
    private String threadName;
    private ThrowableProxy thrown;


    public static LokiDelegateLogEvent ofLogEvent(LogEvent logEvent) {
        return LokiDelegateLogEvent.builder()
                .date(new Date(logEvent.getInstant().getEpochMillisecond()))
                .level(logEvent.getLevel())
                .loggerName(logEvent.getLoggerName())
                .message(logEvent.getMessage())
                .source(logEvent.getSource())
                .threadName(logEvent.getThreadName())
                .thrown(logEvent.getThrownProxy())
                .build();
    }
}
