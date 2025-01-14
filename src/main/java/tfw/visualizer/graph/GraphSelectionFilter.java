package tfw.visualizer.graph;

import java.io.IOException;
import java.util.HashSet;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaUtil;

public class GraphSelectionFilter {
    private GraphSelectionFilter() {}

    public static Graph create(Graph graph, BooleanIla selectedNodes) {
        return new GraphImpl(graph, selectedNodes);
    }

    private static class GraphImpl implements Graph {
        private final Graph graph;
        private final BooleanIla selectedNodes;

        private GraphImpl(Graph graph, BooleanIla selectedNodes) {
            this.graph = graph;
            this.selectedNodes = selectedNodes;
        }

        @Override
        public long nodesLength() {
            return graph.nodesLength();
        }

        @Override
        public long edgesLength() {
            return graph.edgesLength();
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
                int edgesLength)
                throws IOException {
            Object[] allNodes = new Object[(int) graph.nodesLength()];
            Object[] allFroms = new Object[(int) graph.edgesLength()];
            Object[] allTos = new Object[(int) graph.edgesLength()];
            boolean[] selectedNodesArray = BooleanIlaUtil.toArray(selectedNodes);

            graph.get(allNodes, 0, 0, (int) graph.nodesLength(), allFroms, allTos, 0, 0, (int) graph.edgesLength());

            HashSet<Object> selectedNodeSet = new HashSet<>();
            for (int i = 0; i < selectedNodesArray.length; i++) {
                if (selectedNodesArray[i]) {
                    selectedNodeSet.add(allNodes[i]);
                }
            }

            HashSet<Object> resultNodeSet = new HashSet<>(selectedNodeSet);
            for (int i = 0; i < allFroms.length; i++) {
                if (selectedNodeSet.contains(allFroms[i])) {
                    resultNodeSet.add(allTos[i]);
                } else if (selectedNodeSet.contains(allTos[i])) {
                    resultNodeSet.add(allFroms[i]);
                }
            }

            graph.get(
                    nodes,
                    nodesOffset,
                    nodesStart,
                    nodesLength,
                    edgeFroms,
                    edgeTos,
                    edgesOffset,
                    edgesStart,
                    edgesLength);

            int nodesEnd = nodesOffset + nodesLength;

            for (int i = nodesOffset; i < nodesEnd; i++) {
                if (!resultNodeSet.contains(nodes[i])) {
                    nodes[i] = null;
                }
            }

            int edgesEnd = edgesOffset + edgesLength;

            for (int i = edgesOffset; i < edgesEnd; i++) {
                if (!selectedNodeSet.contains(edgeFroms[i]) && !selectedNodeSet.contains(edgeTos[i])) {
                    edgeFroms[i] = null;
                    edgeTos[i] = null;
                }
            }
        }
    }
}
