package tfw.immutable.ila.intila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class IntIlaDivideTest {
    @Test
    void testArguments() {
        final IntIla ila1 = IntIlaFromArray.create(new int[10]);
        final IntIla ila2 = IntIlaFromArray.create(new int[20]);

        assertThrows(IllegalArgumentException.class, () -> IntIlaDivide.create(null, ila2, 1));
        assertThrows(IllegalArgumentException.class, () -> IntIlaDivide.create(ila1, null, 1));
        assertThrows(IllegalArgumentException.class, () -> IntIlaDivide.create(ila1, ila2, 1));
        assertThrows(IllegalArgumentException.class, () -> IntIlaDivide.create(ila1, ila1, 0));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] leftArray = new int[length];
        final int[] rightArray = new int[length];
        final int[] array = new int[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextInt();
            rightArray[ii] = random.nextInt();
            array[ii] = leftArray[ii] / rightArray[ii];
        }
        IntIla leftIla = IntIlaFromArray.create(leftArray);
        IntIla rightIla = IntIlaFromArray.create(rightArray);
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaDivide.create(leftIla, rightIla, 100);
        final int epsilon = 0;
        IntIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
