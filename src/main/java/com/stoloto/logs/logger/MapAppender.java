package com.stoloto.logs.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoloto.logs.model.LokiDelegateLogEvent;
import com.stoloto.logs.model.LokiRequest;
import com.stoloto.logs.model.Stream;
import com.stoloto.logs.model.StreamsItem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Plugin(
        name = "MapAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class MapAppender extends AbstractAppender {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();


    protected MapAppender(String name,
                          Filter filter,
                          Layout<? extends Serializable> layout,
                          boolean ignoreExceptions,
                          Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }


    @PluginFactory
    public static MapAppender createAppender(@PluginAttribute("name") String name,
                                             @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                             @PluginElement("Layout") Layout<? extends Serializable> layout,
                                             @PluginElement("properties") Property[] properties,
                                             @PluginElement("Filter") Filter filter) {
        return new MapAppender(name, filter, layout, ignoreExceptions, properties);
    }


    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void append(LogEvent event) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var lokiJsonObject = LokiDelegateLogEvent.ofLogEvent(event);
        var lokiStringValue = objectMapper.writeValueAsString(lokiJsonObject);

        long nanoTime = event.getInstant().getEpochMillisecond();

        var streamsItems = StreamsItem.builder()
                .stream(
					Stream.of(
                                "test",
                                nanoTime
                        )
                )
			.values(Map.of(nanoTime, lokiStringValue))
                .build();

        var result = LokiRequest.builder()
                .streams(List.of(streamsItems))
                .build();


        var logEventHttpEntity = new HttpEntity<>(result, headers);

		//        var s = REST_TEMPLATE.postForObject("http://localhost:8080/do", logEventHttpEntity, String.class);
		var s = REST_TEMPLATE
			.postForObject("http://10.229.8.23:3100/loki/api/v1/push", logEventHttpEntity, String.class);

    }

}
