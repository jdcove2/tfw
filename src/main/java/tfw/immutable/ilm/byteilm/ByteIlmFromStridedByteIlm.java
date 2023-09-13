package tfw.immutable.ilm.byteilm;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlmFromStridedByteIlm {
    private ByteIlmFromStridedByteIlm() {}

    public static ByteIlm create(final StridedByteIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyByteIlm(stridedIlm);
    }

    private static class MyByteIlm extends AbstractByteIlm {
        private final StridedByteIlm stridedIlm;

        public MyByteIlm(final StridedByteIlm stridedIlm) {
            this.stridedIlm = stridedIlm;
        }

        @Override
        protected long widthImpl() throws IOException {
            return stridedIlm.width();
        }

        @Override
        protected long heightImpl() throws IOException {
            return stridedIlm.height();
        }

        @Override
        protected void getImpl(
                final byte[] array, final int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws IOException {
            stridedIlm.get(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
