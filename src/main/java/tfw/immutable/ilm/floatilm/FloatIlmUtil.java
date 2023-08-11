package tfw.immutable.ilm.floatilm;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class FloatIlmUtil {
    private FloatIlmUtil() {}

    public static float[] toArray(final FloatIlm floatIlm) throws DataInvalidException {
        Argument.assertNotGreaterThan(floatIlm.width(), Integer.MAX_VALUE, "width()", "native array size");
        Argument.assertNotGreaterThan(floatIlm.height(), Integer.MAX_VALUE, "height()", "native array size");

        return toArray(floatIlm, 0, 0, (int) floatIlm.height(), (int) floatIlm.width());
    }

    public static float[] toArray(
            final FloatIlm floatIlm, final long rowStart, final long columnStart, final int rowCount, int colCount)
            throws DataInvalidException {
        Argument.assertNotLessThan(rowCount, 0, "rowCount");
        Argument.assertNotLessThan(colCount, 0, "colCount");

        float[] result = new float[rowCount * colCount];

        floatIlm.toArray(result, 0, rowStart, columnStart, rowCount, colCount);

        return result;
    }
}
// AUTO GENERATED FROM TEMPLATE