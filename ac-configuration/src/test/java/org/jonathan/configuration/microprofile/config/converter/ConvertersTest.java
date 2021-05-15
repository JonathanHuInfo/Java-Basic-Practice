package org.jonathan.configuration.microprofile.config.converter;


import org.jonathan.configuration.microprofile.config.converter.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Converters} Test
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvertersTest {

    private Converters converters;


    @BeforeAll
    void init() {
        converters = new Converters();
    }

    @Test
    public void testResolveConvertedType() {
        assertEquals(Byte.class, converters.resolveConvertedType(new ByteConverter()));
        assertEquals(Short.class, converters.resolveConvertedType(new ShortConverter()));
        assertEquals(Integer.class, converters.resolveConvertedType(new IntegerConverter()));
        assertEquals(Long.class, converters.resolveConvertedType(new LongConverter()));
        assertEquals(Float.class, converters.resolveConvertedType(new FloatConverter()));
        assertEquals(Double.class, converters.resolveConvertedType(new DoubleConverter()));
        assertEquals(String.class, converters.resolveConvertedType(new StringConverter()));
    }
}
