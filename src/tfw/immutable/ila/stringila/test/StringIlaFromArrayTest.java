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

package tfw.immutable.ila.stringila.test;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;

public class StringIlaFromArrayTest extends TestCase
{
	public void testStringIlaFromArray()
		throws DataInvalidException
	{
		final int LENGTH = 25;
		final Random random = new Random();
		final String[] array = new String[LENGTH];
		
		for (int i=0 ; i < array.length ; i++)
		{
			array[i] = new String();
		}
		
		final StringIla ila = StringIlaFromArray.create(array);
		
		try
		{
			StringIlaFromArray.create(null);
			fail("array == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		if (ila.length() != array.length)
		{
			fail("ila.length() (="+ila.length()+
				") != array.length() (="+array.length+")");
		}
				
		// Check to see if the zero argument toArray() methods return
		// the same result.
		
		String[] a = ila.toArray();
		
		if (!Arrays.equals(array, a))
		{
			fail("array != ila.toArray()");
		}
		
		// Check to see if the two argument toArray() methods return
		// the same result.
		
		for (int s=0 ; s < LENGTH ; s++)
		{
			int remainingLength = LENGTH - s;
			
			for (int l=0 ; l < remainingLength ; l++)
			{
				String[] a1 = new String[l];
				System.arraycopy(array, s, a1, 0, l);
				String[] a2 = ila.toArray(s, l);
				
				if (!Arrays.equals(a1, a2))
				{
					fail("array.subarray(start="+s+", length="+l+
						") != ila.toArray(start="+s+", length="+l+")");
				}
			}
		}
		
		// Check to see if the four argument toArray() methods return
		// the same result.

		for (int s=0 ; s < LENGTH ; s++)
		{
			for (int l=0 ; l < LENGTH - s ; l++)
			{
				for (int o=0 ; o < LENGTH - l ; o++)
				{
					String[] a1 = new String[LENGTH];
					String[] a2 = new String[LENGTH];

					System.arraycopy(array, s, a1, o, l);
					ila.toArray(a2, o, s, l);
					
					if (!Arrays.equals(a1, a2))
					{
						fail("array(array, offset="+o+
								", start="+s+", length="+l+
								") != ila2.toArray(array, offset="+o+
								", start="+s+", length="+l+")");
					}
				}
			}
		}
	}
}