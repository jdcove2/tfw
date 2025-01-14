package tfw.visualizer.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import tfw.check.Argument;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SinkProxy;
import tfw.tsm.SourceProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.visualizer.ProxyNameComparator;

public class GraphFromRootProxy {
    private GraphFromRootProxy() {}

    public static Graph create(RootProxy rootProxy) {
        Argument.assertNotNull(rootProxy, "rootProxy");

        return new GraphImpl(rootProxy);
    }

    private static class GraphImpl implements Graph {
        private final RootProxy rootProxy;

        private Object[] nodes = null;
        private Object[] edgeFroms = null;
        private Object[] edgeTos = null;

        private GraphImpl(RootProxy rootProxy) {
            this.rootProxy = rootProxy;
        }

        @Override
        public long nodesLength() {
            calculateArrays();

            return nodes.length;
        }

        @Override
        public long edgesLength() {
            calculateArrays();

            return edgeFroms.length;
        }

        @Override
        public void get(
                Object[] nodes,
                int nodesOffset,
                long nodesStart,
                int nodesLength,
                Object[] edgeFroms,
                Object[] edgeTos,
                int edgesOffset,
                long edgesStart,
                int edgesLength) {
            calculateArrays();

            System.arraycopy(this.nodes, (int) nodesStart, nodes, nodesOffset, nodesLength);
            if (edgesLength > 0) {
                System.arraycopy(this.edgeFroms, (int) edgesStart, edgeFroms, edgesOffset, edgesLength);
                System.arraycopy(this.edgeTos, (int) edgesStart, edgeTos, edgesOffset, edgesLength);
            }
        }

        private void calculateArrays() {
            if (nodes != null) {
                return;
            }

            HashSet<Object> nodesList = new HashSet<>();
            ArrayList<Object> edgeFromsList = new ArrayList<>();
            ArrayList<Object> edgeTosList = new ArrayList<>();

            addStructuralNode(nodesList, edgeFromsList, edgeTosList, rootProxy, null);

            nodes = nodesList.toArray();
            edgeFroms = edgeFromsList.toArray();
            edgeTos = edgeTosList.toArray();
        }

        private static void addStructuralNode(
                Set<Object> nodes, List<Object> edgeFroms, List<Object> edgeTos, Object proxy, Object parent) {
            nodes.add(proxy);

            if (parent != null) {
                edgeFroms.add(parent);
                edgeTos.add(proxy);
            }

            if (proxy instanceof RootProxy) {
                RootProxy rootProxy = (RootProxy) proxy;
                Object[] childProxies = rootProxy.getChildProxies();
                EventChannelProxy[] eventChannelProxies = rootProxy.getEventChannelProxies();

                Arrays.sort(childProxies, ProxyNameComparator.INSTANCE);
                Arrays.sort(eventChannelProxies, ProxyNameComparator.INSTANCE);

                for (int i = 0; i < eventChannelProxies.length; i++) {
                    nodes.add(eventChannelProxies[i]);
                    edgeFroms.add(proxy);
                    edgeTos.add(eventChannelProxies[i]);
                }
                for (int i = 0; i < childProxies.length; i++) {
                    addStructuralNode(nodes, edgeFroms, edgeTos, childProxies[i], proxy);
                }
            } else if (proxy instanceof BranchProxy) {
                BranchProxy branchProxy = (BranchProxy) proxy;
                Object[] childProxies = branchProxy.getChildProxies();
                EventChannelProxy[] eventChannelProxies = branchProxy.getEventChannelProxies();

                Arrays.sort(childProxies, ProxyNameComparator.INSTANCE);
                Arrays.sort(eventChannelProxies, ProxyNameComparator.INSTANCE);

                for (int i = 0; i < eventChannelProxies.length; i++) {
                    nodes.add(eventChannelProxies[i]);
                    edgeFroms.add(proxy);
                    edgeTos.add(eventChannelProxies[i]);
                }
                for (int i = 0; i < childProxies.length; i++) {
                    addStructuralNode(nodes, edgeFroms, edgeTos, childProxies[i], proxy);
                }
            } else if (proxy instanceof CommitProxy) {
                CommitProxy commitProxy = (CommitProxy) proxy;

                addSinkEdges(commitProxy.getSinkProxies(), proxy, edgeFroms, edgeTos);
            } else if (proxy instanceof ConverterProxy) {
                ConverterProxy converterProxy = (ConverterProxy) proxy;

                addSourceEdges(converterProxy.getSourceProxies(), proxy, edgeFroms, edgeTos);
                addSinkEdges(converterProxy.getSinkProxies(), proxy, edgeFroms, edgeTos);
            } else if (proxy instanceof InitiatorProxy) {
                InitiatorProxy initiatorProxy = (InitiatorProxy) proxy;

                addSourceEdges(initiatorProxy.getSourceProxies(), proxy, edgeFroms, edgeTos);
            } else if (proxy instanceof SynchronizerProxy) {
                SynchronizerProxy synchronizerProxy = (SynchronizerProxy) proxy;

                addSourceEdges(synchronizerProxy.getSourceProxies(), proxy, edgeFroms, edgeTos);
                addSinkEdges(synchronizerProxy.getSinkProxies(), proxy, edgeFroms, edgeTos);
            } else if (proxy instanceof TriggeredCommitProxy) {
                TriggeredCommitProxy triggeredCommitProxy = (TriggeredCommitProxy) proxy;

                addSinkEdges(triggeredCommitProxy.getSinkProxies(), proxy, edgeFroms, edgeTos);
            } else if (proxy instanceof TriggeredConverterProxy) {
                TriggeredConverterProxy triggeredConverterProxy = (TriggeredConverterProxy) proxy;

                addSourceEdges(triggeredConverterProxy.getSourceProxies(), proxy, edgeFroms, edgeTos);
                addSinkEdges(triggeredConverterProxy.getSinkProxies(), proxy, edgeFroms, edgeTos);
            } else if (proxy instanceof ValidatorProxy) {
                ValidatorProxy validatorProxy = (ValidatorProxy) proxy;

                addSinkEdges(validatorProxy.getSinkProxies(), proxy, edgeFroms, edgeTos);
            }
        }

        private static void addSourceEdges(
                SourceProxy[] sourceProxies, Object proxy, List<Object> edgeFroms, List<Object> edgeTos) {
            Arrays.sort(sourceProxies, ProxyNameComparator.INSTANCE);

            for (int i = 0; i < sourceProxies.length; i++) {
                edgeFroms.add(proxy);
                edgeTos.add(sourceProxies[i].getEventChannelProxy());
            }
        }

        private static void addSinkEdges(
                SinkProxy[] sinkProxies, Object proxy, List<Object> edgeFroms, List<Object> edgeTos) {
            Arrays.sort(sinkProxies, ProxyNameComparator.INSTANCE);

            for (int i = 0; i < sinkProxies.length; i++) {
                edgeFroms.add(sinkProxies[i].getEventChannelProxy());
                edgeTos.add(proxy);
            }
        }
    }
}
