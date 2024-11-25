package tfw.immutable.iis.doubleiis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
import tfw.immutable.ila.doubleila.DoubleIla;

public final class DoubleIisFromDoubleIla {
    private DoubleIisFromDoubleIla() {}

    public static DoubleIis create(final DoubleIla ila) {
        return new DoubleIisImpl(ila);
    }

    private static class DoubleIisImpl extends AbstractDoubleIis {
        private final DoubleIla ila;

        private long index = 0;

        public DoubleIisImpl(final DoubleIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(double[] array, int offset, int length) throws IOException {
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
