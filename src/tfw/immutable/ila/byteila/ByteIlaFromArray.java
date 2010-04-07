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
package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaFromArray
{
    private ByteIlaFromArray()
    {
        // non-instantiable class
    }

    public static ByteIla create(byte[] array)
    {
        return create(array, true);
    }

    public static ByteIla create(byte[] array, boolean cloneArray)
    {
        Argument.assertNotNull(array, "array");

        return new MyByteIla(array, cloneArray);
    }

    private static class MyByteIla extends AbstractByteIla
        implements ImmutableProxy
    {
        private final byte[] array;

        MyByteIla(byte[] array, boolean cloneArray)
        {
            super(array.length);

            if (cloneArray)
            {
                this.array = (byte[])array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(byte[] array, int offset,
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
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ByteIlaFromArray");
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
