package org.jonathan.microprofile.config.converter;

public class LongConverter extends DefaultAbstractConverter<Long> {

    @Override
    protected Long doConvert(String value) {
        return Long.valueOf(value);
    }
}
