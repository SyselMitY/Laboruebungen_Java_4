package bezirke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeSet;

public class PolBezirk implements Comparable<PolBezirk> {
    private int kennzeichen;
    private String bezirksname;
    private int einwohnerzahl;

    public PolBezirk(String bezirkString) {
        String[] splitted = bezirkString.split(";");

        if (splitted.length != 3)
            throw new IllegalArgumentException("3 Fields expected, got " + splitted.length);

        this.kennzeichen = Integer.parseInt(splitted[0]);
        this.bezirksname = splitted[1];
        this.einwohnerzahl = Integer.parseInt(splitted[2].replace(".", ""));
    }

    public int getKennzeichen() {
        return kennzeichen;
    }

    public String getBezirksname() {
        return bezirksname;
    }

    public int getEinwohnerzahl() {
        return einwohnerzahl;
    }

    @Override
    public int compareTo(PolBezirk o) {
        return this.bezirksname.compareTo(o.bezirksname);
    }

    public static Collection<PolBezirk> readCSV(String filename) throws IOException {
        TreeSet<PolBezirk> bezirke = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.ready()) {
                String line = reader.readLine();
                try {
                    bezirke.add(new PolBezirk(line));
                } catch (IllegalArgumentException e) {
                    System.err.println("Malformed Bezirkstring: " + line);
                }
            }
        }
        return bezirke;
    }

    public static void printBezirke(Collection<PolBezirk> bezirke) {
        for (PolBezirk polBezirk : bezirke) {
            System.out.printf("Kennzeichen: %4d | Name: %-40s | Einwohner: %7d\n",
                    polBezirk.getKennzeichen(),
                    polBezirk.getBezirksname(),
                    polBezirk.getEinwohnerzahl());
        }
    }

    public static void main(String[] args) {
        try {
            TreeSet<PolBezirk> bezirke = (TreeSet<PolBezirk>) readCSV("Labor_01\\src\\main\\resources\\bezirke_noe.csv");
            System.out.println("Natürlich sortiert:");
            printBezirke(bezirke);
            System.out.println("\nRückwärts sortiert:");
            printBezirke(bezirke.descendingSet());

        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getLocalizedMessage());
        }
    }
}
