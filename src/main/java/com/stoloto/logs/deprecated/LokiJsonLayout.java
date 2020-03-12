package com.stoloto.logs.deprecated;


import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.jackson.JsonConstants;
import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.core.util.StringBuilderWriter;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.logging.log4j.core.jackson.JsonConstants.ELT_INSTANT;
import static org.apache.logging.log4j.core.jackson.JsonConstants.ELT_NANO_TIME;

/**
 * Borrowed heavily from org.apache.logging.log4j.core.layout.JsonLayout
 * for example see this https://stackoverflow.com/questions/39590365/print-stacktrace-with-log4j2-in-json-with-jsonlayout-in-a-single-line
 */
@Plugin(name = "LokiJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class LokiJsonLayout extends AbstractStringLayout {
    private static final String CONTENT_TYPE = "application/json; charset=" + StandardCharsets.UTF_8.displayName();

    private final ObjectMapper objectMapper;
    protected static Map<String, String> additionalFields = new HashMap<>();

    /**
     * @param includeLocationInfo       информация о логгере, где он был вызван
     * @param encodeThreadContextAsList
     * @param includeStacktrace         - добавлять к ошибке стек трейс
     * @param stacktraceAsString        - сериализоваться стек трейс ввиде обычной строки (иначе ввиде json)
     * @param objectMessageAsJsonObject - пишет сообщения в json формате
     * @param keyValuePairs             - Дополнительные атрибуты, ключ - значение
     * @return возращает этот лайаут для логгирования ввиде json
     */

    @PluginFactory
    public static LokiJsonLayout createLayout(
            @PluginAttribute(value = "includeLocationInfo", defaultBoolean = true) final boolean includeLocationInfo,
            @PluginAttribute(value = "includeStacktrace", defaultBoolean = true) final boolean includeStacktrace,
            @PluginAttribute(value = "objectMessageAsJsonObject", defaultBoolean = true) final boolean objectMessageAsJsonObject,
            @PluginAttribute(value = "stacktraceAsString") final boolean stacktraceAsString,
            @PluginAttribute(value = "encodeThreadContextAsList") final boolean encodeThreadContextAsList,
            @PluginElement("KeyValuePairs") final KeyValuePair[] keyValuePairs
    ) {

        final var filters = new SimpleFilterProvider();
        final Set<String> except = new HashSet<>();

        if (!includeLocationInfo) {
            except.add(JsonConstants.ELT_SOURCE);
        }

        except.add("loggerFqcn");
        except.add("endOfBatch");
        except.add("threadId");
        except.add("threadPriority");
        except.add("contextMap");
        except.add(ELT_INSTANT);
        except.add(ELT_NANO_TIME);

        filters.addFilter(Log4jLogEvent.class.getName(), SimpleBeanPropertyFilter.serializeAllExcept(except));

        var log4jJsonObjectMapper = new Log4jJsonObjectMapper(
                encodeThreadContextAsList,
                includeStacktrace,
                stacktraceAsString,
                objectMessageAsJsonObject
        )
                .setDefaultPrettyPrinter(new MinimalPrettyPrinter())
                .setFilterProvider(filters);
        return new LokiJsonLayout(log4jJsonObjectMapper, keyValuePairs);
    }

    LokiJsonLayout(ObjectMapper objectMapper, KeyValuePair[] additionalFields) {
        super(StandardCharsets.UTF_8, null, null);
        this.objectMapper = objectMapper;
        for (var keyValue : additionalFields) {
            LokiJsonLayout.additionalFields.put(keyValue.getKey(), keyValue.getValue());
        }
    }

    /**
     * Formats a {@link org.apache.logging.log4j.core.LogEvent}.
     *
     * @param event The LogEvent.
     * @return The JSON representation of the LogEvent.
     */
    @Override
    public String toSerializable(final LogEvent event) {
        final StringBuilderWriter writer = new StringBuilderWriter();
        try {
            objectMapper.writeValue(writer, wrap(event));
            writer.write('\n');
            return writer.toString();
        } catch (final IOException e) {
            LOGGER.error(e);
            return Strings.EMPTY;
        }
    }

    // Overridden in tests
    LogEvent wrap(LogEvent event) {
        return new LokiLogEvent(event);
    }

    @Override
    public String getContentType() {
        return CONTENT_TYPE;
    }
}
//JsonAppend.Attr
