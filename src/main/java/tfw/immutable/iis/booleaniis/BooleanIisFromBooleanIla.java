package tfw.immutable.iis.booleaniis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.booleanila.BooleanIla;

public final class BooleanIisFromBooleanIla {
    private BooleanIisFromBooleanIla() {}

    public static BooleanIis create(final BooleanIla ila) {
        return new BooleanIisImpl(ila);
    }

    private static class BooleanIisImpl extends AbstractBooleanIis {
        private final BooleanIla ila;

        private long index = 0;

        public BooleanIisImpl(final BooleanIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(boolean[] array, int offset, int length) throws IOException {
            if (index == ila.length()) {
                return -1;
            }

            final int elementsToGet = (int) Math.min(ila.length() - index, length);

            ila.get(array, offset, index, elementsToGet);

            index += elementsToGet;

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            if (index == ila.length()) {
                return -1;
            }

            final long originalIndex = index;

            index = Math.min(ila.length(), index + n);

            return index - originalIndex;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
