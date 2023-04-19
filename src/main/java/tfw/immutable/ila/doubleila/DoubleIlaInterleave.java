package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 * 
 * @immutables.types=all
 */
public final class DoubleIlaInterleave
{
    private DoubleIlaInterleave()
    {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla[] ilas)
    {
        Argument.assertNotNull(ilas, "ilas");
        Argument.assertNotLessThan(ilas.length, 1, "ilas.length");
        Argument.assertNotNull(ilas[0], "ilas[0]");
        final long firstLength = ilas[0].length();
        for (int ii = 1; ii < ilas.length; ++ii)
        {
            Argument.assertNotNull(ilas[ii], "ilas[" + ii + "]");
            Argument.assertEquals(ilas[ii].length(), firstLength,
                "ilas[0].length()", "ilas[" + ii + "].length()");
        }

        return new MyDoubleIla(ilas);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements
        ImmutableProxy
    {
        private final DoubleIla[] ilas;

        private final int ilasLength;

        MyDoubleIla(DoubleIla[] ilas)
        {
            super(ilas[0].length() * ilas.length);
            this.ilas = ilas;
            this.ilasLength = ilas.length;
        }

        protected void toArrayImpl(double[] array, int offset, int stride,
            long start, int length) throws DataInvalidException
        {
            int currentIla = (int) (start % ilasLength);
            long ilaStart = start / ilasLength;
            final int ilaStride = stride * ilasLength;
            int ilaLength = (length + ilasLength - 1) / ilasLength;
            int lengthIndex = length % ilasLength;
            if (lengthIndex == 0)
            {
                // invalidate lengthIndex so we don't decrement ilaLength
                // at index 0
                --lengthIndex;
            }

            for (int ii = 0; ii < ilasLength; ++ii)
            {
                if (ii == lengthIndex)
                {
                    --ilaLength;
                }
                if (ilaLength > 0)
                {
                    ilas[currentIla].toArray(array, offset, ilaStride,
                        ilaStart, ilaLength);
                }
                offset += stride;
                ++currentIla;
                if (currentIla == ilasLength)
                {
                    currentIla = 0;
                    ++ilaStart;
                }
            }
        }

        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaInterleave");
            map.put("length", new Long(length()));
            for (int ii = 0; ii < ilas.length; ++ii)
            {
                map.put("ilas[" + ii + "]", getImmutableInfo(ilas[ii]));
            }

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE