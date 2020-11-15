package schuelerstream;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomSchuelerUtilsTest {

    private final Path SCHUELER_CSV_FILE = Path.of("files/schueler12.csv");

    @Test
    void a_getWeichlichSchuelerAnzahl() {
        assertEquals(3,CustomSchuelerUtils.a_getWeichlichSchuelerAnzahl(SchuelerUtils.getTestKlasse()));
    }

    @Test
    void b_getSchuelerinnenNames() {
        assertEquals("Sarah, Angela, Carina",CustomSchuelerUtils.b_getSchuelerinnenNames(SchuelerUtils.getTestKlasse()));
    }

    @Test
    void c_getCsvSchuelerinnen() throws IOException {
        String expected = "1AHIF/10 Hickelsberger-Fueller Sonja W\n" +
                "1AHIF/30 Traxler Tanja W\n" +
                "1AHIF/31 Woegerer Lisa-Marie W\n" +
                "1BHIF/05 Floh Elisabeth W\n" +
                "1BHIF/09 Hollerer Nadine W\n" +
                "1CHIF/10 Holasek Cornelia W\n" +
                "1CHIF/31 Wagner Nicole W\n" +
                "2BHIF/05 Fassl Nina Maria W\n" +
                "2BHIF/07 Hackl Desiree Iris W\n" +
                "2CHIF/12 Pfeiffer Patricia W\n" +
                "2CHIF/18 Stehling Lisa Maria W\n" +
                "2CHIF/20 Winter Sabine Elvira W\n" +
                "3BHIF/03 Ecker Andrea Katharina W\n" +
                "3BHIF/12 Schmatz Julia Andrea W\n" +
                "4BHIF/19 Wagner Nicole W\n" +
                "4CHIF/20 Zimmer Barbara W\n" +
                "5BHIF/02 Gruber Sarah W\n" +
                "5BHIF/09 Purker Angela W\n" +
                "5BHIF/11 Ringelhahn Carina-Anna W";
        assertEquals(expected,CustomSchuelerUtils.c_getCsvSchuelerinnen(SCHUELER_CSV_FILE));
    }

    @Test
    void d_FindSchuelersprecher() throws IOException {
        String expected = "4CHIF/05 Kraushofer Lukas M\n" +
                "4BHIF/19 Schindlegger Lukas M";
        assertEquals(expected,CustomSchuelerUtils.d_FindSchuelersprecher(SCHUELER_CSV_FILE));
    }

    @Test
    void e_findLongestName() throws IOException {
        assertEquals(40,CustomSchuelerUtils.e_findLongestName(SCHUELER_CSV_FILE));
    }

    @Test
    void f_getJulia() throws IOException {
        assertEquals("3BHIF/12 Schmatz Julia Andrea W",CustomSchuelerUtils.f_getJulia(SCHUELER_CSV_FILE));
    }

    @Test
    void g_getKlassenAsString() throws IOException {
        String expected = "1AHIF, 1BHIF, 1CHIF, 2AHIF, 2BHIF, 2CHIF, 3AHIF, 3BHIF, 4BHIF, 4CHIF, 5AHIF, 5BHIF";
        assertEquals(expected,CustomSchuelerUtils.g_getKlassenAsString(SCHUELER_CSV_FILE));
    }

    @Test
    void h_getVornamenCount() throws IOException {
        Map<String, Long> actual = CustomSchuelerUtils.h_getVornamenCount(SCHUELER_CSV_FILE);
        assertEquals(3,actual.get("Alexander"));
        assertEquals(3,actual.get("Benedikt"));
        assertEquals(4,actual.get("Benjamin"));
        assertEquals(9,actual.get("Lukas"));
        assertEquals(10,actual.get("Michael"));
    }
}