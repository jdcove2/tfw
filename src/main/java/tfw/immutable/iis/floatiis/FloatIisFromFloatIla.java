package tfw.immutable.iis.floatiis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
import tfw.immutable.ila.floatila.FloatIla;

public final class FloatIisFromFloatIla {
    private FloatIisFromFloatIla() {}

    public static FloatIis create(final FloatIla ila) {
        return new FloatIisImpl(ila);
    }

    private static class FloatIisImpl extends AbstractFloatIis {
        private final FloatIla ila;

        private long index = 0;

        public FloatIisImpl(final FloatIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(float[] array, int offset, int length) throws IOException {
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
