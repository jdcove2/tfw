package tfw.tsm;

import java.util.Map;
import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;


/**
 *
 */
public class StateLessECDTest extends TestCase
{
    public void testGetState()
    {
        RootFactory rf = new RootFactory();
        StatelessTriggerECD trigger = new StatelessTriggerECD("test");
        rf.addEventChannel(trigger);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("getStateTest", queue);
        MyTriggeredCommit commit = new MyTriggeredCommit(trigger);
        Initiator initiator = new Initiator("TestInitiator", trigger);
        root.add(commit);
        root.add(initiator);
        initiator.trigger(trigger);
        queue.waitTilEmpty();
        assertNotNull("set() of a statelessECD didn't throw an exception",
            commit.setException);
        assertNotNull("get() returned null state map", commit.map);
        assertEquals("get() returned the wron number of values", 0,
            commit.map.size());
        //System.out.println(commit.setException);
    }

    private class MyTriggeredCommit extends TriggeredConverter
    {
        final StatelessTriggerECD trigger;
        IllegalArgumentException getException = null;
        IllegalArgumentException setException = null;
        Map<ObjectECD, Object> map = null;

        public MyTriggeredCommit(StatelessTriggerECD trigger)
        {
            super("Test", trigger, null, null);
            this.trigger = trigger;
        }

        public void convert()
        {
            try
            {
                set(trigger, new Object());
            }
            catch (IllegalArgumentException expected)
            {
                this.setException = expected;
            }

            this.map = get();
        }
    }
}