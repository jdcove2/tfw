/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.DataInvalidException;

/**
 * 
 * @immutables.types=all
 */
public final class CharIlaInterleave
{
    private CharIlaInterleave()
    {
        // non-instantiable class
    }

    public static CharIla create(CharIla[] ilas)
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

        return new MyCharIla(ilas);
    }

    private static class MyCharIla extends AbstractCharIla implements
        ImmutableProxy
    {
        private final CharIla[] ilas;

        private final int ilasLength;

        MyCharIla(CharIla[] ilas)
        {
            super(ilas[0].length() * ilas.length);
            this.ilas = ilas;
            this.ilasLength = ilas.length;
        }

        protected void toArrayImpl(char[] array, int offset, int stride,
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

        public Map getParameters()
        {
            HashMap map = new HashMap();

            map.put("name", "CharIlaInterleave");
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