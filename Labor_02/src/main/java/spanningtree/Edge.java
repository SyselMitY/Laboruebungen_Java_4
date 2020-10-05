package spanningtree;

public class Edge {
    private final int weight;
    private final int node1;
    private final int node2;

    public Edge(int weight, int node1, int node2) {
        this.weight = weight;
        this.node1 = node1;
        this.node2 = node2;
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
}
