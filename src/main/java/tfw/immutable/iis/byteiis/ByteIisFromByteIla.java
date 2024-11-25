package tfw.immutable.iis.byteiis;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.iis.IisFromIlaUtil;
import tfw.immutable.ila.byteila.ByteIla;

public final class ByteIisFromByteIla {
    private ByteIisFromByteIla() {}

    public static ByteIis create(final ByteIla ila) {
        return new ByteIisImpl(ila);
    }

    private static class ByteIisImpl extends AbstractByteIis {
        private final ByteIla ila;

        private long index = 0;

        public ByteIisImpl(final ByteIla ila) {
            Argument.assertNotNull(ila, "ila");

            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(byte[] array, int offset, int length) throws IOException {
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
