package tfw.immutable.iis.shortiis;

import java.io.IOException;
import tfw.immutable.ila.shortila.ShortIla;

public final class ShortIisFromShortIla {
    private ShortIisFromShortIla() {}

    public static ShortIis create(final ShortIla ila) {
        return new ShortIisImpl(ila);
    }

    private static class ShortIisImpl extends AbstractShortIis {
        private final ShortIla ila;

        private long index = 0;

        public ShortIisImpl(final ShortIla ila) {
            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(short[] array, int offset, int length) throws IOException {
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