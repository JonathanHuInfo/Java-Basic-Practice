package org.jonathan.configuration.microprofile.config.converter;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BooleanConverter extends AbstractConverter<Boolean> {

    private final Set<String> values = new HashSet<>(Arrays.asList("true", "1", "YES", "Y", "ON"));

    @Override
    protected Boolean doConvert(String value) {
        return values.contains(value);
    }
}
