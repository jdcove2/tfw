package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaBound;

/**
 *
 * @immutables.types=numeric
 */
public class CharIlaBoundTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length];
        char minimum = (char)random.nextInt();
        char maximum = (char)random.nextInt();
        if(minimum > maximum)
        {
		char tmp = minimum;
		minimum = maximum;
		maximum = tmp;
        }
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (char)random.nextInt();
            target[ii] = array[ii];
            if(target[ii] < minimum)
            {
                target[ii] = minimum;
            }
            else if(target[ii] > maximum)
            {
                target[ii] = maximum;
            }
        }
        CharIla ila = CharIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaBound.create(ila, minimum, maximum);
        final char epsilon = (char) 0.0;
        CharIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE