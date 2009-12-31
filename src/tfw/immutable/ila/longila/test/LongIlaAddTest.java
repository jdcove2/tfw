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

package tfw.immutable.ila.longila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaAdd;
import tfw.immutable.ila.longila.LongIlaFromArray;

public class LongIlaAddTest extends TestCase
{
	public void testLongIlaAdd()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		long[] array1 = new long[LENGTH];
		long[] array2 = new long[LENGTH];
		long[] array3 = new long[LENGTH];
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array1[i] = random.nextLong();
			array2[i] = random.nextLong();
			array3[i] = array1[i] + array2[i];
		}
		
		LongIla ila1 = LongIlaFromArray.create(array1);
		LongIla ila2 = LongIlaFromArray.create(array2);
		LongIla ila3 = LongIlaFromArray.create(array3);
		
		try
		{
			LongIlaAdd.create(null, ila2);
			fail("firstIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			LongIlaAdd.create(ila1, null);
			fail("secondIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = LongIlaCheck.check(ila3,
			LongIlaAdd.create(ila1, ila2));
		
		assertNull(s, s);
	}
}