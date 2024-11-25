package tfw.immutable.iis.shortiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

class ShortIisFromShortIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ShortIisFromShortIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final short[] expectedArray = new short[12];
        final ShortIla ila = ShortIlaFromArray.create(expectedArray);

        try (ShortIis iis = ShortIisFromShortIla.create(ila)) {
            final short[] actualArray = new short[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new short[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final short[] array = new short[12];
        final short[] expectedArray = new short[array.length];

        Arrays.fill(array, (short) 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, (short) 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, (short) 1);

        final ShortIla ila = ShortIlaFromArray.create(array);

        try (ShortIis iis = ShortIisFromShortIla.create(ila)) {
            final short[] actualArray = new short[expectedArray.length];

            Arrays.fill(actualArray, (short) 0);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final short[] expectedArray = new short[12];
        final ShortIla ila = ShortIlaFromArray.create(expectedArray);

        try (ShortIis iis = ShortIisFromShortIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
