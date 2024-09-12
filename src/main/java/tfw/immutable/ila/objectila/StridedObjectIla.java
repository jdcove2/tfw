package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.immutable.ila.ImmutableLongArray;

public interface StridedObjectIla<T> extends ImmutableLongArray {
    void get(final T[] array, final int offset, final int stride, final long start, final int length)
            throws IOException;
}
// AUTO GENERATED FROM TEMPLATE
