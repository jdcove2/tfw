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
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

/**
 *
 * @immutables.types=numericnotint
 */
public final class CharIlaFromCastIntIla
{
    private CharIlaFromCastIntIla()
    {
    	// non-instantiable class
    }

    public static CharIla create(IntIla intIla)
    {
        return create(intIla, IntIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static CharIla create(IntIla intIla, int bufferSize)
    {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(intIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla
        implements ImmutableProxy
    {
        private final IntIla intIla;
        private final int bufferSize;

        MyCharIla(IntIla intIla, int bufferSize)
        {
            super(intIla.length());
                    
            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            IntIlaIterator fi = new IntIlaIterator(
                IntIlaSegment.create(intIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (char) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "CharIlaFromCastIntIla");
            map.put("intIla", getImmutableInfo(intIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
