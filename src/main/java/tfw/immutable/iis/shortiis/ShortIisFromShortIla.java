package tfw.immutable.iis.shortiis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
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
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(short[] array, int offset, int length) throws IOException {
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
