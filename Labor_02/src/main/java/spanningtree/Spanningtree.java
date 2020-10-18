package spanningtree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Spanningtree {

    public static void main(String[] args) {
        try {
            SortedSet<Edge> sortedEdges;
            Set<Edge> finalEdges = new TreeSet<>();
            sortedEdges = getEdgesFromFile("./Labor_02/files/network.txt");
            int[] nodes = IntStream.rangeClosed(0,getBiggestNodeNumber(sortedEdges)).toArray();

            for (Edge edge : sortedEdges) {
                int region1 = nodes[edge.getNode1()];
                int region2 = nodes[edge.getNode2()];
                if (region1 != region2) {
                    uniteRegions(nodes,region1,region2);
                    finalEdges.add(edge);
                }
            }

            System.out.println(finalEdges);
        } catch (IOException e) {
            System.err.println("Fehler beim lesen der Datei:");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Fehler in der Matrix:");
            e.printStackTrace();
        }
    }

    private static int getBiggestNodeNumber(Collection<Edge> edges) {
        return edges.stream()
                .flatMapToInt(edge -> IntStream.of(edge.getNode1(), edge.getNode2()))
                .distinct()
                .max()
                .orElse(0);
    }

    private static SortedSet<Edge> getEdgesFromFile(String filename) throws IOException  {
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
