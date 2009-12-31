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
package tfw.immutable.ila.objectila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class ObjectIlaInsert
{
    private ObjectIlaInsert() {}

    public static ObjectIla create(ObjectIla ila, long index,
		Object value)
    {
    	Argument.assertNotNull(ila, "ila");
    	Argument.assertNotLessThan(index, 0, "index");
    	Argument.assertNotGreaterThan(index, ila.length(),
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
		    super(ila.length() + 1);
		    
		    this.ila = ila;
		    this.index = index;
		    this.value = value;
		}

		protected void toArrayImpl(Object[] array, int offset,
			long start, int length) throws DataInvalidException
		{
	    	if(index < start)
			{
				ila.toArray(array, offset, start - 1, length);
	    	}
	    	else if(index >= start + length)
	    	{
				ila.toArray(array, offset, start, length);
	    	}
			else
			{
				int indexMinusStart = (int) (index - start);
				if(index > start)
				{
					ila.toArray(array, offset, start,
						(int) indexMinusStart);
				}
				array[offset + (int) indexMinusStart] = value;
				if(index < start + length - 1)
				{
					ila.toArray(array, (int)
						(offset + indexMinusStart + 1),
						index, (int)
						(length - indexMinusStart - 1));
				}
			}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ObjectIlaInsert");
			map.put("ila", getImmutableInfo(ila));
			map.put("index", new Long(index));
			map.put("value", value);
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}