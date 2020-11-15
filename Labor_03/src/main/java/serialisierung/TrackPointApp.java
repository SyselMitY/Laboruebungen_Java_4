package serialisierung;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TrackPointApp {

    public static final String TMP_FILE_PATH = "Labor_03/files/serializedLog.dat";

    public static void main(String[] args) {
        try {
            System.out.println("___  ___         _____              _  _____           _ \n" +
                    "|  \\/  |        |_   _|            | ||_   _|         | |\n" +
                    "| .  . | __ ___  _| |_ __ __ _  ___| | _| | ___   ___ | |\n" +
                    "| |\\/| |/ _` \\ \\/ / | '__/ _` |/ __| |/ / |/ _ \\ / _ \\| |\n" +
                    "| |  | | (_| |>  <| | | | (_| | (__|   <| | (_) | (_) | |\n" +
                    "\\_|  |_/\\__,_/_/\\_\\_/_|  \\__,_|\\___|_|\\_\\_/\\___/ \\___/|_|");
            System.out.println("Dieses Programm berechnet die maximale Seehöhe");
            System.out.println("Lese Punkte ein....");
            List<TrackPoint> points = Tools.readCSV("Labor_03/files/GPS-Log.csv");
            System.out.println("Erfolg! Serialisiere Daten....");
            Tools.serialize(points, TMP_FILE_PATH);
            System.out.println("Erfolg! Berechne Maximalhöhe....");
            System.out.printf("Die maximale Höhe beträgt %.1f Meter.\n", Tools.maxElevation(TMP_FILE_PATH));
            System.out.println("Lösche temporäre Datei...");
            Files.delete(Path.of(TMP_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
