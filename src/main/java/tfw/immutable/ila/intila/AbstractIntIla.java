package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIla;

public abstract class AbstractIntIla extends AbstractIla implements IntIla {
    protected abstract void getImpl(final int[] array, int offset, long start, int length) throws IOException;

    protected AbstractIntIla() {}

    @Override
    public final void get(final int[] array, final int offset, final long start, int length) throws IOException {
        if (length == 0) {
            return;
        }

        Argument.assertNotNull(array, "array");
        boundsCheck(array.length, offset, start, length);
        getImpl(array, offset, start, length);
    }
}
// AUTO GENERATED FROM TEMPLATE
