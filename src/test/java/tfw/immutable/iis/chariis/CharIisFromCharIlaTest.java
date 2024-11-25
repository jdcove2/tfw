package tfw.immutable.iis.chariis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

class CharIisFromCharIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> CharIisFromCharIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final char[] expectedArray = new char[12];
        final CharIla ila = CharIlaFromArray.create(expectedArray);

        try (CharIis iis = CharIisFromCharIla.create(ila)) {
            final char[] actualArray = new char[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new char[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final char[] array = new char[12];
        final char[] expectedArray = new char[array.length];

        Arrays.fill(array, (char) 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, (char) 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, (char) 1);

        final CharIla ila = CharIlaFromArray.create(array);

        try (CharIis iis = CharIisFromCharIla.create(ila)) {
            final char[] actualArray = new char[expectedArray.length];

            Arrays.fill(actualArray, (char) 0);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final char[] expectedArray = new char[12];
        final CharIla ila = CharIlaFromArray.create(expectedArray);

        try (CharIis iis = CharIisFromCharIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
