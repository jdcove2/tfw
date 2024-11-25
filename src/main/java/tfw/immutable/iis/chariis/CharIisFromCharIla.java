package tfw.immutable.iis.chariis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
import tfw.immutable.ila.charila.CharIla;

public final class CharIisFromCharIla {
    private CharIisFromCharIla() {}

    public static CharIis create(final CharIla ila) {
        return new CharIisImpl(ila);
    }

    private static class CharIisImpl extends AbstractCharIis {
        private final CharIla ila;

        private long index = 0;

        public CharIisImpl(final CharIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(char[] array, int offset, int length) throws IOException {
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
