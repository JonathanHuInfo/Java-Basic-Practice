package org.jonathan.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Map 配置抽象
 */
public abstract class MapBaseConfigSource implements ConfigSource {

    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private final String name;

    private final int ordinal;

    private final Map<String, String> properties;

    public MapBaseConfigSource(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
        this.properties = getProperties();
    }

    protected abstract void parseConfigData(Map configData);

    @Override
    public Map<String, String> getProperties() {
        Map<String, String> configData = new HashMap<>();
        try {
            parseConfigData(configData);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return Collections.unmodifiableMap(configData);
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }
}
