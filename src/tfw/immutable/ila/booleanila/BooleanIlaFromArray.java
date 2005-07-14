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
package tfw.immutable.ila.booleanila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

public final class BooleanIlaFromArray
{
    private BooleanIlaFromArray() {}

    public static BooleanIla create(boolean[] array)
    {
    	Argument.assertNotNull(array, "array");

		return new MyBooleanIla(array);
    }

    private static class MyBooleanIla extends AbstractBooleanIla
    	implements ImmutableProxy
    {
		private final boolean[] array;

		MyBooleanIla(boolean[] array)
		{
		    super(array.length);
		    
		    this.array = (boolean[])array.clone();
		}

		protected void toArrayImpl(boolean[] array, int offset,
			long start, int length)
		{
		    System.arraycopy(this.array, (int) start, array, offset, length);
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "BooleanIlaFromArray");
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}