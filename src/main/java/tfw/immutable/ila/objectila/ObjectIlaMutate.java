package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

public final class ObjectIlaMutate {
    private ObjectIlaMutate() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, long index, T value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new ObjectIlaImpl<>(ila, index, value);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final long index;
        private final T value;

        private ObjectIlaImpl(ObjectIla<T> ila, long index, T value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(T[] array, int offset, long start, int length) throws IOException {
            final long startPlusLength = start + length;

            if (index < start || index >= startPlusLength) {
                ila.get(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                if (index > start) {
                    ila.get(array, offset, start, indexMinusStart);
                }
                array[offset + indexMinusStart] = value;
                if (index <= startPlusLength) {
                    ila.get(array, offset + indexMinusStart + 1, index + 1, length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
