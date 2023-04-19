package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class FloatIlaRamp
{
    private FloatIlaRamp()
    {
        // non-instantiable class
    }

    public static FloatIla create(float startValue, float increment,
                                   long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyFloatIla(startValue, increment, length);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final float startValue;
        private final float increment;

        MyFloatIla(float startValue, float increment, long length)
        {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            //float value = startValue;
            //for(long ii = 0; ii < start; ++ii)
            //{
            //    value += increment;
            //}
            
            // INCORRECT, BUT FAST
            float value = (float) (startValue + increment * start);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride, value += increment)
            {
                array[offset] = value;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Float(startValue));
            map.put("increment", new Float(increment));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE