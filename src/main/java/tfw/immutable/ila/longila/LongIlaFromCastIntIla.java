package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

/**
 *
 * @immutables.types=numericnotint
 */
public final class LongIlaFromCastIntIla
{
    private LongIlaFromCastIntIla()
    {
    	// non-instantiable class
    }

    public static LongIla create(IntIla intIla)
    {
        return create(intIla, IntIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(IntIla intIla, int bufferSize)
    {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(intIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla
        implements ImmutableProxy
    {
        private final IntIla intIla;
        private final int bufferSize;

        MyLongIla(IntIla intIla, int bufferSize)
        {
            super(intIla.length());
                    
            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            IntIlaIterator fi = new IntIlaIterator(
                IntIlaSegment.create(intIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                array[ii] = (long) fi.next();
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "LongIlaFromCastIntIla");
            map.put("intIla", getImmutableInfo(intIla));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE