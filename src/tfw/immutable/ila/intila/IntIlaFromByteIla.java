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
package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class IntIlaFromByteIla
{
    private IntIlaFromByteIla() {}

    public static IntIla create(ByteIla byteIla)
    {
    	Argument.assertNotNull(byteIla, "byteIla");
    	
		return new MyIntIla(byteIla);
    }

    private static class MyIntIla extends AbstractIntIla
		implements ImmutableProxy
    {
		private ByteIla byteIla;

		MyIntIla(ByteIla byteIla)
		{
		    super(byteIla.length() / 4);
		    
		    this.byteIla = byteIla;
		}

		protected void toArrayImpl(int[] array, int offset,
			long start, int length) throws DataInvalidException
		{
			ByteIlaIterator bii = new ByteIlaIterator(
				ByteIlaSegment.create(byteIla, 4 * start, 4 * length));
				
		    for (int i=0 ; i < length ; i++)
		    {
		    	array[offset+i] =
					((bii.next() & 0xFF) << 24) |
					((bii.next() & 0xFF) << 16) |
					((bii.next() & 0xFF) <<  8) |
					((bii.next() & 0xFF));
		    }
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "IntIlaFromByteIla");
			map.put("byteIla", getImmutableInfo(byteIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}
