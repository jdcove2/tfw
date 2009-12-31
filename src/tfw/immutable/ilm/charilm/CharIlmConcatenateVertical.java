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
package tfw.immutable.ilm.charilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class CharIlmConcatenateVertical
{
    private CharIlmConcatenateVertical() {}

    public static CharIlm create(CharIlm topIlm, CharIlm bottomIlm)
    {
    	Argument.assertNotNull(topIlm, "topIlm");
    	Argument.assertNotNull(bottomIlm, "bottomIlm");
    	Argument.assertEquals(topIlm.width(), bottomIlm.width(),
    		"topIlm.width()", "bottomIlm.width()");

		return new MyCharIlm(topIlm, bottomIlm);
    }

    private static class MyCharIlm extends AbstractCharIlm
    	implements ImmutableProxy
    {
		private CharIlm topIlm;
		private CharIlm bottomIlm;

		MyCharIlm(CharIlm topIlm, CharIlm bottomIlm)
		{
		    super(topIlm.width(), topIlm.height() + bottomIlm.height());
		    
		    this.topIlm = topIlm;
		    this.bottomIlm = bottomIlm;
		}
		
		protected void toArrayImpl(char[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
		    if (rowStart + height <= topIlm.height())
		    {
				topIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, height);
		    }
		    else if (rowStart >= topIlm.height())
			{
				bottomIlm.toArray(array, rowOffset, columnOffset,
					rowStart - topIlm.height(), columnStart, width, height);
		    }
		    else
			{
				int firstamount = (int)(topIlm.height() - rowStart);
				topIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, firstamount);
				bottomIlm.toArray(array, rowOffset + firstamount, columnOffset,
					0, columnStart, width, height - firstamount);
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "CharIlmConcatenateVertical");
			map.put("topIlm", getImmutableInfo(topIlm));
			map.put("bottomIlm", getImmutableInfo(bottomIlm));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}