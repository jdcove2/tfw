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
package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaFromArray
{
    private DoubleIlaFromArray()
    {
        // non-instantiable class
    }

    public static DoubleIla create(double[] array)
    {
        return create(array, true);
    }

    public static DoubleIla create(double[] array, boolean cloneArray)
    {
        Argument.assertNotNull(array, "array");

        return new MyDoubleIla(array, cloneArray);
    }

    private static class MyDoubleIla extends AbstractDoubleIla
        implements ImmutableProxy
    {
        private final double[] array;

        MyDoubleIla(double[] array, boolean cloneArray)
        {
            super(array.length);

            if (cloneArray)
            {
                this.array = (double[])array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(double[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = this.array[startInt];
            }
        }
                
        public Map getParameters()
        {
            HashMap map = new HashMap();
                        
            map.put("name", "DoubleIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
