package tfw.visualizer.graph;

import java.io.IOException;
import tfw.check.Argument;

public class GraphEdgeEitherClassFilter {
    private GraphEdgeEitherClassFilter() {}

    public static Graph create(Graph graph, Class<?> classToRemove) {
        Argument.assertNotNull(graph, "graph");
        Argument.assertNotNull(classToRemove, "classToRemove");

        return new GraphImpl(graph, classToRemove);
    }

    private static class GraphImpl implements Graph {
        private final Graph graph;
        private final Class<?> classToRemove;

        private GraphImpl(Graph graph, Class<?> classToRemove) {
            this.graph = graph;
            this.classToRemove = classToRemove;
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

            int edgesEnd = edgesOffset + edgesLength;

            for (int i = edgesOffset; i < edgesEnd; i++) {
                if (edgeFroms[i] != null
                        && edgeTos[i] != null
                        && (edgeFroms[i].getClass() == classToRemove || edgeTos[i].getClass() == classToRemove)) {
                    edgeFroms[i] = null;
                    edgeTos[i] = null;
                }
            }
        }
    }
}
