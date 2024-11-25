package tfw.immutable.iis.intiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;

class IntIisFromIntIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> IntIisFromIntIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final int[] expectedArray = new int[12];
        final IntIla ila = IntIlaFromArray.create(expectedArray);

        try (IntIis iis = IntIisFromIntIla.create(ila)) {
            final int[] actualArray = new int[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new int[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final int[] array = new int[12];
        final int[] expectedArray = new int[array.length];

        Arrays.fill(array, 1);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1);

        final IntIla ila = IntIlaFromArray.create(array);

        try (IntIis iis = IntIisFromIntIla.create(ila)) {
            final int[] actualArray = new int[expectedArray.length];

            Arrays.fill(actualArray, 0);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final int[] expectedArray = new int[12];
        final IntIla ila = IntIlaFromArray.create(expectedArray);

        try (IntIis iis = IntIisFromIntIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
