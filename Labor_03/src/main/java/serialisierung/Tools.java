package serialisierung;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tools {
    private Tools() {
    }

    public static List<TrackPoint> readCSV(String filename) throws IOException {
        try (Stream<String> stream = Files.lines(Path.of(filename))) {
            AtomicInteger counter = new AtomicInteger(0);
            ArrayList<TrackPoint> trackPoints = stream
                    .filter(s -> isValidTrackPoint(s, counter))
                    .map(TrackPoint::of)
                    .collect(Collectors.toCollection(ArrayList::new));
            System.out.printf("Anzahl der fehlerhaften Zeilen: %d\n", counter.get());
            return trackPoints;
        }
    }

    public static void serialize(List<TrackPoint> trackPoints, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (TrackPoint trackPoint : trackPoints) {
                oos.writeObject(trackPoint);
            }
        }
    }

    private static boolean isValidTrackPoint(String s, AtomicInteger wrongLineCounter) {
        try {
            TrackPoint.of(s);
            return true;
        } catch (IllegalArgumentException e) {
            wrongLineCounter.incrementAndGet();
            System.out.printf("Fehlerhafte Zeile: %s\n", s);
            return false;
        }
    }

    public static double maxElevation(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<TrackPoint> points = new ArrayList<>();

            while (fis.available() > 0) {
                points.add((TrackPoint) ois.readObject());
            }

            return points.stream()
                    .mapToDouble(TrackPoint::getHeight)
                    .max()
                    .orElse(0);

        } catch (ClassNotFoundException e) {
            System.err.println("Die TrackPoint Klasse konnte nicht gefunden werden!");
            System.exit(1);
        } catch (ClassCastException e) {
            System.err.println("Es wurde ein ungültiges Objekt gelesen!");
            System.exit(1);
        }
        return 0;
    }
}
