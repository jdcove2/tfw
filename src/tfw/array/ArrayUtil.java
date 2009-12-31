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
package tfw.array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Utility for manipulating arrays.
 */
public class ArrayUtil
{
    /**
     * Returns true if both arrays contain the same elements without regard
     * to order.
     * <P> WARNING: This can be a very expensive operation. Becareful about
     * using this method.</P>
     * @param array1 Array one.
     * @param array2 Array two.
     * @return true if both arrays contain the same elements without regard to order.
     */
    public static boolean unorderedEquals(Object[] array1, Object[] array2)
    {
        if (array1 == array2)
        {
            return true;
        }

        if (array1 == null)
        {
            return false;
        }

        if (array2 == null)
        {
            return false;
        }

        if (array1.length != array2.length)
        {
            return false;
        }

        Map array1Map = new HashMap();
        Map array2Map = new HashMap();

        mapArray(array1, array1Map);
        mapArray(array2, array2Map);

        if (array1Map.size() != array2Map.size())
        {
            return false;
        }

        Set set = new HashSet(array1Map.keySet());
        set.addAll(array2Map.keySet());

        if (set.size() != array1Map.size())
        {
            return false;
        }

        Iterator itr = array1Map.keySet().iterator();

        while (itr.hasNext())
        {
            Object key = itr.next();
            Count a1 = (Count) array1Map.get(key);
            Count a2 = (Count) array2Map.get(key);

            if (a1.count != a2.count)
            {
                return false;
            }
        }

        return true;
    }

    private static void mapArray(Object[] array, Map map)
    {
        for (int i = 0; i < array.length; i++)
        {
            Count c = (Count) map.get(array[i]);

            if (c == null)
            {
                c = new Count();
                map.put(array[i], c);
            }

            c.count++;
        }
    }

    private static class Count
    {
        private int count;
    }
}
