package tfw.immutable.iis.booleaniis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
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
