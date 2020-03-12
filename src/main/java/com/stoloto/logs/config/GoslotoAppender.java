package com.stoloto.logs.config;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.HttpAppender;
import org.apache.logging.log4j.core.appender.HttpManager;
import org.apache.logging.log4j.core.appender.HttpURLConnectionManager;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;

import java.io.Serializable;
import java.net.URL;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Plugin(
        name = "GoslotoAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class GoslotoAppender extends AbstractAppender {

    private ConcurrentMap<String, LogEvent> eventMap = new ConcurrentHashMap<>();

    protected GoslotoAppender(String name, Filter filter) {
        super(name, filter, null);
    }

    @PluginFactory
    public static GoslotoAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter) {
        return new GoslotoAppender(name, filter);
    }

    @Override
    public void append(LogEvent event) {
        eventMap.put(Instant.now().toString(), event);
    }
}