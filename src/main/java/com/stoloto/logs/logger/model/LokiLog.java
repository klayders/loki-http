package com.stoloto.logs.logger.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LokiLog {

    @JsonProperty("ts")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SSS")
    private Date date;
    private Level level;
    private String loggerName;
    private Message message;
    private StackTraceElement source;
    private String threadName;
    private ThrowableProxy thrown;


	public static LokiLog ofLogEvent(LogEvent logEvent) {
		return LokiLog.builder()
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
