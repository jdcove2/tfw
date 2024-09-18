package tfw.immutable.iis.floatiis;

import java.io.IOException;
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
            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(float[] array, int offset, int length) throws IOException {
            final int elementsToGet = (int) Math.min(ila.length() - index, length);

            ila.get(array, offset, index, elementsToGet);

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            final long originalIndex = index;

            index = Math.min(ila.length(), index + n);

            return index - originalIndex;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE