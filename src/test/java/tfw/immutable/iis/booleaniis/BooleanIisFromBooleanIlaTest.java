package tfw.immutable.iis.booleaniis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

class BooleanIisFromBooleanIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> BooleanIisFromBooleanIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final boolean[] expectedArray = new boolean[12];
        final BooleanIla ila = BooleanIlaFromArray.create(expectedArray);

        try (BooleanIis iis = BooleanIisFromBooleanIla.create(ila)) {
            final boolean[] actualArray = new boolean[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new boolean[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final boolean[] array = new boolean[12];
        final boolean[] expectedArray = new boolean[array.length];

        Arrays.fill(array, true);
        Arrays.fill(expectedArray, 0, expectedArray.length, false);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, true);

        final BooleanIla ila = BooleanIlaFromArray.create(array);

        try (BooleanIis iis = BooleanIisFromBooleanIla.create(ila)) {
            final boolean[] actualArray = new boolean[expectedArray.length];

            Arrays.fill(actualArray, false);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final boolean[] expectedArray = new boolean[12];
        final BooleanIla ila = BooleanIlaFromArray.create(expectedArray);

        try (BooleanIis iis = BooleanIisFromBooleanIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
