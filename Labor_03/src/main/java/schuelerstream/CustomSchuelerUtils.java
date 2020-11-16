package schuelerstream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomSchuelerUtils {
    private CustomSchuelerUtils() {
    }

    public static long a_getWeichlichSchuelerAnzahl(Collection<Schueler> schuelers) {
        return schuelers.stream()
                .filter(Schueler::isWeiblich)
                .count();
    }

    public static String b_getSchuelerinnenNames(Collection<Schueler> schuelers) {
        return schuelers.stream()
                .filter(Schueler::isWeiblich)
                .map(Schueler::getVorname)
                .collect(Collectors.joining(", "));
    }

    public static String c_getCsvSchuelerinnen(Path path) throws IOException {
        try (Stream<Schueler> schuelerStream = getSchuelerStreamFromCsv(path)) {
            return schuelerStream
                    .filter(Schueler::isWeiblich)
                    .map(Schueler::toString)
                    .sorted()
                    .collect(Collectors.joining("\n"));
        }
    }

    public static String d_FindSchuelersprecher(Path path) throws IOException {
        try (Stream<Schueler> schuelerStream = getSchuelerStreamFromCsv(path)) {
            return schuelerStream
                    .filter(schueler -> !schueler.isWeiblich())
                    .filter(schueler -> schueler.getVorname().equals("Lukas"))
                    .filter(schueler -> schueler.getKlasse().charAt(0) == '4')
                    .sorted(Comparator.comparing(Schueler::getVorname))
                    .map(Schueler::toString)
                    .collect(Collectors.joining("\n"));
        }
    }

    public static int e_findLongestName(Path path) throws IOException {
        try (Stream<Schueler> schuelerStream = getSchuelerStreamFromCsv(path)) {
            return schuelerStream
                    .map(s -> s.getVorname() + s.getNachname())
                    .mapToInt(String::length)
                    .max()
                    .orElse(0);
        }
    }

    public static String f_getJulia(Path path) throws IOException {
        try (Stream<Schueler> schuelerStream = getSchuelerStreamFromCsv(path)) {
            return schuelerStream
                    .filter(schueler -> schueler.getVorname().contains("Julia"))
                    .findFirst()
                    .map(Schueler::toString)
                    .orElse("keine Julia");
        }
    }

    public static String g_getKlassenAsString(Path path) throws IOException {
        try (Stream<Schueler> schuelerStream = getSchuelerStreamFromCsv(path)) {
            return schuelerStream
                    .map(Schueler::getKlasse)
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining(", "));
        }
    }

    public static Map<String, Long> h_getVornamenCount(Path path) throws IOException {
        try (Stream<Schueler> schuelerStream = getSchuelerStreamFromCsv(path)) {
            return schuelerStream
                    .collect(Collectors.groupingBy(Schueler::getVorname, TreeMap::new,Collectors.counting()));
        }
    }



    public static Stream<Schueler> getSchuelerStreamFromCsv(Path path) throws IOException {
        return Files.lines(path)
                .map(SchuelerUtils::fromCSV);
    }
}
