package tfw.immutable.ila.stringila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaFiltered {
    private StringIlaFiltered() {
        // non-instantiable class
    }

    public static interface StringFilter {
        public boolean matches(String value);
    }

    public static StringIla create(StringIla ila, StringFilter filter) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyStringIla(ila, filter);
    }

    private static class MyStringIla implements StringIla, ImmutableLongArray, ImmutableProxy {
        private final StringIla ila;
        private final StringFilter filter;

        private long length = -1;

        private MyStringIla(StringIla ila, StringFilter filter) {
            this.ila = ila;
            this.filter = filter;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final String[] toArray() throws DataInvalidException {
            calculateLength();

            if (length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final String[] toArray(long start, int length) throws DataInvalidException {
            calculateLength();

            String[] result = new String[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(String[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(String[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            StringIlaIterator oii = new StringIlaIterator(StringIlaSegment.create(ila, start));

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                String node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                StringIlaIterator oii = new StringIlaIterator(ila);

                try {
                    for (int i = 0; oii.hasNext(); i++) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                } catch (DataInvalidException die) {
                    length = 0;
                }
            }
        }

        public Map<String, Object> getParameters() {
            calculateLength();

            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "StringIlaFromArray");
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
