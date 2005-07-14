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
package tfw.immutable.ila.objectila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class ObjectIlaMutate
{
    private ObjectIlaMutate() {}

    public static ObjectIla create(ObjectIla ila, long index,
		Object value)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotLessThan(index, 0, "index");
    	Argument.assertLessThan(index, ila.length(),
    		"index", "ila.length()");

		return new MyObjectIla(ila, index, value);
    }

    private static class MyObjectIla extends AbstractObjectIla
    	implements ImmutableProxy
    {
		private ObjectIla ila;
		private long index;
		private Object value;

		MyObjectIla(ObjectIla ila, long index, Object value)
		{
		    super(ila.length());
		    
		    this.ila = ila;
		    this.index = index;
		    this.value = value;
		}

		protected void toArrayImpl(Object[] array, int offset,
			long start, int length) throws DataInvalidException
		{
			long startPlusLength = start + length;
			
			if(index < start || index >= startPlusLength)
			{
				ila.toArray(array, offset, start, length);
			}
			else
			{
				long indexMinusStart = index - start;
				if(index > start)
				{
				    ila.toArray(array, offset, start,
						(int) indexMinusStart);
				}
				array[offset + (int) indexMinusStart] = value;
				if(index <= startPlusLength)
				{
					ila.toArray(array, (int)
						(offset + indexMinusStart + 1),
						index + 1,
						(int) (length - indexMinusStart - 1));
				}
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ObjectIlaMutate");
			map.put("ila", getImmutableInfo(ila));
			map.put("index", new Long(index));
			map.put("value", value);
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}