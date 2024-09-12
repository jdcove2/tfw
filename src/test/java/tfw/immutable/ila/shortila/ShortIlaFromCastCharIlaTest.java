package tfw.immutable.ila.shortila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

class ShortIlaFromCastCharIlaTest {
    @Test
    void testArguments() {
        final CharIla ila = CharIlaFromArray.create(new char[10]);

        assertThrows(IllegalArgumentException.class, () -> ShortIlaFromCastCharIla.create(null, 1));
        assertThrows(IllegalArgumentException.class, () -> ShortIlaFromCastCharIla.create(ila, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final short[] target = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (short) array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaFromCastCharIla.create(ila, 100);

        ShortIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
