package tfw.immutable.iis.doubleiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

class DoubleIisFromDoubleIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> DoubleIisFromDoubleIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final double[] expectedArray = new double[12];
        final DoubleIla ila = DoubleIlaFromArray.create(expectedArray);

        try (DoubleIis iis = DoubleIisFromDoubleIla.create(ila)) {
            final double[] actualArray = new double[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new double[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final double[] array = new double[12];
        final double[] expectedArray = new double[array.length];

        Arrays.fill(array, 1.0);
        Arrays.fill(expectedArray, 0, expectedArray.length, 0.0);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, 1.0);

        final DoubleIla ila = DoubleIlaFromArray.create(array);

        try (DoubleIis iis = DoubleIisFromDoubleIla.create(ila)) {
            final double[] actualArray = new double[expectedArray.length];

            Arrays.fill(actualArray, 0.0);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final double[] expectedArray = new double[12];
        final DoubleIla ila = DoubleIlaFromArray.create(expectedArray);

        try (DoubleIis iis = DoubleIisFromDoubleIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
