package tfw.immutable.ila.booleanila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class StridedBooleanIla {
    private final BooleanIla ila;
    private final boolean[] buffer;

    public StridedBooleanIla(final BooleanIla ila, final boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        this.ila = ila;
        this.buffer = buffer;
    }

    public long length() {
        return ila.length();
    }

    public void toArray(final boolean[] array, final int offset, final int stride, final long start, final int length)
            throws DataInvalidException {
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotGreaterThanOrEquals(offset, array.length, "offset", "array.length");
        Argument.assertNotEquals(stride, 0, "stride");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        if (stride < 0) {
            Argument.assertNotLessThan(offset + (length - 1) * stride, 0, "offset+(length-1)*stride");
        } else {
            Argument.assertNotGreaterThan(
                    offset + (length - 1) * stride + 1, array.length, "offset+(length-1)*stride+1", "array.length");
        }
        Argument.assertNotGreaterThan(start + length, ila.length(), "start+length", "Ila.length()");

        final BooleanIla segmentBooleanIla =
                start == 0 && length == ila.length() ? ila : BooleanIlaSegment.create(ila, start, length);
        final BooleanIlaIterator bii = new BooleanIlaIterator(segmentBooleanIla, buffer.clone());

        for (int i = 0, ii = offset; i < length; i++, ii += stride) {
            array[ii] = bii.next();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE