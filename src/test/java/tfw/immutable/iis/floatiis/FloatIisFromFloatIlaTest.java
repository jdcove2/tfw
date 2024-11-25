package tfw.immutable.iis.floatiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

class FloatIisFromFloatIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> FloatIisFromFloatIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final float[] expectedArray = new float[12];
        final FloatIla ila = FloatIlaFromArray.create(expectedArray);

        try (FloatIis iis = FloatIisFromFloatIla.create(ila)) {
            final float[] actualArray = new float[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new float[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final float[] array = new float[12];
        final float[] expectedArray = new float[array.length];

        Arrays.fill(array, 1.0f);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0.0f);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1.0f);

        final FloatIla ila = FloatIlaFromArray.create(array);

        try (FloatIis iis = FloatIisFromFloatIla.create(ila)) {
            final float[] actualArray = new float[expectedArray.length];

            Arrays.fill(actualArray, 0.0f);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final float[] expectedArray = new float[12];
        final FloatIla ila = FloatIlaFromArray.create(expectedArray);

        try (FloatIis iis = FloatIisFromFloatIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
