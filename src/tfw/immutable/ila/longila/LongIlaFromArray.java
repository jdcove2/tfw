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

public final class LongIlaFromArray
{
    private LongIlaFromArray() {}

    public static LongIla create(long[] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyLongIla(array);
    }

    private static class MyLongIla extends AbstractLongIla
    	implements ImmutableProxy
    {
		private final long[] array;

		MyLongIla(long[] array)
		{
		    super(array.length);
		    
		    this.array = (long[])array.clone();
		}

		protected void toArrayImpl(long[] array, int offset,
			long start, int length)
		{
		    System.arraycopy(this.array, (int) start, array, offset, length);
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "LongIlaFromArray");
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}