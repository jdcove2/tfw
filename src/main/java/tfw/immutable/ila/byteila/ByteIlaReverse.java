package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaReverse {
    private ByteIlaReverse() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, final byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new ByteIlaImpl(ila, buffer);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla ila;
        private final byte[] buffer;

        private ByteIlaImpl(ByteIla ila, final byte[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            final StridedByteIla stridedByteIla = StridedByteIlaFromByteIla.create(ila, buffer.clone());

            stridedByteIla.get(array, offset + length - 1, -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
