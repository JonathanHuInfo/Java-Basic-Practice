package org.jonathan.microprofile.config.converter;

public class ByteConverter extends DefaultAbstractConverter<Byte> {

    @Override
    protected Byte doConvert(String value) {
        return Byte.valueOf(value);
    }
}
