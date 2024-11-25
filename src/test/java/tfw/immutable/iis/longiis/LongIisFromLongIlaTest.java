package tfw.immutable.iis.longiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

class LongIisFromLongIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> LongIisFromLongIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final long[] expectedArray = new long[12];
        final LongIla ila = LongIlaFromArray.create(expectedArray);

        try (LongIis iis = LongIisFromLongIla.create(ila)) {
            final long[] actualArray = new long[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new long[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final long[] array = new long[12];
        final long[] expectedArray = new long[array.length];

        Arrays.fill(array, 1L);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0L);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1L);

        final LongIla ila = LongIlaFromArray.create(array);

        try (LongIis iis = LongIisFromLongIla.create(ila)) {
            final long[] actualArray = new long[expectedArray.length];

            Arrays.fill(actualArray, 0L);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final long[] expectedArray = new long[12];
        final LongIla ila = LongIlaFromArray.create(expectedArray);

        try (LongIis iis = LongIisFromLongIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
