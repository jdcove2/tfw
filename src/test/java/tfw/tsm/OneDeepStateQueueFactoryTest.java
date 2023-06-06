package tfw.tsm;

import java.util.NoSuchElementException;
import junit.framework.TestCase;

/**
 *
 */
public class OneDeepStateQueueFactoryTest extends TestCase {
    public void testFactory() {
        StateQueueFactory factory = new OneDeepStateQueueFactory();
        StateQueue queue = factory.create();
        assertNotNull("factory return null", queue);
        assertTrue("isEmpty() == false when empty", queue.isEmpty());
        try {
            queue.pop();
            fail("pop() on an empty queue didn't throw exception!");
        } catch (NoSuchElementException expected) {
            // System.out.println(expected);
        }

        Object state = new Object();
        queue.push(state);
        assertFalse("isEmpty() == true when empty", queue.isEmpty());
        assertEquals("push/pop returned the wrong value!", state, queue.pop());
        assertTrue("isEmpty() == false after pop()", queue.isEmpty());
    }
}
