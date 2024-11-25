package tfw.immutable.iis.byteiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

class ByteIisFromByteIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ByteIisFromByteIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final byte[] expectedArray = new byte[12];
        final ByteIla ila = ByteIlaFromArray.create(expectedArray);

        try (ByteIis iis = ByteIisFromByteIla.create(ila)) {
            final byte[] actualArray = new byte[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new byte[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final byte[] array = new byte[12];
        final byte[] expectedArray = new byte[array.length];

        Arrays.fill(array, (byte) 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, (byte) 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, (byte) 1);

        final ByteIla ila = ByteIlaFromArray.create(array);

        try (ByteIis iis = ByteIisFromByteIla.create(ila)) {
            final byte[] actualArray = new byte[expectedArray.length];

            Arrays.fill(actualArray, (byte) 0);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final byte[] expectedArray = new byte[12];
        final ByteIla ila = ByteIlaFromArray.create(expectedArray);

        try (ByteIis iis = ByteIisFromByteIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
