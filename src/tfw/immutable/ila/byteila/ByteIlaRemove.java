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

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class ByteIlaRemove
{
    private ByteIlaRemove() {}

    public static ByteIla create(ByteIla ila, long index)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotLessThan(index, 0, "index");
    	Argument.assertLessThan(index, ila.length(),
    		"index", "ila.length()");

		return new MyByteIla(ila, index);
    }

    private static class MyByteIla extends AbstractByteIla
    	implements ImmutableProxy
    {
		private ByteIla ila;
		private long index;

		MyByteIla(ByteIla ila, long index)
		{
		    super(ila.length() - 1);
		    
		    this.ila = ila;
		    this.index = index;
		}

		protected void toArrayImpl(byte[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    if(index <= start)
		    {
				ila.toArray(array, offset, start + 1, length);
		    }
		    else if(index >= start + length)
		    {
				ila.toArray(array, offset, start, length);
		    }
		    else
		    {
				long indexMinusStart = index - start;
				ila.toArray(array, offset, start, (int) indexMinusStart);
				ila.toArray(array, (int) (offset + indexMinusStart),
					index + 1, (int) (length - indexMinusStart));
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ByteIlaRemove");
			map.put("ila", getImmutableInfo(ila));
			map.put("index", new Long(index));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}