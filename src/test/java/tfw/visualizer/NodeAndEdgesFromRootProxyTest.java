package tfw.visualizer;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaCheck;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.BranchProxy;
import tfw.tsm.Commit;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.Initiator;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.MultiplexedBranchProxy;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.RootProxy;
import tfw.tsm.Synchronizer;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.Validator;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

class NodeAndEdgesFromRootProxyTest {
    @Test
    void testNodeAndEdgesFromRootProxy() throws Exception {
        BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();

        StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
        ObjectECD objectECD = new ObjectECD("object");
        IntegerECD integer1ECD = new IntegerECD("integer1");
        IntegerECD integer2ECD = new IntegerECD("integer2");
        ObjectIlaECD objectIlaECD = new ObjectIlaECD("multiEventChannel2");

        BranchFactory branchFactory = new BranchFactory();
        branchFactory.addEventChannel(triggerECD);
        branchFactory.addEventChannel(objectIlaECD);
        Branch branch = branchFactory.create("branch");

        Commit commit = new Commit("commit", new ObjectECD[] {integer2ECD}, null, null) {
            @Override
            protected void commit() {}
        };

        Converter converter = new Converter("converter", new ObjectECD[] {integer2ECD}, null, null) {
            @Override
            protected void convert() {}
        };

        Initiator initiator = new Initiator("initiator", new EventChannelDescription[] {integer1ECD, triggerECD});

        MultiplexedBranchFactory multiplexedBranchFactory = new MultiplexedBranchFactory();
        multiplexedBranchFactory.addMultiplexer(objectECD, objectIlaECD);
        MultiplexedBranch multiplexedBranch = multiplexedBranchFactory.create("multiplexedBranch");

        RootFactory rootFactory = new RootFactory();
        rootFactory.addEventChannel(integer1ECD);
        rootFactory.addEventChannel(integer2ECD);
        Root root = rootFactory.create("root", basicTransactionQueue);

        Synchronizer synchronizer =
                new Synchronizer(
                        "synchronizer", new ObjectECD[] {integer1ECD}, new ObjectECD[] {integer2ECD}, null, null) {
                    @Override
                    protected void convertAToB() {}

                    @Override
                    protected void convertBToA() {}
                };

        TriggeredCommit triggeredCommit = new TriggeredCommit("triggeredCommit", triggerECD, null, null) {
            @Override
            protected void commit() {}
        };

        TriggeredConverter triggeredConverter = new TriggeredConverter("triggeredConverter", triggerECD, null, null) {
            @Override
            protected void convert() {}
        };

        Validator validator = new Validator("validator", new ObjectECD[] {integer1ECD, integer2ECD}, null, null) {
            @Override
            protected void validateState() {}
        };

        root.add(branch);
        branch.add(validator);
        branch.add(triggeredConverter);
        branch.add(triggeredCommit);
        branch.add(synchronizer);
        branch.add(multiplexedBranch);
        branch.add(initiator);
        branch.add(converter);
        branch.add(commit);

        basicTransactionQueue.waitTilEmpty();

        NodeAndEdgesFromRootProxy nodeAndEdgesFromRootProxy = new NodeAndEdgesFromRootProxy(new RootProxy(root));

        EventChannelProxy[] rootECs = new RootProxy(root).getEventChannelProxies();
        EventChannelProxy[] branchECs = new BranchProxy(branch).getEventChannelProxies();

        Arrays.sort(rootECs, ProxyNameComparator.INSTANCE);
        Arrays.sort(branchECs, ProxyNameComparator.INSTANCE);

        Object[] nodesArray = new Object[] {
            new RootProxy(root),
            rootECs[0],
            rootECs[1],
            new BranchProxy(branch),
            branchECs[0],
            branchECs[1],
            new CommitProxy(commit),
            new ConverterProxy(converter),
            new InitiatorProxy(initiator),
            new MultiplexedBranchProxy(multiplexedBranch),
            new SynchronizerProxy(synchronizer),
            new TriggeredCommitProxy(triggeredCommit),
            new TriggeredConverterProxy(triggeredConverter),
            new ValidatorProxy(validator)
        };
        Object[] nodesObjectIlaArray =
                new Object[(int) nodeAndEdgesFromRootProxy.getNodesObjectIla().length()];

        nodeAndEdgesFromRootProxy.getNodesObjectIla().get(nodesObjectIlaArray, 0, 0, nodesObjectIlaArray.length);

        Arrays.sort(nodesArray, ProxyNameComparator.INSTANCE);
        Arrays.sort(nodesObjectIlaArray, ProxyNameComparator.INSTANCE);

        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(nodesArray);
        ObjectIla<Object> actualIla = ObjectIlaFromArray.create(nodesObjectIlaArray);

        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);

        Object[] edgeFromsArray = new Object[] {
            new RootProxy(root),
            new RootProxy(root),
            new RootProxy(root),
            new BranchProxy(branch),
            new BranchProxy(branch),
            new BranchProxy(branch),
            rootECs[1],
            new BranchProxy(branch),
            rootECs[1],
            new BranchProxy(branch),
            new InitiatorProxy(initiator),
            new InitiatorProxy(initiator),
            new BranchProxy(branch),
            new BranchProxy(branch),
            new SynchronizerProxy(synchronizer),
            new SynchronizerProxy(synchronizer),
            rootECs[0],
            rootECs[1],
            new BranchProxy(branch),
            branchECs[1],
            new BranchProxy(branch),
            branchECs[1],
            new BranchProxy(branch),
            rootECs[0],
            rootECs[1]
        };

        targetIla = ObjectIlaFromArray.create(edgeFromsArray);
        actualIla = nodeAndEdgesFromRootProxy.getEdgeFromsObjectIla();

        ObjectIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);

        Object[] edgeTosArray = new Object[] {
            rootECs[0],
            rootECs[1],
            new BranchProxy(branch),
            branchECs[0],
            branchECs[1],
            new CommitProxy(commit),
            new CommitProxy(commit),
            new ConverterProxy(converter),
            new ConverterProxy(converter),
            new InitiatorProxy(initiator),
            rootECs[0],
            branchECs[1],
            new MultiplexedBranchProxy(multiplexedBranch),
            new SynchronizerProxy(synchronizer),
            rootECs[0],
            rootECs[1],
            new SynchronizerProxy(synchronizer),
            new SynchronizerProxy(synchronizer),
            new TriggeredCommitProxy(triggeredCommit),
            new TriggeredCommitProxy(triggeredCommit),
            new TriggeredConverterProxy(triggeredConverter),
            new TriggeredConverterProxy(triggeredConverter),
            new ValidatorProxy(validator),
            new ValidatorProxy(validator),
            new ValidatorProxy(validator)
        };

        targetIla = ObjectIlaFromArray.create(edgeTosArray);
        actualIla = nodeAndEdgesFromRootProxy.getEdgeTosObjectIla();

        ObjectIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
