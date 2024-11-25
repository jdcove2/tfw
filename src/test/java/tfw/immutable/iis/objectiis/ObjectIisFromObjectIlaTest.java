package tfw.immutable.iis.objectiis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

class ObjectIisFromObjectIlaTest {
    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> ObjectIisFromObjectIla.create(null));
    }

    @Test
    void testRead() throws IOException {
        final Object[] expectedArray = new Object[12];
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(expectedArray);

        try (ObjectIis<Object> iis = ObjectIisFromObjectIla.create(ila)) {
            final Object[] actualArray = new Object[expectedArray.length];

            for (int i = 0; i < actualArray.length; i += actualArray.length / 4) {
                assertEquals(actualArray.length / 4, iis.read(actualArray, i, actualArray.length / 4));
            }

            assertEquals(-1, iis.read(new Object[1], 0, 1));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testRead2() throws IOException {
        final Object[] array = new Object[12];
        final Object[] expectedArray = new Object[array.length];

        Arrays.fill(array, String.class);
        Arrays.fill(expectedArray, 0, expectedArray.length, Object.class);
        Arrays.fill(expectedArray, 0, expectedArray.length - 1, String.class);

        final ObjectIla<Object> ila = ObjectIlaFromArray.create(array);

        try (ObjectIis<Object> iis = ObjectIisFromObjectIla.create(ila)) {
            final Object[] actualArray = new Object[expectedArray.length];

            Arrays.fill(actualArray, Object.class);

            assertEquals(1, iis.skip(1));
            assertEquals(expectedArray.length - 1, iis.read(actualArray, 0, actualArray.length));
            assertArrayEquals(expectedArray, actualArray);
        }
    }

    @Test
    void testSkip() throws IOException {
        final Object[] expectedArray = new Object[12];
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(expectedArray);

        try (ObjectIis<Object> iis = ObjectIisFromObjectIla.create(ila)) {
            for (int i = 0; i < 4; i++) {
                assertEquals(expectedArray.length / 4, iis.skip(expectedArray.length / 4));
            }

            assertEquals(-1, iis.skip(1));
            assertEquals(-1, iis.skip(1));
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
