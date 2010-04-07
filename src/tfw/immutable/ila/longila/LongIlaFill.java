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
package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaFill
{
    private LongIlaFill()
    {
        // non-instantiable class
    }

    public static LongIla create(long value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyLongIla(value, length);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final long value;

        MyLongIla(long value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = value;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "LongIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Long(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
