package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Array;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaInterleaveTest {
    @Test
    @SuppressWarnings("unchecked")
    void testArguments() {
        final ObjectIla<Object> ila1 = ObjectIlaFromArray.create(new Object[10]);
        final ObjectIla<Object> ila2 = ObjectIlaFromArray.create(new Object[20]);
        final ObjectIla<Object>[] ilas1 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 0);
        final ObjectIla<Object>[] ilas2 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas3 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas4 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas5 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final ObjectIla<Object>[] ilas6 = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, 2);
        final Object[] buffer = new Object[10];

        ilas3[0] = null;
        ilas3[1] = ila1;
        ilas4[0] = ila1;
        ilas4[1] = null;
        ilas5[0] = ila1;
        ilas5[1] = ila1;
        ilas6[0] = ila1;
        ilas6[1] = ila2;

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(ilas5, null));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(ilas1, buffer));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(ilas2, buffer));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(ilas3, buffer));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(ilas4, buffer));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInterleave.create(ilas6, buffer));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final Object[][] target = new Object[jj][length];
            final Object[] array = new Object[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = new Object();
            }
            ObjectIla<Object>[] ilas = (ObjectIla<Object>[]) Array.newInstance(ObjectIla.class, jj);
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = ObjectIlaFromArray.create(target[ii]);
            }
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> actualIla = ObjectIlaInterleave.create(ilas, new Object[1000]);

            ObjectIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
