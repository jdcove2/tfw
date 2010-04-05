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
package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.AbstractIlaCheck;
import tfw.immutable.ila.ImmutableLongArray;

/**
 *
 * @immutables.types=all
 */
public final class ShortIlaFiltered
{
    private ShortIlaFiltered()
    {
        // non-instantiable class
    }

    public static interface ShortFilter {
        public boolean matches(short value);
    }

    public static ShortIla create(ShortIla ila, ShortFilter filter)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");

        return new MyShortIla(ila, filter);
    }

    private static class MyShortIla implements ShortIla,
        ImmutableLongArray, ImmutableProxy
    {
        private final ShortIla ila;
        private final ShortFilter filter;

        private long length = -1;

        private MyShortIla(ShortIla ila, ShortFilter filter)
        {
            this.ila = ila;
            this.filter = filter;
        }
        
        public final long length() {
            calculateLength();

            return length;
        }

        public final short[] toArray()
            throws DataInvalidException
        {
            calculateLength();

            if(length() > (long) Integer.MAX_VALUE)
                throw new ArrayIndexOutOfBoundsException
                    ("Ila too large for native array");

            return toArray((long) 0, (int) length());
        }

        public final short[] toArray(long start, int length)
            throws DataInvalidException
        {
            calculateLength();

            short[] result = new short[length];

            toArray(result, 0, start, length);

            return result;
        }

        public final void toArray(short[] array, int offset,
                                  long start, int length)
            throws DataInvalidException
        {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(short[] array, int offset, int stride,
                                  long start, int length)
            throws DataInvalidException
        {
            calculateLength();

            if(length == 0)
            {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            ShortIlaIterator oii = new ShortIlaIterator(ShortIlaSegment.create(ila, start));
            long endingNode = start + length;
            
            // left off here
            for (int i=offset; oii.hasNext(); i+=stride) {
                short node = oii.next();
                
                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength()
        {
            if (length < 0) {			
                length = ila.length();
                ShortIlaIterator oii = new ShortIlaIterator(ila);
                
                try {
                    for (int i=0 ; oii.hasNext() ; i++) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                }
                catch (DataInvalidException die) {
                    length = 0;
                }
            }
        }

        public Map getParameters()
        {
            calculateLength();

            HashMap map = new HashMap();
                        
            map.put("name", "ShortIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE