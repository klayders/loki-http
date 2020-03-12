package com.stoloto.logs.config;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.time.Instant;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
class LokiEvent implements LogEvent {
    private  LogEvent delegate;


    @JsonAnyGetter
    public Map<String, String> getAdditionalFields() {
        return LokiJsonLayout.additionalFields;
    }

    @Override
    public LogEvent toImmutable() {
        return delegate;
    }


    @Override
    public ReadOnlyStringMap getContextData() {
        return delegate.getContextData();
    }


    @Override
    public ThreadContext.ContextStack getContextStack() {
        return delegate.getContextStack();
    }


    @Override
    public String getLoggerFqcn() {
        return delegate.getLoggerFqcn();
    }


    @Override
    public Level getLevel() {
        return delegate.getLevel();
    }


    @Override
    public String getLoggerName() {
        return delegate.getLoggerName();
    }


    @Override
    public Marker getMarker() {
        return delegate.getMarker();
    }


    @Override
    public Message getMessage() {
        return delegate.getMessage();
    }

    @Override
    public Map<String, String> getContextMap() {
        return delegate.getContextMap();
    }


    @Override
    public long getTimeMillis() {
        return delegate.getTimeMillis();
    }

    @JsonProperty("ts")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SSS")
    public Date getTime() {
        return new Date(delegate.getTimeMillis());
    }

    @Override
    public Instant getInstant() {
        return delegate.getInstant();
    }

    @Override
    public StackTraceElement getSource() {
        return delegate.getSource();
    }

    @Override
    public String getThreadName() {
        return delegate.getThreadName();
    }

    @Override
    public long getThreadId() {
        return 0;
    }

    @Override
    public void setIncludeLocation(boolean locationRequired) {
        delegate.setIncludeLocation(locationRequired);
    }

    @Override
    public int getThreadPriority() {
        return 0;
    }

    @Override
    public void setEndOfBatch(boolean endOfBatch) {
        delegate.setEndOfBatch(endOfBatch);
    }

    @Override
    public Throwable getThrown() {
        return delegate.getThrown();
    }

    @Override
    public boolean isIncludeLocation() {
        return delegate.isIncludeLocation();
    }

    @Override
    public ThrowableProxy getThrownProxy() {
        return delegate.getThrownProxy();
    }


    @Override
    public boolean isEndOfBatch() {
        return delegate.isEndOfBatch();
    }

    @Override
    public long getNanoTime() {
        return delegate.getNanoTime();
    }
}
