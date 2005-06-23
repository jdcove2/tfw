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
package tfw.immutable.ila.shortila;

import tfw.immutable.ila.AbstractIla;

public abstract class AbstractShortIla extends AbstractIla
	implements ShortIla
{
    protected abstract void toArrayImpl(short[] array, int offset,
			long start, int length);

    protected AbstractShortIla(long length)
    {
    	super(length);
    }

    public final short[] toArray()
    {
    	if(length() > (long) Integer.MAX_VALUE)
    		throw new ArrayIndexOutOfBoundsException
				("Ila too large for native array");
    	
    	return toArray((long) 0, (int) length());
    }

    public final short[] toArray(long start, int length)
    {
    	short[] result = new short[length];
    	
    	toArray(result, 0, start, length);
    	
    	return result;
    }

    public final void toArray(short[] array, int offset,
    	long start, int length)
    {
    	if (length == 0)
    	{
    		return;
    	}
    	
    	boundsCheck(array.length, offset, start, length);
    	toArrayImpl(array, offset, start, length);
    }
}