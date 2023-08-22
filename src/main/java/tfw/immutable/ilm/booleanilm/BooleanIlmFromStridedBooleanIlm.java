package tfw.immutable.ilm.booleanilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class BooleanIlmFromStridedBooleanIlm {
    private BooleanIlmFromStridedBooleanIlm() {}

    public static BooleanIlm create(final StridedBooleanIlm stridedIlm) {
        Argument.assertNotNull(stridedIlm, "stridedIlm");

        return new MyBooleanIlm(stridedIlm);
    }

    private static class MyBooleanIlm extends AbstractBooleanIlm {
        private final StridedBooleanIlm stridedIlm;

        public MyBooleanIlm(final StridedBooleanIlm stridedIlm) {
            super(stridedIlm.width(), stridedIlm.height());

            this.stridedIlm = stridedIlm;
        }

        @Override
        protected void toArrayImpl(
                final boolean[] array, int offset, long rowStart, long colStart, int rowCount, int colCount)
                throws DataInvalidException {
            stridedIlm.toArray(array, offset, colCount, 1, rowStart, colStart, rowCount, colCount);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE