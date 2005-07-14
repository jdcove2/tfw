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
 * witout even the implied warranty of
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class ByteIlaFill
{
    private ByteIlaFill() {}

    public static ByteIla create(byte value, long length)
    {
    	Argument.assertNotLessThan(length, 0, "length");

		return new MyByteIla(value, length);
    }

    private static class MyByteIla extends AbstractByteIla
    	implements ImmutableProxy
    {
		private byte value;

		MyByteIla(byte value, long length)
		{
		    super(length);
		    
		    this.value = value;
		}

		protected void toArrayImpl(byte[] array, int offset,
			long start, int length)
		{
		    Arrays.fill(array, offset, offset + length, value);
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ByteIlaFill");
			map.put("value", new Byte(value));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}