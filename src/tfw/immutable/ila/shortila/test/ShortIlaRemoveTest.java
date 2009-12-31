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

package tfw.immutable.ila.shortila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaRemove;

public class ShortIlaRemoveTest extends TestCase
{
	public void testShortIlaRemove()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		short[] array = new short[LENGTH];
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = (short)random.nextInt();
		}
		
		ShortIla ila = ShortIlaFromArray.create(array);
		
		try
		{
			ShortIlaRemove.create(null, 0);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ShortIlaRemove.create(ila, -1);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ShortIlaRemove.create(ila, LENGTH);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			short[] a = new short[LENGTH - 1];
			
			System.arraycopy(array, 0, a, 0, i);
			System.arraycopy(array, i + 1, a, i, LENGTH - i - 1);
			
			ShortIla ia = ShortIlaFromArray.create(a);
			
			String s = ShortIlaCheck.check(ia,
				ShortIlaRemove.create(ila, i));
			
			assertNull(s, s);
		}
	}
}