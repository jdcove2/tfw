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
package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class CharIlaConcatenate
{
    private CharIlaConcatenate() {}

    public static CharIla create(CharIla leftIla, CharIla rightIla)
    {
    	Argument.assertNotNull(leftIla, "leftIla");
    	Argument.assertNotNull(rightIla, "rightIla");

		return new MyCharIla(leftIla, rightIla);
    }

    private static class MyCharIla extends AbstractCharIla
    	implements ImmutableProxy
    {
		private CharIla leftIla;
		private CharIla rightIla;
		private long leftIlaLength;

		MyCharIla(CharIla leftIla, CharIla rightIla)
		{
		    super(leftIla.length() + rightIla.length());
		    
		    this.leftIla = leftIla;
		    this.rightIla = rightIla;
		    this.leftIlaLength = leftIla.length();
		}
		
		protected void toArrayImpl(char[] array, int offset,
			long start, int length) throws DataInvalidException
		{
		    if (start + length <= leftIlaLength)
		    {
				leftIla.toArray(array, offset, start, length);
		    }
		    else if (start >= leftIlaLength)
			{
				rightIla.toArray(array, offset, start - leftIlaLength, length);
		    }
		    else
			{
				int firstamount = (int) leftIlaLength - (int) start;
				leftIla.toArray(array, offset, start, firstamount);
				rightIla.toArray(array, offset + firstamount, 0,
					length - firstamount);
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "CharIlaConcatenate");
			map.put("leftIla", getImmutableInfo(leftIla));
			map.put("rightIla", getImmutableInfo(rightIla));
			map.put("length", new Long(length()));
			
			return(map);
		}
    }
}