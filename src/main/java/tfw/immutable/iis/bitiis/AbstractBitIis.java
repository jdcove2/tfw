package tfw.immutable.iis.bitiis;

import java.io.IOException;
import tfw.check.Argument;

/**
 * This class is an optional abstract class that implements BitIis and provides
 * some common parameter validation.
 */
public abstract class AbstractBitIis implements BitIis {
    /**
     * This method replaces the actual read method and is called after parameter validation.
     *
     * @param array that will hold the bits.
     * @param offset into the array to put the first bit.
     * @param length of bits to put into the array.
     * @return the number of bits put into the array.
     * @throws IOException if something happens while trying to read the bits.
     */
    protected abstract int readImpl(long[] array, int offset, int length) throws IOException;

    /**
     * This method replaces the actual skip method and is called after parameter validation.
     *
     * @param n the number of bits to skip.
     * @return the number of bits skipped.
     * @throws IOException if something happens while trying to skip the bits.
     */
    protected abstract long skipImpl(long n) throws IOException;

    @Override
    public final int read(final long[] array, final int offset, final int length) throws IOException {
        Argument.assertNotNull(array, "array");
        Argument.assertNotLessThan(offset, 0, "offset");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(length, array.length - offset, "length", "array.length - offset");

        if (length == 0) {
            return 0;
        }

        return readImpl(array, offset, length);
    }

    @Override
    public final long skip(final long n) throws IOException {
        if (n < 0) {
            return 0;
        }

        return skipImpl(n);
    }
}