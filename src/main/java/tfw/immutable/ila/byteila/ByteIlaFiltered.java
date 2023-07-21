package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaFiltered {
    private ByteIlaFiltered() {
        // non-instantiable class
    }

    public interface ByteFilter {
        boolean matches(byte value);
    }

    public static ByteIla create(ByteIla ila, ByteFilter filter, byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyByteIla(ila, filter, buffer);
    }

    private static class MyByteIla implements ByteIla {
        private final ByteIla ila;
        private final ByteFilter filter;
        private final byte[] buffer;

        private long length = -1;

        private MyByteIla(ByteIla ila, ByteFilter filter, byte[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(byte[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            ByteIlaIterator oii = new ByteIlaIterator(ByteIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                byte node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                ByteIlaIterator oii = new ByteIlaIterator(ila, buffer.clone());

                try {
                    while (oii.hasNext()) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                } catch (DataInvalidException die) {
                    length = 0;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
