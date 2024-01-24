package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaMultiplyTest {
    @Test
    void testArguments() {
        final LongIla ila1 = LongIlaFromArray.create(new long[10]);
        final LongIla ila2 = LongIlaFromArray.create(new long[20]);

        assertThrows(IllegalArgumentException.class, () -> LongIlaMultiply.create(null, ila1, 10));
        assertThrows(IllegalArgumentException.class, () -> LongIlaMultiply.create(ila1, null, 10));
        assertThrows(IllegalArgumentException.class, () -> LongIlaMultiply.create(ila1, ila1, 0));
        assertThrows(IllegalArgumentException.class, () -> LongIlaMultiply.create(ila1, ila2, 10));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] leftArray = new long[length];
        final long[] rightArray = new long[length];
        final long[] array = new long[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextLong();
            rightArray[ii] = random.nextLong();
            array[ii] = leftArray[ii] * rightArray[ii];
        }
        LongIla leftIla = LongIlaFromArray.create(leftArray);
        LongIla rightIla = LongIlaFromArray.create(rightArray);
        LongIla targetIla = LongIlaFromArray.create(array);
        LongIla actualIla = LongIlaMultiply.create(leftIla, rightIla, 100);

        LongIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
