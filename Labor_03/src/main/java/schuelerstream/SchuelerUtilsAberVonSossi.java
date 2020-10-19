package schuelerstream;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class SchuelerUtilsAberVonSossi {
    private SchuelerUtilsAberVonSossi() {
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

    public static String c_getCsvSchuelerinnen(Path path) {
        throw new UnsupportedOperationException();
    }

    public static Schueler d_FindSchuelersprecher(Path path) {
        throw new UnsupportedOperationException();
    }

    public static int e_findLongestName(Path path) {
        throw new UnsupportedOperationException();
    }

    public static String f_getJulia(Path path) {
        throw new UnsupportedOperationException();
    }

    public static String g_getKlassenAsString(Collection<Schueler> schuelers) {
        throw new UnsupportedOperationException();
    }

    public static Map<String,Integer> h_getVornamenCount(Collection<Schueler> schuelers) {
        throw new UnsupportedOperationException();
    }
}
