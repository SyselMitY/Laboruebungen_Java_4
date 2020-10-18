package spanningtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Spanningtree {

    public static void main(String[] args) {
        try {
            SortedSet<Edge> sortedEdges = getEdgesFromFile("./Labor_02/files/network_krass.txt");
            int[] nodes = IntStream.rangeClosed(0, getBiggestNodeNumber(sortedEdges)).toArray();
            Set<Edge> finalEdges;

            int originalWeight = getTotalEdgeWeight(sortedEdges);
            finalEdges = getMinimalSpanningTree(sortedEdges, nodes);
            int finalWeight = getTotalEdgeWeight(finalEdges);

            System.out.printf("Original: %d\nNew: %d\nSavings: %d", originalWeight, finalWeight, originalWeight - finalWeight);

        } catch (IOException e) {
            System.err.println("Fehler beim lesen der Datei:");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Fehler in der Matrix:");
            e.printStackTrace();
        }
    }

    public static int getTotalEdgeWeight(Collection<Edge> edges) {
        return edges
                .stream()
                .mapToInt(Edge::getWeight)
                .sum();
    }

    public static Set<Edge> getMinimalSpanningTree(SortedSet<Edge> sortedEdges, int[] nodesWithRegions) {
        Set<Edge> finalEdges = new TreeSet<>();
        for (Edge edge : sortedEdges) {
            int region1 = nodesWithRegions[edge.getNode1()];
            int region2 = nodesWithRegions[edge.getNode2()];
            if (region1 != region2) {
                uniteRegions(nodesWithRegions, region1, region2);
                finalEdges.add(edge);
            }
        }
        return finalEdges;
    }

    private static int getBiggestNodeNumber(Collection<Edge> edges) {
        return edges.stream()
                .flatMapToInt(edge -> IntStream.of(edge.getNode1(), edge.getNode2()))
                .distinct()
                .max()
                .orElse(0);
    }

    private static SortedSet<Edge> getEdgesFromFile(String filename) throws IOException {
        SortedSet<Edge> sortedEdges;
        try (Stream<String> inputStream = Files.lines(Path.of(filename))) {
            sortedEdges = inputStream
                    .map(new IndexMapper<String>())
                    .flatMap(line -> Edge.getEdgeStreamFromMatrixRow(line.getValue(), line.getKey()))
                    .collect(Collectors.toCollection(TreeSet::new));
        }
        return sortedEdges;
    }

    static void uniteRegions(int[] nodes, int region1, int region2) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == region2)
                nodes[i] = region1;
        }
    }
}