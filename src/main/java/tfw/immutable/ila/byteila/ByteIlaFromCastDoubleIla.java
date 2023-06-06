package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

/**
 *
 * @immutables.types=numericnotdouble
 */
public final class ByteIlaFromCastDoubleIla {
    private ByteIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static ByteIla create(DoubleIla doubleIla) {
        return create(doubleIla, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(DoubleIla doubleIla, int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(doubleIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla implements ImmutableProxy {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        MyByteIla(DoubleIla doubleIla, int bufferSize) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator fi = new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (byte) fi.next();
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ByteIlaFromCastDoubleIla");
            map.put("doubleIla", getImmutableInfo(doubleIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
