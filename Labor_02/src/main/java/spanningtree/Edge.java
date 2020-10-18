package spanningtree;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class Edge implements Comparable<Edge> {
    private final int weight;
    private final int node1;
    private final int node2;

    public Edge(int weight, int node1, int node2) {
        this.weight = weight;
        if (node1 == node2)
            throw new IllegalArgumentException("Edge can't link a node to itself");
        if (node1 < node2) {
            this.node1 = node1;
            this.node2 = node2;
        } else {
            this.node1 = node2;
            this.node2 = node1;
        }
    }

    public int getWeight() {
        return weight;
    }

    public int getNode1() {
        return node1;
    }

    public int getNode2() {
        return node2;
    }

    @Override
    public String toString() {
        return String.format("(%d->%d): %d",node1,node2,weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight &&
                node1 == edge.node1 &&
                node2 == edge.node2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, node1, node2);
    }

    @Override
    public int compareTo(Edge o) {
        return Comparator.comparing(Edge::getWeight)
                .thenComparing(Edge::getNode1)
                .thenComparing(Edge::getNode2)
                .compare(this,o);
    }

    public static Stream<Edge> getEdgeStreamFromMatrixRow(String row, int rowIndex) {
        Stream.Builder<Edge> streamBuilder = Stream.builder();
        String[] splitted = row.split(",");

        for (int i = 0; i < splitted.length; i++) {

            if (rowIndex != i && !splitted[i].equals("-")) {
                int readWeight = Integer.parseInt(splitted[i]);
                Edge newEdge = new Edge(readWeight, rowIndex, i);
                streamBuilder.accept(newEdge);
            }
        }
        return streamBuilder.build();
    }
}
