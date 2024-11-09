package tfw.immutable.ila.longila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Array;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class LongIlaInterleaveTest {
    @Test
    void testArguments() {
        final LongIla ila1 = LongIlaFromArray.create(new long[10]);
        final LongIla ila2 = LongIlaFromArray.create(new long[20]);
        final LongIla[] ilas1 = (LongIla[]) Array.newInstance(LongIla.class, 0);
        final LongIla[] ilas2 = (LongIla[]) Array.newInstance(LongIla.class, 2);
        final LongIla[] ilas3 = (LongIla[]) Array.newInstance(LongIla.class, 2);
        final LongIla[] ilas4 = (LongIla[]) Array.newInstance(LongIla.class, 2);
        final LongIla[] ilas5 = (LongIla[]) Array.newInstance(LongIla.class, 2);
        final LongIla[] ilas6 = (LongIla[]) Array.newInstance(LongIla.class, 2);
        final long[] buffer = new long[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> LongIlaInterleave.create(ilas6, buffer));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final long[][] target = new long[jj][length];
            final long[] array = new long[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextLong();
            }
            LongIla[] ilas = (LongIla[]) Array.newInstance(LongIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = LongIlaFromArray.create(target[ii]);
            }
            LongIla targetIla = LongIlaFromArray.create(array);
            LongIla actualIla = LongIlaInterleave.create(ilas, new long[1000]);

            LongIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
