package tfw.immutable.iis.objectiis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIisFromObjectIla {
    private ObjectIisFromObjectIla() {}

    public static <T> ObjectIis<T> create(final ObjectIla<T> ila) {
        return new ObjectIisImpl<>(ila);
    }

    private static class ObjectIisImpl<T> extends AbstractObjectIis<T> {
        private final ObjectIla<T> ila;

        private long index = 0;

        public ObjectIisImpl(final ObjectIla<T> ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(T[] array, int offset, int length) throws IOException {
            final int elementsToGet = IisFromIlaUtil.read(ila.length(), index, length);

            if (elementsToGet > -1) {
                ila.get(array, offset, index, elementsToGet);

                index += elementsToGet;
            }

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            final long elementsSkipped = IisFromIlaUtil.skip(ila.length(), index, n);

            if (elementsSkipped > -1) {
                index += elementsSkipped;
            }

            return elementsSkipped;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
