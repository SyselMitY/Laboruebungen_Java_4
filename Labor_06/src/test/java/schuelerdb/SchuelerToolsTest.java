package schuelerdb;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;

public class SchuelerToolsTest {

    @Test
    void testReadFromCSVOk1() throws IOException {
        List<Schueler> schueler = SchuelerTools.readFromCSV("files/schueler.csv");
        assertThat(schueler.size()).isEqualTo(352);
    }

    @Test
    void testReadFromCSVOk2() throws IOException {
        List<Schueler> schueler = SchuelerTools.readFromCSV("files/schueler_err.csv");
        assertThat(schueler.size()).isEqualTo(352);
    }

    @Test
    void testReadFromCSVthrowsException() throws IOException {
        assertThatIOException().isThrownBy(() ->
                SchuelerTools.readFromCSV("not_exists.csv"));
    }
}
