package org.jonathan.microprofile.config.converter;

public class ShortConverter extends DefaultAbstractConverter<Short> {

    @Override
    protected Short doConvert(String value) {
        return Short.valueOf(value);
    }
}
